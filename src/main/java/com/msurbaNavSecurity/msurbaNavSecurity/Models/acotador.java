package com.msurbaNavSecurity.msurbaNavSecurity.Models;


public class acotador {

    private String role;
    private String url;
    private String[] methods;


    public acotador(String role, String url, String[] methods) {
        this.url = url;
        this.methods = methods;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String[] getMethods() {
        return methods;
    }

    public void setMethods(String[] methods) {
        this.methods = methods;
    }
}
