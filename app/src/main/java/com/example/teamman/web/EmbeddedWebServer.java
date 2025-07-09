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

        if (Method.GET.equals(method) && uri.startsWith("/assets/img/")) {
            String fileName = uri.substring("/assets/img/".length());
            InputStream is = getClass().getClassLoader().getResourceAsStream("assets/img/" + fileName);
            if (is == null) {
                return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "Icon not found");
            }
            return newChunkedResponse(Response.Status.OK, "image/svg+xml", is);
        }

        if (Method.GET.equals(method) && uri.startsWith("/assets/js/")) {
            String fileName = uri.substring("/assets/js/".length());
            InputStream is = getClass().getClassLoader().getResourceAsStream("assets/js/" + fileName);
            if (is == null) {
                return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "JS file not found");
            }
            return newChunkedResponse(Response.Status.OK, "application/javascript", is);
        }

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

//        if (Method.GET.equals(method) && uri.endsWith(".html")) {
//            String path = "assets" + uri;
//            InputStream is = getClass().getClassLoader().getResourceAsStream(path);
//            if (is == null) {
//                return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "HTML page not found: " + uri);
//            }
//            return newChunkedResponse(Response.Status.OK, "text/html", is);
//        }
//
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
