package org.example.mealplannerfx.control;

import java.io.*;

public class GetGlobalSettings {
    private static final String GLOBAL_SETTINGS_FILE_NAME = "fileData/globalSettings/globalSettings.globalSettings";
    public static final String[] DBMS_TYPES = {"DBMS (SQL)", "File System"};
    public static final String[] VIEW_MS_TYPES = {"Colored Screens", "B/W Screens"};
    private static final String DEFAULT_DBMS_SYSTEM = "File System";
    private static final String DEFAULT_VIEW_SYSTEM = "Colored Screens";
    public static void saveGlobalSettings(){
        try {
            String dBMS = AppController.getAppControllerInstance().getNextDBMS();
            String viewMS = AppController.getAppControllerInstance().getNextViewMS();
            FileOutputStream thisFile = new FileOutputStream(GLOBAL_SETTINGS_FILE_NAME);
            // Save the settings in a binary file
            ObjectOutputStream stateFileObj = new ObjectOutputStream(new BufferedOutputStream(thisFile));
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
            stateFileObj.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadGlobalSettings(){
        try {
            ObjectInputStream stateFileObj = new ObjectInputStream(new FileInputStream(GLOBAL_SETTINGS_FILE_NAME));
            String dBMS = (String) stateFileObj.readObject();
            String viewMS = (String) stateFileObj.readObject();
            stateFileObj.close();
            AppController.getAppControllerInstance().setActualDBMS(dBMS);
            AppController.getAppControllerInstance().setActualViewMS(viewMS);
        } catch (Exception e) {
            // If the file doesn't exist
            saveGlobalSettings();
        }
    }
}
