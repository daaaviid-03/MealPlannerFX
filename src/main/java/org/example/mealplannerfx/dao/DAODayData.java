package org.example.mealplannerfx.dao;

import org.example.mealplannerfx.control.AppController;
import org.example.mealplannerfx.dao.db.DAODayDataDB;
import org.example.mealplannerfx.dao.fs.DAODayDataFS;
import org.example.mealplannerfx.entity.DayData;

import java.util.List;

public abstract class DAODayData {
    /**
     * The object that contains the instance of the singleton class
     */
    private static DAODayData daoDayDataInstance;

    protected DAODayData(){}

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
     */
    public abstract void saveDayData(DayData dayData);

    /**
     * Deletes all day data from an specific user
     * @param nick the nickname of the user
     * @param dayDataNumber the day number to delete, if NULL then deletes all
     */
    public abstract void deleteDayDataFromUser(String nick, Long dayDataNumber);

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
            saveDayData(thisDayData);
            return thisDayData;
        }
    }

    public static DAODayData getDaoDayDataInstance() {
        if (daoDayDataInstance == null){
            if (AppController.getAppControllerInstance().getActualDBMS().equals("DBMS (SQL)")) {
                daoDayDataInstance = new DAODayDataDB();
            } else {
                daoDayDataInstance = new DAODayDataFS();
            }
        }
        return daoDayDataInstance;
    }
}
