package org.fandanzle.annovtexrest.entity;

import org.fandanzle.annovtexrest.annotation.RequestMapping;
import org.fandanzle.annovtexrest.annotation.RequestMethods;

import java.util.List;

/**
 * Created by alexb on 28/10/2016.
 */
public class Route {

    private String uri;

    private List<String> requiredRouteParams;

    private List<String> requiredHeaders;

    private List<String> requiredQueryParams;

    private List<String> providedRouteParams;

    private List<String> providedHeaders;

    private List<String> providedQueryParams;

    private List<String> uriBreakDown;

    private String consumes;

    private String produces;

    private String description;

    private RequestMethods method;


    public String getUri() {
        return uri;
    }

    public Route setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public List<String> getRequiredRouteParams() {
        return requiredRouteParams;
    }

    public Route setRequiredRouteParams(List<String> requiredRouteParams) {
        this.requiredRouteParams = requiredRouteParams;
        return this;
    }

    public List<String> getRequiredHeaders() {
        return requiredHeaders;
    }

    public Route setRequiredHeaders(List<String> requiredHeaders) {
        this.requiredHeaders = requiredHeaders;
        return this;
    }

    public List<String> getRequiredQueryParams() {
        return requiredQueryParams;
    }

    public Route setRequiredQueryParams(List<String> requiredQueryParams) {
        this.requiredQueryParams = requiredQueryParams;
        return this;
    }

    public List<String> getUriBreakDown() {
        return uriBreakDown;
    }

    public Route setUriBreakDown(List<String> uriBreakDown) {
        this.uriBreakDown = uriBreakDown;
        return this;
    }

    public String getConsumes() {
        return consumes;
    }

    public Route setConsumes(String consumes) {
        this.consumes = consumes;
        return this;
    }

    public String getProduces() {
        return produces;
    }

    public Route setProduces(String produces) {
        this.produces = produces;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Route setDescription(String description) {
        this.description = description;
        return this;
    }

    public RequestMethods getMethod() {
        return method;
    }

    public Route setMethod(RequestMethods method) {
        this.method = method;
        return this;
    }

    public List<String> getProvidedRouteParams() {
        return providedRouteParams;
    }

    public Route setProvidedRouteParams(List<String> providedRouteParams) {
        this.providedRouteParams = providedRouteParams;
        return this;
    }

    public List<String> getProvidedHeaders() {
        return providedHeaders;
    }

    public Route setProvidedHeaders(List<String> providedHeaders) {
        this.providedHeaders = providedHeaders;
        return this;
    }

    public List<String> getProvidedQueryParams() {
        return providedQueryParams;
    }

    public Route setProvidedQueryParams(List<String> providedQueryParams) {
        this.providedQueryParams = providedQueryParams;
        return this;
    }
}
