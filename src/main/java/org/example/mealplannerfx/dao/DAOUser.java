package org.example.mealplannerfx.dao;

import org.example.mealplannerfx.entity.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public abstract class DAOUser {
    private final static String USER_ORIGINAL_DB_TXT = "fileData/originalDataToDB/usersOriginalDB.txt";
    /**
     * The object that contains the instance of the singleton class
     */
    private static DAOUser daoUserInstance;

    /**
     * Constructor in witch is assigned the instance of the singleton class
     */
    public DAOUser(){
        setDaoUserInstance(this);
    }
    /**
     * Obtain the user that has the exact name
     * @param nick the name of the user
     * @return the user object of the specific name, if it doesn't exist, returns null
     */
    public abstract User getUser(String nick);

    /**
     * Saves the user in the binary file
     * @param user the user to save
     */
    public abstract void saveUser(User user);

    /**
     * Saves the list of users in the binary file
     * @param usersToSave the list of users to save
     */
    public abstract void saveUsers(List<User> usersToSave);

    /**
     * Deletes the user from the DB and their dependencies
     * @param nick the nick of the user to delete
     */
    public void deleteCompleteUser(String nick){
        DAORecipe.getDaoRecipeInstance().deleteAllRecipesFrom(nick, null);
        DAODayData.getDaoDayDataInstance().deleteDayDataFromUser(nick, null);
        deleteUser(nick);
    }

    /**
     * Deletes the user from the DB
     * @param nick the nick of the user to delete
     */
    public abstract void deleteUser(String nick);

    /**
     * Load all the users from the original DB (txt) into de binary file
     */
    public void loadUsersFromOriginalDB(){
        try (BufferedReader in = new BufferedReader(new FileReader(USER_ORIGINAL_DB_TXT))){
            List<User> users = new ArrayList<>();
            String line;
            while((line = in.readLine()) != null){
                String[] s = line.split("\t");
                User thisUser = new User(s[0], Float.parseFloat(s[1]), Float.parseFloat(s[2]), Long.parseLong(s[3]), s[4], s[5]);
                users.add(thisUser);
            }
            in.close();
            saveUsers(users);
        } catch (Exception e) {
            System.err.println("Can't load original User's DB file due to: " + e.getMessage());
        }
    }

    public static DAOUser getDaoUserInstance() {
        return daoUserInstance;
    }

    public static void setDaoUserInstance(DAOUser daoUserInstance) {
        DAOUser.daoUserInstance = daoUserInstance;
    }
}
