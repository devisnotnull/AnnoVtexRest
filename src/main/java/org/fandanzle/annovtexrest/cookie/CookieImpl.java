package org.fandanzle.annovtexrest.cookie;

import io.netty.handler.codec.http.cookie.Cookie;

/**
 * Created by alexb on 30/11/2016.
 */
public class CookieImpl implements io.netty.handler.codec.http.cookie.Cookie {

    private String name;
    private String value;
    private boolean wrap;
    private String domain;
    private String path;
    private Long maxAge;
    private boolean isSecure;
    private boolean httpOnly;

    public CookieImpl(){

    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean wrap() {
        return wrap;
    }

    @Override
    public void setWrap(boolean wrap) {
        this.wrap = wrap;
    }

    @Override
    public String domain() {
        return domain;
    }

    @Override
    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public String path() {
        return path;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public long maxAge() {
        return maxAge;
    }

    @Override
    public void setMaxAge(long maxAge) {
        this.maxAge = maxAge;
    }

    @Override
    public boolean isSecure() {
        return isSecure;
    }

    @Override
    public void setSecure(boolean secure) {
        this.isSecure = secure;
    }

    @Override
    public boolean isHttpOnly() {
        return httpOnly;
    }

    @Override
    public void setHttpOnly(boolean httpOnly) {
        this.httpOnly =httpOnly;
    }

    @Override
    public int compareTo(Cookie o) {
        return 0;
    }
}
