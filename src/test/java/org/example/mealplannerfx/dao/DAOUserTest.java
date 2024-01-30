package org.example.mealplannerfx.dao;

import org.example.mealplannerfx.dao.db.DAOUserDB;
import org.example.mealplannerfx.entity.User;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DAOUserTest {
    private final static String USER_ORIGINAL_DB_TXT = "fileData/originalDataToDB/usersOriginalDB.txt";
    @Test
    void loadUsersFromOriginalDB() {
        // Get the values from de original DB
        List<String> users = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(USER_ORIGINAL_DB_TXT));
            String line;
            while((line = in.readLine()) != null){
                String[] s = line.split("\t");
                users.add(s[0]);
            }
            in.close();
        } catch (Exception e) {
            fail();
        }
        DAOUser daoUser = new DAOUserDB();
        // Deletes all users from de DB that came from the original DB
        for (String nick : users){
            daoUser.deleteUser(nick);
        }
        // Execute the function
        daoUser.loadUsersFromOriginalDB();
        // Test if there are in the DB
        for (String nick : users){
            assertNotNull(daoUser.getUser(nick));
        }
    }
}