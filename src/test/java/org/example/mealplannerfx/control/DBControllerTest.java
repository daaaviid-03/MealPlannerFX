package org.example.mealplannerfx.control;

import org.example.mealplannerfx.dao.*;
import org.example.mealplannerfx.dao.fs.*;
import org.example.mealplannerfx.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DBControllerTest {
    @BeforeEach
    public void setUp(){
        DAODayData daoDayData = new DAODayDataFS();
        DAOIngredient daoIngredient = new DAOIngredientFS();
        DAORecipe daoRecipe = new DAORecipeFS();
        DAORecipeMaxId daoRecipeMaxId = new DAORecipeMaxIdFS();
        DAOUser daoUser = new DAOUserFS();
        DBController dbController = new DBController();

        dbController.createUser("testUser1", "password1", 180, 70,
                "example@gmail.com", 12345);
        dbController.createUser("testUser2", "password2", 180, 70,
                "example@gmail.com", 12345);
        dbController.createUser("testUser3", "password3", 180, 70,
                "example@gmail.com", 12345);
    }

    @Test
    void checkUserInDB() {
        // Obtain the DBController instance
        DBController dbController = DBController.getDBControllerInstance();
        // Test check by nickname to success
        assertTrue(dbController.checkUserInDB("testUser1"));
        assertTrue(dbController.checkUserInDB("testUser2"));
        assertTrue(dbController.checkUserInDB("testUser3"));
        // Test check by nickname to fail
        assertFalse(dbController.checkUserInDB("testUserNosvds"));
        assertFalse(dbController.checkUserInDB("sDFsdsS"));
        assertFalse(dbController.checkUserInDB("sdfSDFSdfa"));
        // Test check by nickname and password to success
        try {
            dbController.checkUserInDB("testUser1", "password1");
        } catch (WrongArgException wrongArgException){
            fail();
        }
        try {
            dbController.checkUserInDB("testUser2", "password2");
        } catch (WrongArgException wrongArgException){
            fail();
        }
        try {
            dbController.checkUserInDB("testUser3", "password3");
        } catch (WrongArgException wrongArgException){
            fail();
        }
        // Test check by nickname and password to fail by password
        try {
            dbController.checkUserInDB("testUser1", "passwordWrong");
        } catch (WrongArgException wrongArgException){
            assertEquals("Incorrect password", wrongArgException.getWrongArgumentDescription());
        }
        try {
            dbController.checkUserInDB("testUser2", "passwordWrong");
        } catch (WrongArgException wrongArgException){
            assertEquals("Incorrect password", wrongArgException.getWrongArgumentDescription());
        }
        try {
            dbController.checkUserInDB("testUser3", "passwordWrong");
        } catch (WrongArgException wrongArgException){
            assertEquals("Incorrect password", wrongArgException.getWrongArgumentDescription());
        }
        // Test check by nickname and password to fail by nickname
        try {
            dbController.checkUserInDB("testUserN", "password1");
        } catch (WrongArgException wrongArgException){
            assertEquals("Nickname doesn't exists", wrongArgException.getWrongArgumentDescription());
        }
        try {
            dbController.checkUserInDB("davbfdsb", "password2");
        } catch (WrongArgException wrongArgException){
            assertEquals("Nickname doesn't exists", wrongArgException.getWrongArgumentDescription());
        }
        try {
            dbController.checkUserInDB("ASDFSDFSD", "BVdfBADFBD");
        } catch (WrongArgException wrongArgException){
            assertEquals("Nickname doesn't exists", wrongArgException.getWrongArgumentDescription());
        }
    }

    @Test
    void getUserInfo() {
        // Obtain the DBController instance
        DBController dbController = DBController.getDBControllerInstance();
        // Test get the users objects
        User user1 = dbController.getUserInfo("testUser1");
        assertEquals(user1.getNickname(), "testUser1");
        assertEquals(user1.getPassword(), "password1");
        assertEquals(user1.getBirth(), 12345);
        assertEquals(user1.getEmail(), "example@gmail.com");
        assertEquals(user1.getWeight(), 70);
        assertEquals(user1.getHeight(), 180);

        User user2 = dbController.getUserInfo("testUser2");
        assertEquals(user2.getNickname(), "testUser2");
        assertEquals(user2.getPassword(), "password2");
        assertEquals(user2.getBirth(), 12345);
        assertEquals(user2.getEmail(), "example@gmail.com");
        assertEquals(user2.getWeight(), 70);
        assertEquals(user2.getHeight(), 180);

        User user3 = dbController.getUserInfo("testUser3");
        assertEquals(user3.getNickname(), "testUser3");
        assertEquals(user3.getPassword(), "password3");
        assertEquals(user3.getBirth(), 12345);
        assertEquals(user3.getEmail(), "example@gmail.com");
        assertEquals(user3.getWeight(), 70);
        assertEquals(user3.getHeight(), 180);

    }

    @Test
    void deleteUserFromDB() {
        // Obtain the DBController instance
        DBController dbController = DBController.getDBControllerInstance();
        // Deletes the 3 users
        dbController.deleteUserFromDB("testUser1");
        dbController.deleteUserFromDB("testUser2");
        dbController.deleteUserFromDB("testUser3");
        // Checks that are not in the db
        assertFalse(dbController.checkUserInDB("testUser1"));
        assertFalse(dbController.checkUserInDB("testUser2"));
        assertFalse(dbController.checkUserInDB("testUser3"));
    }
}