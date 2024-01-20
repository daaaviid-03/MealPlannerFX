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

    /**
     * Deletes all day data from an specific user
     * @param nick the nickname of the user
     */
    public abstract void deleteAllDayDataFromUser(String nick);

    /**
     * Returns the day data for that day, but if oit doesn't exist, it creates the dayData for that day from that user
     * @param nick the name of the user
     * @param dayNumber the day number of the dayData
     */
    public DayData getOrCreateDayDataFromUserIn(String nick, long dayNumber) {
        try {
            // If it exists, return the dayData
            return getDayDataFromUserBetween(nick, dayNumber, dayNumber).getFirst();
        } catch (Exception e){
            // If it doesn't exist, create and return the dayData
            DayData thisDayData = new DayData(nick, dayNumber);
            saveDayData(thisDayData, true);
            return thisDayData;
        }
    }

    public static DAODayData getDaoDayDataInstance() {
        return daoDayDataInstance;
    }

    public static void setDaoDayDataInstance(DAODayData daoDayDataInstance) {
        DAODayData.daoDayDataInstance = daoDayDataInstance;
    }
}
