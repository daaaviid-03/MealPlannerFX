package org.example.mealplannerfx.dao.fs;

import org.example.mealplannerfx.dao.DAODayData;
import org.example.mealplannerfx.dao.DAOIngredient;
import org.example.mealplannerfx.dao.DAORecipe;
import org.example.mealplannerfx.dao.DAOUser;
import org.example.mealplannerfx.entity.DayData;
import org.example.mealplannerfx.entity.Ingredient;
import org.example.mealplannerfx.entity.Recipe;
import org.example.mealplannerfx.entity.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DAOUserFS extends DAOUser {
    private final static String USERS_FILE_NAME_DB = "fileData/fileDataBase/usersInfo_DB.usersInfo";
    private final FileRW<User> fileRW = new FileRW<>(USERS_FILE_NAME_DB);
    @Override
    public List<User> getAllUsersAsRegex(String regex) {
        return fileRW.getAllObjectsAs(user -> (user.getNickname().matches(regex)));
    }

    @Override
    public void saveUser(User userToSave, boolean newUser) {
        fileRW.appendObjectsWithout(userToSave, user -> (user.getNickname().equals(userToSave.getNickname())));
    }

    @Override
    public void deleteUser(String nick) {
        fileRW.deleteObjects(user -> (user.getNickname().equals(nick)));
    }
}
