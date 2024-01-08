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
        try {
            System.out.println("Dia: " + dayDataName + daysData.get(dayDataName));
            return daysData.get(dayDataName);
        } catch (Exception e){
            return null;
        }
    }

    public void addDayData(DayData dayData) {
        this.daysData.put(dayData.getDayNumber(), dayData);
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

    public Map<Long, DayData> getDaysData() {
        return daysData;
    }

    public void setDaysData(Map<Long, DayData> daysData) {
        this.daysData = daysData;
    }

    public List<DayData> getDaysData(long fromDate, long toDate) {
        List<DayData> thisDaysData = new ArrayList<>();
        for(DayData dayData : daysData.values()){
            if (dayData.getDayNumber() >= fromDate && dayData.getDayNumber() < toDate){
                thisDaysData.add(dayData);
            }
        }
        return thisDaysData;
    }
}
