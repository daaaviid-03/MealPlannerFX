package org.example.mealplannerfx.dao.db;

import org.example.mealplannerfx.dao.DAODayData;
import org.example.mealplannerfx.dao.DAORecipe;
import org.example.mealplannerfx.entity.DayData;
import org.example.mealplannerfx.entity.Recipe;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DAODayDataDB extends DAODayData {
    /**
     * The connection to the server of the db
     */
    private final ConnectionMySQL connectionMySQL = ConnectionMySQL.getConnectionMySQLInstance();

    @Override
    public List<DayData> getDayDataFromUserBetween(String nick, long fromDate, long toDate) {
        List<DayData> dayData = new ArrayList<>();
        try {
            String query = "SELECT * FROM DayData WHERE (userNickname = '" + nick + "' AND " +
                    fromDate + " <= dayNumber <= " + toDate + ");";
            ResultSet resultSet = connectionMySQL.newQuery(query);
            while (resultSet.next()){
                long dayNumber = resultSet.getLong("dayNumber");
                Long breakfastRecipeId = resultSet.getLong("breakfastRecipe");
                Recipe breakfastRecipe = null;
                if (!resultSet.wasNull()){
                    breakfastRecipe = DAORecipe.getDaoRecipeInstance().getRecipe(breakfastRecipeId);
                }
                Long lunchRecipeId = resultSet.getLong("lunchRecipe");
                Recipe lunchRecipe = null;
                if (!resultSet.wasNull()){
                    lunchRecipe = DAORecipe.getDaoRecipeInstance().getRecipe(lunchRecipeId);
                }
                Long dinnerRecipeId = resultSet.getLong("dinnerRecipe");
                Recipe dinnerRecipe = null;
                if (!resultSet.wasNull()){
                    dinnerRecipe = DAORecipe.getDaoRecipeInstance().getRecipe(dinnerRecipeId);
                }
                dayData.add(new DayData(nick, dayNumber, breakfastRecipe, lunchRecipe, dinnerRecipe));
            }
            connectionMySQL.endQuery(resultSet);
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
        return dayData;
    }

    @Override
    public void saveDayData(DayData dayData, boolean newDayData) {
        connectionMySQL.newQueryNoResult("INSERT INTO DayData (userNickname, dayNumber, breakfastRecipe, lunchRecipe, dinnerRecipe) VALUES ('" +
                dayData.getUserNickname() + "', " + dayData.getDayNumber() + ", " + dayData.getBreakfastId() + ", " +
                dayData.getLunchId() + ", " + dayData.getDinnerId() + ") ON DUPLICATE KEY UPDATE " +
                "breakfastRecipe = " + dayData.getBreakfastId()+ ", lunchRecipe = " + dayData.getLunchId() +
                ", dinnerRecipe = " + dayData.getDinnerId() + ";");
    }

    @Override
    public void deleteDayDataFromUser(String nick, Long dayDataNumber) {
        String query = "DELETE FROM DayData WHERE (userNickname = '" + nick + "'";
        if (dayDataNumber != null){
            query += " AND dayNumber = " + dayDataNumber;
        }
        connectionMySQL.newQueryNoResult(query + ");");
    }
}
