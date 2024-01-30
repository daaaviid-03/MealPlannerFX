package org.example.mealplannerfx.dao;

import org.example.mealplannerfx.entity.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DAOUser {
    private final static String USER_ORIGINAL_DB_TXT = "fileData/originalDataToDB/usersOriginalDB.txt";
    /**
     * The object that contains the instance of the singelton class
     */
    private static DAOUser daoUserInstance;

    /**
     * Constructor in witch is assigned the instance of the singelton class
     */
    public DAOUser(){
        setDaoUserInstance(this);
    }
    /**
     * Obtain the user that has the exact name
     * @param nick the name of the user
     * @return the user object of the especific name, if it doesn't exist, returns null
     */
    public User getUser(String nick){
        List<User> user = getAllUsersAsRegex("^" + nick + "$");
        if (user.size() == 1){
            return user.getFirst();
        } else {
            return null;
        }
    }

    /**
     * Obtain all the users that matches with the regex string
     * @param regex the regex string to match with the name of the user
     * @return all users that matches the regex string
     */
    public abstract List<User> getAllUsersAsRegex(String regex);

    /**
     * Saves the user in the binary file
     * @param user the user to save
     * @param newUser whether is a new user or have to override an existing one
     */
    public abstract void saveUser(User user, boolean newUser);

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
//    public void loadUsersFromOriginalDB(){
//        try {
//            BufferedReader in = new BufferedReader(new FileReader(USER_ORIGINAL_DB_TXT));
//            String line;
//            while((line = in.readLine()) != null){
//                String[] s = line.split("\t");
//                User thisUser = new User(s[0], Float.parseFloat(s[1]), Float.parseFloat(s[2]), Float.parseFloat(s[3]), Float.parseFloat(s[4]), s[5], portions);
//                saveUser(thisUser, true);
//            }
//            in.close();
//        } catch (Exception e) {
//            System.err.println("Original User's DB file not found.");
//        }
//    }

    public static DAOUser getDaoUserInstance() {
        return daoUserInstance;
    }

    public static void setDaoUserInstance(DAOUser daoUserInstance) {
        DAOUser.daoUserInstance = daoUserInstance;
    }
}
