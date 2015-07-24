package com.nedap.healthcare.framework;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Translates incoming paths with params to template locations
 * Created by wout.slakhorst on 24/07/15.
 */
public class TemplatePathResolver {

    private String method;
    private UriInfo uriInfo;

    public TemplatePathResolver(String method, UriInfo uriInfo) {
        this.method = method;
        this.uriInfo = uriInfo;
    }

    /**
     *
     * @return
     */
    public String resolve() {
        List<PathSegment> pathSegments = uriInfo.getPathSegments();
        MultivaluedMap<String, String> pathParams =  uriInfo.getPathParameters();

        String resourcePath = annotatedPathToResourcePath(reverseEngineerAnnotatedPath());

        if (resourcePath.endsWith("{}")) { // PUT, DELETE, GET, PATCH => update, delete, show, update
            switch(method) {
                case "GET":
                case "PUT":
                case "PATCH":
                    return resourcePath.replace("{}", "show");
                case "DELETE":
                    return resourcePath.replace("{}", "index");
            }
        } else { // POST, GET => create, action, index
            switch(method) {
                case "GET":
                    if(uriInfo.getMatchedResources().size() * 2 - 1 == pathSegments.size()) {
                        return String.format("%s/index", resourcePath);
                    }
                    return resourcePath;
                case "POST":
                    return String.format("%s/index", resourcePath);
            }
        }

        if ("GET".equals(method) && pathParams.isEmpty() && pathSegments.size() > 1) { // action
            return uriInfo.getPath();
        } else if ("GET".equals(method) && pathParams.isEmpty()) { // index
            return String.format("%s/index", pathSegments.get(0));
        } else if ("GET".equals(method) && pathParams.size() == 1 && pathSegments.size() > 1) { // show
            return String.format("%s/show", pathSegments.get(0));
        }

        return "DEFAULT/501";
    }

    /**
     * best effort, replace all pathParams with {}
     * @return
     */
    private String reverseEngineerAnnotatedPath() {
        String p = uriInfo.getPath();
        for (List<String> paramValue : uriInfo.getPathParameters().values()) {
            p = p.replaceAll(paramValue.get(0), "{}");
        }
        return p;
    }

    /**
     * replace any {} except the last one
     * @param path
     * @return
     */
    private String annotatedPathToResourcePath(String path) {
        return path.replaceAll("/\\{\\}/", "/");
    }
}