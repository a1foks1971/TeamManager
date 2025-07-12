package com.example.teamman.web.handlers;
import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;

public interface IRequestHandler {
    Response handle(IHTTPSession session);
}
