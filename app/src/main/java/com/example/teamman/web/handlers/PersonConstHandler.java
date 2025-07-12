package com.example.teamman.web.handlers;

import com.example.teamman.db.AppDatabase;
import com.example.teamman.db.person.PersonConst;
import fi.iki.elonen.NanoHTTPD;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonConstHandler implements IRequestHandler {
    private final AppDatabase db;

    public PersonConstHandler(AppDatabase database) {
        this.db = database;
    }

    @Override
    public NanoHTTPD.Response handle(NanoHTTPD.IHTTPSession session) {
        String uri = session.getUri();
        NanoHTTPD.Method method = session.getMethod();

        try {
            if (NanoHTTPD.Method.GET.equals(method) && uri.equals("/api/personconst")) {
                return handleGetAll();
            }

            if (NanoHTTPD.Method.POST.equals(method) && uri.equals("/api/personconst/add")) {
                return handlePost(session);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.INTERNAL_ERROR, "text/plain", "Internal Server Error");
        }

        return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.NOT_FOUND, "text/plain", "Not found: " + uri);
    }

    private NanoHTTPD.Response handleGetAll() {
        List<PersonConst> people = db.personConstDao().getAll();

        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < people.size(); i++) {
            PersonConst p = people.get(i);
            json.append("{")
                    .append("\"id\":").append(p.id).append(",")
                    .append("\"lastName\":\"").append(escape(p.lastName)).append("\",")
                    .append("\"firstName\":\"").append(escape(p.firstName)).append("\",")
                    .append("\"patronymic\":\"").append(escape(p.patronymic)).append("\",")
                    .append("\"sex\":").append(p.sex == null ? "null" : p.sex).append(",")
                    .append("\"birthDate\":\"").append(escape(p.birthDate)).append("\",")
                    .append("\"citizenship\":\"").append(escape(p.citizenship)).append("\",")
                    .append("\"placeOfBirth\":\"").append(escape(p.placeOfBirth)).append("\",")
                    .append("\"bloodType\":\"").append(escape(p.bloodType)).append("\"")
                    .append("}");
            if (i < people.size() - 1) json.append(",");
        }
        json.append("]");

        return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK, "application/json", json.toString());
    }

    private NanoHTTPD.Response handlePost(NanoHTTPD.IHTTPSession session) throws Exception {
        Map<String, String> files = new HashMap<>();
        session.parseBody(files);
        Map<String, String> params = session.getParms();

        PersonConst person = new PersonConst.Builder()
                .lastName(params.get("lastName"))
                .firstName(params.get("firstName"))
                .patronymic(params.get("patronymic"))
                .sex(Boolean.parseBoolean(params.get("sex")))
                .birthDate(params.get("birthDate"))
                .citizenship(params.get("citizenship"))
                .placeOfBirth(params.get("placeOfBirth"))
                .bloodType(params.get("bloodType"))
                .build();

        db.personConstDao().insert(person);

        return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.REDIRECT, "text/html",
                "<meta http-equiv='refresh' content='0;url=/pages/personConst.html' />");
    }

    private String escape(String value) {
        if (value == null) return "";
        return value.replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "")
                .replace("\\", "\\\\");
    }
}

