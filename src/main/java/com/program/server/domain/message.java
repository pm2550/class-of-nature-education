package com.program.server.domain;

public class message {
    String name;
    String user;
    String type;
    String target;
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    String msg;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
