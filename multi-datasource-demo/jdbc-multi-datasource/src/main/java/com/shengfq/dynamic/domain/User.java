package com.shengfq.dynamic.domain;

/**
 * ClassName: User
 * Description: 普通用户类
 *
 * @author shengfq
 * @date: 2024/4/14 4:04 下午
 */
public class User {
    private long id;
    private String name;
    private int age;
    private String city;
    private int status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
