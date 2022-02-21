package com.riguz.frameworks.model;

public class AppInfo {
    private String app;
    private String events;

    public AppInfo() {
    }

    public AppInfo(String app, String events) {
        this.app = app;
        this.events = events;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }
}
