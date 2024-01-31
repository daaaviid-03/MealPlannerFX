package org.example.mealplannerfx.control;

import java.io.*;

public class GetGlobalSettings {
    private static final String GLOBAL_SETTINGS_FILE_NAME = "fileData/globalSettings/globalSettings.globalSettings";
    private static final String DEFAULT_DBMS_SYSTEM = "File System";
    private static final String DEFAULT_VIEW_SYSTEM = "Colored Screens";
    private static final String[] DBMS_TYPES = {DEFAULT_DBMS_SYSTEM, "DBMS (SQL)"};
    private static final String[] VIEW_MS_TYPES = {DEFAULT_VIEW_SYSTEM, "B/W Screens"};



    private GetGlobalSettings(){}

    public static void saveGlobalSettings(){
        try (ObjectOutputStream stateFileObj = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(GLOBAL_SETTINGS_FILE_NAME)))) {
            String dBMS = AppController.getAppControllerInstance().getNextDBMS();
            String viewMS = AppController.getAppControllerInstance().getNextViewMS();
            if (dBMS != null){
                stateFileObj.writeObject(dBMS);
            } else {
                stateFileObj.writeObject(DEFAULT_DBMS_SYSTEM);
                AppController.getAppControllerInstance().setActualDBMS(DEFAULT_DBMS_SYSTEM);
            }
            if (viewMS != null){
                stateFileObj.writeObject(viewMS);
            } else {
                stateFileObj.writeObject(DEFAULT_VIEW_SYSTEM);
                AppController.getAppControllerInstance().setActualViewMS(DEFAULT_VIEW_SYSTEM);
            }
        } catch (Exception e) {
            // No action
        }
    }

    public static void loadGlobalSettings(){
        try (ObjectInputStream stateFileObj = new ObjectInputStream(new FileInputStream(GLOBAL_SETTINGS_FILE_NAME))) {
            String dBMS = (String) stateFileObj.readObject();
            String viewMS = (String) stateFileObj.readObject();
            AppController.getAppControllerInstance().setActualDBMS(dBMS);
            AppController.getAppControllerInstance().setActualViewMS(viewMS);
        } catch (Exception e) {
            // If the file doesn't exist
            saveGlobalSettings();
        }
    }
    public static String[] getDbmsTypes(){
        return DBMS_TYPES;
    }

    public static String[] getViewMsTypes(){
        return VIEW_MS_TYPES;
    }
}
