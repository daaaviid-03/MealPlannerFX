package org.example.mealplannerfx.dao.fs;

import org.example.mealplannerfx.dao.DAOUser;
import org.example.mealplannerfx.entity.User;

import java.util.List;

public class DAOUserFS extends DAOUser {
    private final static String USERS_FILE_NAME_DB = "fileData/fileDataBase/usersInfo_DB.usersInfo";
    private final FileRW<User> fileRW = new FileRW<>(USERS_FILE_NAME_DB);

    @Override
    public User getUser(String nick) {
        return fileRW.getObjectAs(user -> (user.getNickname().equals(nick)));
    }

    @Override
    public void saveUser(User userToSave) {
        fileRW.appendObjectsWithout(userToSave, user -> (user.getNickname().equals(userToSave.getNickname())));
    }

    @Override
    public void saveUsers(List<User> usersToSave){
        fileRW.appendObjectsWithout(usersToSave, user ->
                (usersToSave.stream().anyMatch(user1 ->
                        (user.getNickname().equals(user1.getNickname())))));
    }

    @Override
    public void deleteUser(String nick) {
        fileRW.deleteObjects(user -> (user.getNickname().equals(nick)));
    }
}
