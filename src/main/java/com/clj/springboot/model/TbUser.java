package com.clj.springboot.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class TbUser implements Serializable {
    private Long id;

    private String username;

    private String password;

    private String phone;

    private String email;

    private Date created;

    private Date updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public static List<String> getProperty(){
        List<String> result = new ArrayList<>();
        result.add(0, "用户id");
        result.add(1, "密码");
        result.add(2, "用户名");
        result.add(3, "注册手机号");
        result.add(4, "注册邮箱");
        result.add(5, "创建时间");
        result.add(6, "更新时间");
        return result;
    }
    public static String[][] getcontent(List<TbUser> params){
            String[][] content = new String[params.size()][7];
        for (int i = 0; i < params.size(); i++) {
            content[i] = new String[7];
            TbUser son = params.get(i);
            content[i][0] = son.getId().toString();
            content[i][1] = son.getPassword();
            content[i][2] = son.getUsername();
            content[i][3] = son.getPhone();
            content[i][4] = son.getEmail();
            SimpleDateFormat sdf =new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            content[i][5] = sdf.format(son.getCreated());
            content[i][6] = sdf.format(son.getUpdated());
        }
        return content;
    }

    public static List<Integer> getRepeatNumber(List<TbUser> orderList) {
        List<Integer> result = new ArrayList<>();

        Map<Object, Integer> map = new TreeMap<>();
        for (TbUser str : orderList) {
            Integer num = map.get(str.getId());
            map.put(str.getId(), num == null ? 1 : num + 1);
        }

        Iterator<Object> it01 = map.keySet().iterator();
        while (it01.hasNext()) {
            Object key = it01.next();
            result.add(map.get(key));
        }
        return result;
    }
}