package com.nedap.healthcare;

import com.nedap.healthcare.framework.TemplateResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("hello")
public class HelloWorld extends TemplateResource {

    @Path("{id}/sub")
    public SubResource getSubResource(@PathParam("id") Long id) {
        return delegateTo(SubResource.class).setMainId(id);
    }

    @GET
    @Produces({"text/html", "application/json", "application/xml"})
    public Response index() {
        return ok(new Greeting()).build();
    }

    @GET
    @Path("{id}")
    @Produces({"text/html", "application/json", "application/xml"})
    public Response show(@PathParam("id") Long id) {
        return ok(new Greeting()).build();
    }

    @GET
    @Path("redirect")
    @Produces({"text/html", "application/json", "application/xml"})
    public Response redirect() {
        return okWithTemplate(new Greeting(),"hello/index").build();
    }

    @GET
    @Path("action")
    @Produces({"text/html", "application/json", "application/xml"})
    public Response action() {
        return ok(new Greeting()).build();
    }

    @GET
    @Path("error")
    @Produces({"text/html", "application/json", "application/xml"})
    public Response error() {
        return serverError().entity(new Greeting()).build();
    }
}
