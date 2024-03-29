package org.example.mealplannerfx.entity;

import java.io.Serializable;

public class User implements Serializable {
    private final String nickname;
    private String email;
    private String password;
    private long birth;
    private float height;
    private float weight;

    public User(String nickname, float height, float weight, long birth, String email, String password) {
        this.nickname = nickname;
        this.birth = birth;
        this.height = height;
        this.weight = weight;
        this.email = email;
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public long getBirth() {
        return birth;
    }

    public void setBirth(long birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
