package org.example.mealplannerfx.dao.db;

import org.example.mealplannerfx.dao.DAOUser;
import org.example.mealplannerfx.entity.User;

import java.sql.ResultSet;
import java.util.List;

public class DAOUserDB extends DAOUser {

    @Override
    public User getUser(String nick) {
        String query = "SELECT * FROM UserT WHERE nickname = '" + nick + "';";
        try (ResultSet resultSet = ConnectionManager.newQuery(query)){
            if (resultSet.next()){
                String email = resultSet.getString("email");
                String password = resultSet.getString("pass");
                long birth = resultSet.getLong("birth");
                float height = resultSet.getFloat("heightVal");
                float weight = resultSet.getFloat("weightVal");
                ConnectionManager.endQuery(resultSet);
                return new User(nick, height, weight, birth, email, password);
            } else {
                return null;
            }
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public void saveUser(User user) {
        ConnectionManager.newQueryNoResult("INSERT INTO UserT (nickname, pass, email, heightVal, weightVal, birth) VALUES ('" +
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
        ConnectionManager.newQueryNoResult("DELETE FROM UserT WHERE (nickname = '" + nick + "');");
    }
}
