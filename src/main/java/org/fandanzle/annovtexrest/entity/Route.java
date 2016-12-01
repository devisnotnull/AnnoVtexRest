package org.fandanzle.annovtexrest.entity;

import org.fandanzle.annovtexrest.MimeType;
import org.fandanzle.annovtexrest.annotation.FormParam;
import org.fandanzle.annovtexrest.annotation.RequestMethods;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexb on 28/10/2016.
 */
public class Route {

    private String uri;

    private Class invokeClazz;

    private Method invokeMethod;

    private List<String> scopes;

    private List<PathParam> requiredPathParams = new ArrayList<>();

    private List<HeaderParam> requiredHeaders = new ArrayList<>();

    private List<QueryParam> requiredQueryParams = new ArrayList<>();

    private List<BodyParams> requiredBodyParams = new ArrayList<>();

    private List<FormParams> requiredFormParams = new ArrayList<>();

    private List<String> uriBreakDown;

    private MimeType consumes;

    private MimeType produces;

    private String description;

    public List<BodyParams> getRequiredBodyParams() {
        return requiredBodyParams;
    }

    public Route setRequiredBodyParams(List<BodyParams> requiredBodyParams) {
        this.requiredBodyParams = requiredBodyParams;
        return this;
    }

    public List<FormParams> getRequiredFormParams() {
        return requiredFormParams;
    }

    public Route setRequiredFormParams(List<FormParams> requiredFormParams) {
        this.requiredFormParams = requiredFormParams;
        return this;
    }

    private RequestMethods method;

    public List<String> getScopes() {
        return scopes;
    }

    public Route setScopes(List<String> scopes) {
        this.scopes = scopes;
        return this;
    }

    public Parameter[] getParams() {
        return params;
    }

    public Route setParams(Parameter[] params) {
        this.params = params;
        return this;
    }

    private Parameter[] params = {};

    public Class getInvokeClazz() {
        return invokeClazz;
    }

    public Route setInvokeClazz(Class invokeClazz) {
        this.invokeClazz = invokeClazz;
        return this;
    }

    public Method getInvokeMethod() {
        return invokeMethod;
    }

    public Route setInvokeMethod(Method invokeMethod) {
        this.invokeMethod = invokeMethod;
        return this;
    }

    public Route(){
        requiredHeaders = new ArrayList<>();
        requiredHeaders = new ArrayList<>();
        requiredQueryParams = new ArrayList<>();
        uriBreakDown = new ArrayList<>();
    }

    public String getUri() {
        return uri;
    }

    public Route setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public List<PathParam> getRequiredPathParams() {
        return requiredPathParams;
    }

    public Route setRequiredPathParams(List<PathParam> requiredPathParams) {
        this.requiredPathParams = requiredPathParams;
        return this;
    }

    public List<HeaderParam> getRequiredHeaders() {
        return requiredHeaders;
    }

    public Route setRequiredHeaders(List<HeaderParam> requiredHeaders) {
        this.requiredHeaders = requiredHeaders;
        return this;
    }

    public List<QueryParam> getRequiredQueryParams() {
        return requiredQueryParams;
    }

    public Route setRequiredQueryParams(List<QueryParam> requiredQueryParams) {
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

    public MimeType getConsumes() {
        return consumes;
    }

    public Route setConsumes(MimeType consumes) {
        this.consumes = consumes;
        return this;
    }

    public MimeType getProduces() {
        return produces;
    }

    public Route setProduces(MimeType produces) {
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
}
