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
            String dBMS = AppController.getNextDBMS();
            String viewMS = AppController.getNextViewMS();
            stateFileObj.writeObject(dBMS);
            stateFileObj.writeObject(viewMS);
        } catch (Exception e) {
            // No action
        }
    }

    public static void loadGlobalSettings(){
        String dBMS = null;
        String viewMS = null;
        try (ObjectInputStream stateFileObj = new ObjectInputStream(new FileInputStream(GLOBAL_SETTINGS_FILE_NAME))) {
            dBMS = (String) stateFileObj.readObject();
            viewMS = (String) stateFileObj.readObject();
        } catch (Exception e) {
            // If the file doesn't exist
            dBMS = DEFAULT_DBMS_SYSTEM;
            viewMS = DEFAULT_VIEW_SYSTEM;
        } finally {
            AppController.setActualDBMS(dBMS);
            AppController.setActualViewMS(viewMS);
            AppController.setNextDBMS(dBMS);
            AppController.setNextViewMS(viewMS);
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
