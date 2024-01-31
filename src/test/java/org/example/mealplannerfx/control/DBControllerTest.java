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

        DBController.createUser("testUser1", "password1", 180, 70,
                "example@gmail.com", 12345);
        DBController.createUser("testUser2", "password2", 180, 70,
                "example@gmail.com", 12345);
        DBController.createUser("testUser3", "password3", 180, 70,
                "example@gmail.com", 12345);
    }

    @Test
    void checkUserInDB() {
        // Test check by nickname to success
        assertTrue(DBController.checkUserInDB("testUser1"));
        assertTrue(DBController.checkUserInDB("testUser2"));
        assertTrue(DBController.checkUserInDB("testUser3"));
        // Test check by nickname to fail
        assertFalse(DBController.checkUserInDB("testUserNosvds"));
        assertFalse(DBController.checkUserInDB("sDFsdsS"));
        assertFalse(DBController.checkUserInDB("sdfSDFSdfa"));
        // Test check by nickname and password to success
        try {
            DBController.checkUserInDB("testUser1", "password1");
        } catch (WrongArgException wrongArgException){
            fail();
        }
        try {
            DBController.checkUserInDB("testUser2", "password2");
        } catch (WrongArgException wrongArgException){
            fail();
        }
        try {
            DBController.checkUserInDB("testUser3", "password3");
        } catch (WrongArgException wrongArgException){
            fail();
        }
        // Test check by nickname and password to fail by password
        try {
            DBController.checkUserInDB("testUser1", "passwordWrong");
        } catch (WrongArgException wrongArgException){
            assertEquals("Incorrect password", wrongArgException.getWrongArgumentDescription());
        }
        try {
            DBController.checkUserInDB("testUser2", "passwordWrong");
        } catch (WrongArgException wrongArgException){
            assertEquals("Incorrect password", wrongArgException.getWrongArgumentDescription());
        }
        try {
            DBController.checkUserInDB("testUser3", "passwordWrong");
        } catch (WrongArgException wrongArgException){
            assertEquals("Incorrect password", wrongArgException.getWrongArgumentDescription());
        }
        // Test check by nickname and password to fail by nickname
        try {
            DBController.checkUserInDB("testUserN", "password1");
        } catch (WrongArgException wrongArgException){
            assertEquals("Nickname doesn't exists", wrongArgException.getWrongArgumentDescription());
        }
        try {
            DBController.checkUserInDB("davbfdsb", "password2");
        } catch (WrongArgException wrongArgException){
            assertEquals("Nickname doesn't exists", wrongArgException.getWrongArgumentDescription());
        }
        try {
            DBController.checkUserInDB("ASDFSDFSD", "BVdfBADFBD");
        } catch (WrongArgException wrongArgException){
            assertEquals("Nickname doesn't exists", wrongArgException.getWrongArgumentDescription());
        }
    }

    @Test
    void getUserInfo() {
        // Test get the users objects
        User user1 = DBController.getUserInfo("testUser1");
        assertEquals(user1.getNickname(), "testUser1");
        assertEquals(user1.getPassword(), "password1");
        assertEquals(user1.getBirth(), 12345);
        assertEquals(user1.getEmail(), "example@gmail.com");
        assertEquals(user1.getWeight(), 70);
        assertEquals(user1.getHeight(), 180);

        User user2 = DBController.getUserInfo("testUser2");
        assertEquals(user2.getNickname(), "testUser2");
        assertEquals(user2.getPassword(), "password2");
        assertEquals(user2.getBirth(), 12345);
        assertEquals(user2.getEmail(), "example@gmail.com");
        assertEquals(user2.getWeight(), 70);
        assertEquals(user2.getHeight(), 180);

        User user3 = DBController.getUserInfo("testUser3");
        assertEquals(user3.getNickname(), "testUser3");
        assertEquals(user3.getPassword(), "password3");
        assertEquals(user3.getBirth(), 12345);
        assertEquals(user3.getEmail(), "example@gmail.com");
        assertEquals(user3.getWeight(), 70);
        assertEquals(user3.getHeight(), 180);

    }

    @Test
    void deleteUserFromDB() {
        // Deletes the 3 users
        DBController.deleteUserFromDB("testUser1");
        DBController.deleteUserFromDB("testUser2");
        DBController.deleteUserFromDB("testUser3");
        // Checks that are not in the db
        assertFalse(DBController.checkUserInDB("testUser1"));
        assertFalse(DBController.checkUserInDB("testUser2"));
        assertFalse(DBController.checkUserInDB("testUser3"));
    }
}