package com.nedap.healthcare;

import com.nedap.healthcare.framework.MustacheResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created by wout.slakhorst on 24/07/15.
 */
public class SubResource extends MustacheResource {

    private Long mainId;

    public SubResource setMainId(Long mainId) {
        this.mainId = mainId;
        return this;
    }

    @GET
    @Produces({"text/html", "application/json", "application/xml"})
    public Response list() {
        return ok(new Greeting()).build();
    }

    @GET
    @Path("{id2}")
    @Produces({"text/html", "application/json", "application/xml"})
    public Response show(@PathParam("id2") Long id2) {
        return ok(new Greeting()).build();
    }
}
