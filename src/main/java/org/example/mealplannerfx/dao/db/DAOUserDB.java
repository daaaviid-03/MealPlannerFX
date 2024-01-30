package org.example.mealplannerfx.dao.db;

import org.example.mealplannerfx.dao.DAOUser;
import org.example.mealplannerfx.entity.User;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOUserDB extends DAOUser {
    /**
     * The connection to the server of the db
     */
    private final ConnectionManager connectionManager = ConnectionManager.getConnectionManagerInstance();

    @Override
    public User getUser(String nick) {
        try {
            String query = "SELECT * FROM UserT WHERE nickname = '" + nick + "';";
            ResultSet resultSet = connectionManager.newQuery(query);
            if (resultSet.next()){
                String email = resultSet.getString("email");
                String password = resultSet.getString("pass");
                long birth = resultSet.getLong("birth");
                float height = resultSet.getFloat("heightVal");
                float weight = resultSet.getFloat("weightVal");
                connectionManager.endQuery(resultSet);
                return new User(nick, height, weight, birth, email, password);
            } else {
                return null;
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(User user) {
        connectionManager.newQueryNoResult("INSERT INTO UserT (nickname, pass, email, heightVal, weightVal, birth) VALUES ('" +
                user.getNickname() + "', '" + user.getPassword() + "', '" + user.getEmail() + "', " +
                user.getHeight() + ", " + user.getWeight() + ", " + user.getBirth() + ") ON DUPLICATE KEY UPDATE pass = '" +
                user.getPassword() + "', email = '" + user.getEmail() + "', heightVal = " + user.getHeight() +
                ", weightVal = " + user.getWeight() + ", birth = " + user.getBirth() + ";");
    }

    @Override
    public void saveUsers(List<User> usersToSave) {
        for (User user : usersToSave){
            saveUser(user);
        }
    }

    @Override
    public void deleteUser(String nick) {
        connectionManager.newQueryNoResult("DELETE FROM UserT WHERE (nickname = '" + nick + "');");
    }
}
