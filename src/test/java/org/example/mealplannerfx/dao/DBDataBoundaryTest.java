package org.example.mealplannerfx.dao;

import org.example.mealplannerfx.control.WrongArgException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DBDataBoundaryTest {

    @Test
    void correctRecipeNameString() {
        // Test valid strings returns
        try {
            assertEquals("abcsdfsdde", DBDataBoundary.correctRecipeNameString("abcsdfsdde"));
        } catch (WrongArgException wrongArgException){
            fail();
        }
        try {
            assertEquals("abc", DBDataBoundary.correctRecipeNameString("abc"));
        } catch (WrongArgException wrongArgException){
            fail();
        }
        try {
            assertEquals("kamdoslejfndoghtbapfkamdoslejfndoghtbapfkamdoslejfndoghtbapf",
                    DBDataBoundary.correctRecipeNameString("kamdoslejfndoghtbapfkamdoslejfndoghtbapfkamdoslejfndoghtbapf"));
        } catch (WrongArgException wrongArgException){
            fail();
        }
        // Test invalid strings
        try {
            DBDataBoundary.correctRecipeNameString("ka");
            fail();
        } catch (WrongArgException ignore) {}
        try {
            DBDataBoundary.correctRecipeNameString("kamdoslejfndoghtbapfkamdoslejfndoghtbapfkamdoslejfndoghtbapf1");
            fail();
        } catch (WrongArgException ignore) {}
    }

    @Test
    void correctUserNicknameString() {
        // Test valid strings returns
        try {
            assertEquals("abcsdfsdde", DBDataBoundary.correctUserNicknameString("abcsdfsdde"));
        } catch (WrongArgException wrongArgException){
            fail();
        }
        try {
            assertEquals("abc", DBDataBoundary.correctUserNicknameString("abc"));
        } catch (WrongArgException wrongArgException){
            fail();
        }
        try {
            assertEquals("kamdoslejfndoghtbapfkamdoslejfndoghtbapfkamdoslejfndoghtbapf",
                    DBDataBoundary.correctUserNicknameString("kamdoslejfndoghtbapfkamdoslejfndoghtbapfkamdoslejfndoghtbapf"));
        } catch (WrongArgException wrongArgException){
            fail();
        }
        // Test invalid strings
        try {
            DBDataBoundary.correctUserNicknameString("ka");
            fail();
        } catch (WrongArgException ignore) {}
        try {
            DBDataBoundary.correctUserNicknameString("kamdoslejfndoghtbapfkamdoslejfndoghtbapfkamdoslejfndoghtbapf1");
            fail();
        } catch (WrongArgException ignore) {}
    }

    @Test
    void correctEmailString() {
        // Test valid strings returns
        try {
            assertEquals("example@gmail.com", DBDataBoundary.correctEmailString("example@gmail.com"));
        } catch (WrongArgException wrongArgException){
            fail();
        }
        try {
            assertEquals("david@tor-vergata.com", DBDataBoundary.correctEmailString("david@tor-vergata.com"));
        } catch (WrongArgException wrongArgException){
            fail();
        }
        try {
            assertEquals("as@as.as",
                    DBDataBoundary.correctEmailString("as@as.as"));
        } catch (WrongArgException wrongArgException){
            fail();
        }
        // Test invalid strings
        try {
            DBDataBoundary.correctEmailString("correo");
            fail();
        } catch (WrongArgException ignore) {}
        try {
            DBDataBoundary.correctEmailString("este.correo@dsfsdf");
            fail();
        } catch (WrongArgException ignore) {}
    }

    @Test
    void correctBirthLong() {
        // Test valid long returns
        try {
            assertEquals(12360, DBDataBoundary.correctBirthLong(LocalDate.ofEpochDay(12360)));
        } catch (WrongArgException wrongArgException){
            fail();
        }
        try {
            assertEquals(12000, DBDataBoundary.correctBirthLong(LocalDate.ofEpochDay(12000)));
        } catch (WrongArgException wrongArgException){
            fail();
        }
        try {
            assertEquals(13000, DBDataBoundary.correctBirthLong(LocalDate.ofEpochDay(13000)));
        } catch (WrongArgException wrongArgException){
            fail();
        }
        // Test invalid longs
        try {
            DBDataBoundary.correctBirthLong(LocalDate.ofEpochDay(-30000));
            fail();
        } catch (WrongArgException ignore) {}
        try {
            DBDataBoundary.correctBirthLong(LocalDate.ofEpochDay(30000));
            fail();
        } catch (WrongArgException ignore) {}
    }

    @Test
    void correctHeightFloat() {
        // Test valid floats returns
        try {
            assertEquals(180, DBDataBoundary.correctHeightFloat("180"));
        } catch (WrongArgException wrongArgException){
            fail();
        }
        try {
            assertEquals(20.46f, DBDataBoundary.correctHeightFloat("20.46"));
        } catch (WrongArgException wrongArgException){
            fail();
        }
        try {
            assertEquals(50.5f, DBDataBoundary.correctHeightFloat("50.50"));
        } catch (WrongArgException wrongArgException){
            fail();
        }
        // Test invalid floats
        try {
            DBDataBoundary.correctHeightFloat("34,56");
            fail();
        } catch (WrongArgException ignore) {}
        try {
            DBDataBoundary.correctHeightFloat("1000");
            fail();
        } catch (WrongArgException ignore) {}
    }

    @Test
    void correctDuration() {
        // Test valid integers returns
        try {
            assertEquals(180, DBDataBoundary.correctDuration("180"));
        } catch (WrongArgException wrongArgException){
            fail();
        }
        try {
            assertEquals(20, DBDataBoundary.correctDuration("20.06"));
        } catch (WrongArgException wrongArgException){
            fail();
        }
        try {
            assertEquals(5, DBDataBoundary.correctDuration("5"));
        } catch (WrongArgException wrongArgException){
            fail();
        }
        // Test invalid integers
        try {
            DBDataBoundary.correctDuration("34,56");
            fail();
        } catch (WrongArgException ignore) {}
        try {
            DBDataBoundary.correctDuration("100.0.1");
            fail();
        } catch (WrongArgException ignore) {}
    }
}