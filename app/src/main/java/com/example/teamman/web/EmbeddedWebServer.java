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
        super("0.0.0.0", port);
        this.db = database;
        start(SOCKET_READ_TIMEOUT, false);
        Log.i("WebServer", "Server started on port " + port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Method method = session.getMethod();

        Log.i("WebServer", "Request: " + method + " " + uri);

        if (Method.GET.equals(method)) {
            if (uri.isEmpty()) uri = "index.html";      // корень

            InputStream is = getClass()
                    .getClassLoader()
                    .getResourceAsStream("assets/" + uri);

            if (is != null) {
                String mime = detectMimeType(uri);
                return newChunkedResponse(Response.Status.OK, mime, is);
            }
        }


        // === 1. Обработка POST-запроса (пример: добавление записи) ===
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
            return newFixedLengthResponse(Response.Status.REDIRECT, "text/html",
                    "<meta http-equiv='refresh' content='0;url=/' />");
        }

        // === 2. Главная страница ===
        if (Method.GET.equals(method) && "/".equals(uri)) {
            return serveStaticFile("assets/index.html");
        }

        // === 3. Все страницы из /pages/... ===
        if (Method.GET.equals(method) && uri.startsWith("/pages/")) {
            return serveStaticFile("assets" + uri);
        }

        // === 4. Все статические ресурсы из /assets/... ===
        if (Method.GET.equals(method) && uri.startsWith("/assets/")) {
            return serveStaticFile("assets" + uri.substring("/assets".length()));
        }

        // === 5. Всё остальное — 404 ===
        return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "Not found: " + uri);
    }

    // ======= Вспомогательные методы =======

    private Response serveStaticFile(String assetPath) {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(assetPath);
            if (is == null) {
                return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "File not found: " + assetPath);
            }

            String mime = detectMimeType(assetPath);
            return newChunkedResponse(Response.Status.OK, mime, is);
        } catch (Exception e) {
            e.printStackTrace();
            return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, "text/plain", "Ошибка загрузки ресурса");
        }
    }

    private String detectMimeType(String path) {
        if (path.endsWith(".html")) return "text/html";
        if (path.endsWith(".js")) return "application/javascript";
        if (path.endsWith(".css")) return "text/css";
        if (path.endsWith(".svg")) return "image/svg+xml";
        if (path.endsWith(".png")) return "image/png";
        if (path.endsWith(".jpg") || path.endsWith(".jpeg")) return "image/jpeg";
        if (path.endsWith(".gif")) return "image/gif";
        if (path.endsWith(".woff")) return "font/woff";
        if (path.endsWith(".woff2")) return "font/woff2";
        return "application/octet-stream";
    }
}