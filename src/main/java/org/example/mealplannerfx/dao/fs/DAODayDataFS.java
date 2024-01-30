package org.example.mealplannerfx.dao.fs;

import org.example.mealplannerfx.dao.DAODayData;
import org.example.mealplannerfx.entity.DayData;

import java.util.List;

public class DAODayDataFS extends DAODayData {
    private final static String DAY_DATA_FILE_NAME_DB = "fileData/fileDataBase/dayDataFromUserInfo_DB.dayDataInfo";
    private final FileRW<DayData> fileRW = new FileRW<>(DAY_DATA_FILE_NAME_DB);

    @Override
    public List<DayData> getDayDataFromUserBetween(String nick, long fromDate, long toDate) {
        return fileRW.getAllObjectsAs(dayData ->
                (dayData.getUserNickname().equals(nick) && fromDate <= dayData.getDayNumber() &&
                        dayData.getDayNumber() <= toDate));
    }

    @Override
    public void saveDayData(DayData dayDataToSave, boolean newDayData) {
        fileRW.appendObjectsWithout(dayDataToSave, dayData -> (dayData.getUserNickname().equals(dayDataToSave.getUserNickname()) &&
                    dayData.getDayNumber() == dayDataToSave.getDayNumber()));
    }

    @Override
    public void deleteDayDataFromUser(String nick, Long dayDataNumber) {
        fileRW.deleteObjects(dayData -> (dayData.getUserNickname().equals(nick) && dayData.getDayNumber() == dayDataNumber));
    }
}
