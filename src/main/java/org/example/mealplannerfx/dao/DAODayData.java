package org.example.mealplannerfx.dao;

import org.example.mealplannerfx.entity.DayData;

import java.util.List;
import java.util.Map;

public abstract class DAODayData {
    /**
     * The object that contains the instance of the singelton class
     */
    private static DAODayData daoDayDataInstance;

    /**
     * Constructor in witch is assigned the instance of the singelton class
     */
    public DAODayData(){
        setDaoDayDataInstance(this);
    }

    /**
     * Obtain the dayData that are in the ingredient
     * @param nick name of the user
     * @param fromDate the date of the ini (inclusive)
     * @param toDate the date of the end (inclusive)
     * @return the dayData list from the user in between the dates
     */
    public abstract List<DayData> getDayDataFromUserBetween(String nick, long fromDate, long toDate);

    /**
     * Saves the dayData in the binary file
     * @param dayData the dayData to save
     * @param newDayData whether is a new dayData or have to override an existing one
     */
    public abstract void saveDayData(DayData dayData, boolean newDayData);

    public static DAODayData getDaoDayDataInstance() {
        return daoDayDataInstance;
    }

    public static void setDaoDayDataInstance(DAODayData daoDayDataInstance) {
        DAODayData.daoDayDataInstance = daoDayDataInstance;
    }
}
