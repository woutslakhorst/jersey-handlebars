package com.nedap.healthcare.framework;

import javax.inject.Singleton;
import javax.ws.rs.core.*;
import java.util.List;

/**
 * Created by wout.slakhorst on 20/07/15.
 */
@Singleton
public class MustacheResource {

    public static final String TEMPLATE_HEADER = "x-mustache-template";

    @Context
    private UriInfo uri;

    @Context
    private Request request;

    protected String resolveTemplate() {
        List<PathSegment> segments = uri.getPathSegments();
        MultivaluedMap<String, String> params =  uri.getPathParameters();
        String method = request.getMethod();

        if ("GET".equals(method) && params.isEmpty() && segments.size() > 1) { // action
            return uri.getPath();
        } else if ("GET".equals(method) && params.isEmpty()) { // index
            return String.format("%s/index", segments.get(0));
        } else if ("GET".equals(method) && params.size() == 1 && segments.size() > 1) { // show
            return String.format("%s/show", segments.get(0));
        }

        return "DEFAULT/500";
    }

    protected Response.ResponseBuilder ok(Object entity) {
        return Response.ok(entity).header(TEMPLATE_HEADER, resolveTemplate());
    }

    protected Response.ResponseBuilder serverError() {
        return Response.serverError().header(TEMPLATE_HEADER, "DEFAULT/500");
    }
}
