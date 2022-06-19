package com.example.c19trace.Others;

public class NotificationClass {

    private String notificationTitle;
    private String notificationInfo;

    private Boolean expandable;

    public NotificationClass(String notificationTitle, String notificationInfo) {
        this.notificationTitle = notificationTitle;
        this.notificationInfo = notificationInfo;
        this.expandable = false;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationInfo() {
        return notificationInfo;
    }

    public void setNotificationInfo(String notificationInfo) {
        this.notificationInfo = notificationInfo;
    }

    public Boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(Boolean expandable) {
        this.expandable = expandable;
    }
}
