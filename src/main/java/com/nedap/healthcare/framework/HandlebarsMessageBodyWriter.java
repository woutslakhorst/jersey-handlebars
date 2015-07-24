package com.nedap.healthcare.framework;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

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
public class HandlebarsMessageBodyWriter implements MessageBodyWriter<Object> {

    private static final TemplateLoader loader = new ClassPathTemplateLoader();
    private static final Handlebars handlebars;
    static {
        loader.setPrefix("/templates");
        loader.setSuffix(".hbs");
        handlebars = new Handlebars(loader);
    }

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
        //String path = uri.getPath();
        //String method = request.getMethod();

        String path = getTemplatePath(httpHeaders);
        Template template = handlebars.compile(path);
        //Charset encoding = setContentType(mediaType, httpHeaders);
        // httpHeaders.get("Content-Type"); // set encoding
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, Charset.forName("UTF-8"));
        template.apply(o, writer);
        writer.flush();
    }

    private String getTemplatePath(MultivaluedMap<String, Object> httpHeaders) {
        return (String) httpHeaders.remove(TemplateResource.TEMPLATE_HEADER).get(0);
    }
}
