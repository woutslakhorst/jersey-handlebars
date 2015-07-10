package com.nedap.healthcare.framework;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * Created by wout.slakhorst on 10/07/15.
 */
@Provider
@Produces("text/html")
public class MustacheMessageBodyWriter implements MessageBodyWriter<Object> {

    private final MustacheFactory factory = new DefaultMustacheFactory();

    @Context
    private UriInfo uri;

    @Context
    private Request request;

    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public long getSize(Object o, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return 0;
    }

    @Override
    public void writeTo(Object o, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream outputStream) throws IOException, WebApplicationException {
        String path = uri.getPath();
        String method = request.getMethod();

        // need path resolving convention based on Path annotation, method and path

        Mustache mustache = factory.compile("templates/index.mustache");
        //Charset encoding = setContentType(mediaType, httpHeaders);
        // httpHeaders.get("Content-Type"); // set encoding
        mustache.execute(new OutputStreamWriter(outputStream, Charset.forName("UTF-8")), o).flush();
    }
}
