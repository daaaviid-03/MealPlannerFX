package org.example.mealplannerfx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User implements Serializable {
    private String nickname, email, password;
    private long birth;
    private float height, weight;
    private Map<Long, DayData> daysData = new HashMap<Long, DayData>();
    private boolean isAdmin;

    public User(String nickname, float height, float weight, long birth, String email, boolean isAdmin, String password) {
        this.nickname = nickname;
        this.birth = birth;
        this.height = height;
        this.weight = weight;
        this.email = email;
        this.isAdmin = isAdmin;
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public DayData searchForDayData(Long dayDataName) {
        return daysData.get(dayDataName);
    }

    public void addDayData(DayData dayData) {
        this.daysData.put(dayData.getDayNumber(), dayData);
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
