package com.clj.springboot.util;

import java.util.HashMap;
import java.util.Map;

public class Msg {

    private String code;

    private String Msg;

    private Map<String,Object> map = new HashMap<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public static Msg success(){
        Msg result = new Msg();
        result.setCode("10000");
        result.setMsg("success");
        return result;
    }

    public static Msg error(){
        Msg result = new Msg();
        result.setCode("20000");
        result.setMsg("error");
        return result;
    }

    public Msg add(String key,Object value){
        this.getMap().put(key,value);
        return this;
    }

    @Override
    public String toString() {
        return "返回结果{" +
                "code='" + code + '\'' +
                ", Msg='" + Msg + '\'' +
                ", map=" + map +
                '}';
    }
}
