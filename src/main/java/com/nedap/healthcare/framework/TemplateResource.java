package com.nedap.healthcare.framework;

import javax.ws.rs.core.*;

/**
 * Created by wout.slakhorst on 20/07/15.
 */
public class TemplateResource {

    public static final String TEMPLATE_HEADER = "x-template";

    private UriInfo uriInfo;
    private Request request;

    protected String resolveTemplate() {
        return new TemplatePathResolver(request.getMethod(), uriInfo).resolve();
    }

    protected Response.ResponseBuilder ok(Object entity) {
        return Response.ok(entity).header(TEMPLATE_HEADER, resolveTemplate());
    }

    protected Response.ResponseBuilder okWithTemplate(Object entity, String template) {
        return Response.ok(entity).header(TEMPLATE_HEADER, template);
    }

    protected Response.ResponseBuilder serverError() {
        return Response.serverError().header(TEMPLATE_HEADER, "DEFAULT/500");
    }

    protected <T extends TemplateResource> T delegateTo(Class<T> clazz) {
        try {
            TemplateResource subResource = clazz.newInstance();
            return (T) subResource.setUriInfo(uriInfo).setRequest(request);
        } catch (IllegalAccessException | InstantiationException e) {
            // todo: redirect to error resource with fixed template path
            return null;
        }
    }

    @Context
    public TemplateResource setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
        return this;
    }

    @Context
    public TemplateResource setRequest(Request request) {
        this.request = request;
        return this;
    }
}
