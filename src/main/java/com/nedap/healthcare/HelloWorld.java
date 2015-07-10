package com.nedap.healthcare;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("hello")
public class HelloWorld {

    @GET
    @Produces({"text/html", "application/json", "application/xml"})
    public Greeting getIt() {
        return new Greeting();
    }
}
