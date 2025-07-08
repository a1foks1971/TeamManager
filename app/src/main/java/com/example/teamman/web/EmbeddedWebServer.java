package com.example.teamman.web;

import android.util.Log;

import com.example.teamman.db.AppDatabase;
import com.example.teamman.db.person.PersonConst;

import fi.iki.elonen.NanoHTTPD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmbeddedWebServer extends NanoHTTPD {
    private final AppDatabase db;

    public EmbeddedWebServer(int port, AppDatabase database) throws IOException {
        super(port);
        this.db = database;
        start(SOCKET_READ_TIMEOUT, false);
        Log.i("WebServer", "Server started on port " + port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Method method = session.getMethod();

        if (Method.POST.equals(method) && "/add".equals(uri)) {
            try {
                session.parseBody(new HashMap<>());
                Map<String, String> params = session.getParms();
                String name = params.get("name");
                String position = params.get("position");
                if (name != null && position != null && !name.isEmpty() && !position.isEmpty()) {
                    db.personConstDao().insert(
                        new PersonConst.Builder()
                                .lastName(name)
                                .firstName(position)
                                .build()
                    );
                }
            } catch (Exception e) {
                e.printStackTrace();
                return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, "text/plain", "Ошибка добавления");
            }
            // Redirect to main page
            return newFixedLengthResponse(Response.Status.REDIRECT, "text/html", "<meta http-equiv='refresh' content='0;url=/' />");
        }

//        if (Method.GET.equals(method) && "/".equals(uri)) {
//            List<Employee> employees = db.employeeDao().getAll();
//            StringBuilder html = new StringBuilder();
//            html.append("<html><head><meta charset='utf-8'><title>Сотрудники</title></head><body>");
//            html.append("<h1>Список сотрудников</h1><ul>");
//            for (Employee emp : employees) {
//                html.append("<li>").append(emp.name).append(" — ").append(emp.position).append("</li>");
//            }
//            html.append("</ul>");
//            html.append("<h2>Добавить сотрудника</h2>");
//            html.append("<form method='post' action='/add'>");
//            html.append("Имя: <input name='name'><br>");
//            html.append("Должность: <input name='position'><br>");
//            html.append("<button type='submit'>Добавить</button>");
//            html.append("</form>");
//            html.append("</body></html>");
//
//            return newFixedLengthResponse(Response.Status.OK, "text/html", html.toString());
//        }

        if (Method.GET.equals(method) && "/".equals(uri)) {
            List<PersonConst> people = db.personConstDao().getAll();
            StringBuilder listHtml = new StringBuilder();
            for (PersonConst emp : people) {
                listHtml.append("<li>").append(emp.lastName).append(" — ").append(emp.firstName).append("</li>");
            }

            String template = loadHtmlTemplate();
            if (template == null) {
                return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, "text/plain", "Не удалось загрузить шаблон");
            }

            String finalHtml = template.replace("<!-- {{employees}} -->", listHtml.toString());
            return newFixedLengthResponse(Response.Status.OK, "text/html", finalHtml);
        }

        return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "Not found");
    }

    private String loadHtmlTemplate() {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("assets/index.html");
            if (is == null) return null;

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder html = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                html.append(line).append("\n");
            }
            return html.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

//// File: EmbeddedWebServer.java
//package com.example.teamman;
//
//import android.util.Log;
//
//import fi.iki.elonen.NanoHTTPD;
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.List;
//
//public class EmbeddedWebServer extends NanoHTTPD {
//    private final AppDatabase db;
//
//    public EmbeddedWebServer(int port, AppDatabase database) throws IOException {
//        super(port);
//        this.db = database;
//        start(SOCKET_READ_TIMEOUT, false);
//        Log.i("WebServer", "Server started on port " + port);
//    }
//
//    @Override
//    public Response serve(IHTTPSession session) {
//        String uri = session.getUri();
//        Method method = session.getMethod();
//
//        if (Method.GET.equals(method) && "/employees".equals(uri)) {
//            return getAllEmployees();
//        }
//
//        return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "Not found");
//    }
//
//    private Response getAllEmployees() {
//        List<Employee> employees = db.employeeDao().getAll();
//        JSONArray array = new JSONArray();
//        for (Employee emp : employees) {
//            JSONObject obj = new JSONObject();
//            try {
//                obj.put("id", emp.id);
//                obj.put("name", emp.name);
//                obj.put("position", emp.position);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            array.put(obj);
//        }
//        return newFixedLengthResponse(Response.Status.OK, "application/json", array.toString());
//    }
//}
