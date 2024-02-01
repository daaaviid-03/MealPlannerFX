package org.example.mealplannerfx.control;

import org.example.mealplannerfx.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DBControllerTest {
    @BeforeEach
    public void setUp(){
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
        nickAndPasCorrect();
        // Test check by nickname and password to fail by password
        nickCorrectPasFail();
        // Test check by nickname and password to fail by nickname
        nickFailPasUnknow();
    }

    private static void nickFailPasUnknow() {
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

    private static void nickCorrectPasFail() {
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
    }

    private static void nickAndPasCorrect() {
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
    }

    @Test
    void getUserInfo() {
        // Test get the users objects
        User user1 = DBController.getUserInfo("testUser1");
        assertEquals("testUser1", user1.getNickname());
        assertEquals("password1", user1.getPassword());
        assertEquals(12345, user1.getBirth());
        assertEquals("example@gmail.com", user1.getEmail());
        assertEquals(70, user1.getWeight());
        assertEquals(180, user1.getHeight());

        User user2 = DBController.getUserInfo("testUser2");
        assertEquals("testUser2", user2.getNickname());
        assertEquals("password2", user2.getPassword());
        assertEquals(12345, user2.getBirth());
        assertEquals("example@gmail.com", user2.getEmail());
        assertEquals(70, user2.getWeight());
        assertEquals(180, user2.getHeight());

        User user3 = DBController.getUserInfo("testUser3");
        assertEquals("testUser3", user3.getNickname());
        assertEquals("password3", user3.getPassword());
        assertEquals(12345, user3.getBirth());
        assertEquals("example@gmail.com", user3.getEmail());
        assertEquals(70, user3.getWeight());
        assertEquals(180, user3.getHeight());

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