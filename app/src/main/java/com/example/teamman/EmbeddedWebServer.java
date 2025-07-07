// File: EmbeddedWebServer.java
package com.example.teamman;

import android.util.Log;

import fi.iki.elonen.NanoHTTPD;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

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

        if (Method.GET.equals(method) && "/employees".equals(uri)) {
            return getAllEmployees();
        }

        return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "Not found");
    }

    private Response getAllEmployees() {
        List<Employee> employees = db.employeeDao().getAll();
        JSONArray array = new JSONArray();
        for (Employee emp : employees) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("id", emp.id);
                obj.put("name", emp.name);
                obj.put("position", emp.position);
            } catch (Exception e) {
                e.printStackTrace();
            }
            array.put(obj);
        }
        return newFixedLengthResponse(Response.Status.OK, "application/json", array.toString());
    }
}
