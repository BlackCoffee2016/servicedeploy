package com.kongzhong.common.servicedeploy.domain;

/**
 * Created by xxg on 15-8-19.
 */
public class LogInfo {

    private String uuid;
    private int type;
    private String username;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
