package com.program.server.domain;

public class videoInfo {
    public String Uuid;
    public String code;
    public int status;

    public String getUuid() {
        return Uuid;
    }

    public void setUuid(String uuid) {
        Uuid = uuid;
    }

    public String describe;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        return "videoInfo{" +
                "code='" + code + '\'' +
                ", " +
                ", status=" + status +
                ", describe='" + describe + '\'' +
                '}';
    }

}
