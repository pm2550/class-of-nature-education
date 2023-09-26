package com.program.server.utils;

public class Results<T>{
    private String code;
    private String msg;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    public Results() {

    }
    public Results(T data) {
        this.data = data;
    }
    public static Results success() {
        Results result = new Results<>();
        result.setCode("0");
        result.setMsg("成功");
        return result;
    }

    public static<T> Results<T> success(T data) {
        Results<T> result = new Results<>(data);
        result.setCode("0");
        result.setMsg("成功");
        return result;
    }

    public static<T> Results<T> success(T data, String msg)
    {
        Results<T> result=new Results<>(data);
        result.setCode("0");
        result.setMsg(msg);
        return result;
    }
    public static Results error(String code, String msg)
    {
        Results result=new Results<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
