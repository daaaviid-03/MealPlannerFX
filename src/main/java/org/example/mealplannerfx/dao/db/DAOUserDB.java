package org.example.mealplannerfx.dao.db;

import org.example.mealplannerfx.dao.DAOIngredient;
import org.example.mealplannerfx.dao.DAOUser;
import org.example.mealplannerfx.entity.Ingredient;
import org.example.mealplannerfx.entity.User;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DAOUserDB extends DAOUser {
    /**
     * The connection to the server of the db
     */
    private final ConnectionMySQL connectionMySQL = ConnectionMySQL.getConnectionMySQLInstance();

    @Override
    public List<User> getAllUsersAsRegex(String regex) {
        List<User> users = new ArrayList<>();
        try {
            String query = "SELECT * FROM UserT WHERE nickname RLIKE '" + regex + "';";
            ResultSet resultSet = connectionMySQL.newQuery(query);
            while (resultSet.next()){
                String nick = resultSet.getString("nickname");
                String email = resultSet.getString("email");
                String password = resultSet.getString("pass");
                long birth = resultSet.getLong("birth");
                float height = resultSet.getFloat("heightVal");
                float weight = resultSet.getFloat("weightVal");
                users.add(new User(nick, height, weight, birth, email, password));
            }
            connectionMySQL.endQuery(resultSet);
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
        return users;
    }

    @Override
    public void saveUser(User user, boolean newUser) {
        connectionMySQL.newQueryNoResult("INSERT INTO UserT (nickname, pass, email, heightVal, weightVal, birth) VALUES ('" +
                user.getNickname() + "', '" + user.getPassword() + "', '" + user.getEmail() + "', " +
                user.getHeight() + ", " + user.getWeight() + ", " + user.getBirth() + ") ON DUPLICATE KEY UPDATE pass = '" +
                user.getPassword() + "', email = '" + user.getEmail() + "', heightVal = " + user.getHeight() +
                ", weightVal = " + user.getWeight() + ", birth = " + user.getBirth() + ";");
    }

    @Override
    public void deleteUser(String nick) {
        connectionMySQL.newQueryNoResult("DELETE FROM UserT WHERE (nickname = '" + nick + "');");
    }
}
