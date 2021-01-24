package com.company.controllers;

import com.company.common.User;
import com.company.models.UserModel;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class UsersController {
    private Dao<UserModel, Integer> usersDao;

    public UsersController() {
        String path = "jdbc:sqlite:./src/main/java/database.db";
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(path);
            this.usersDao = DaoManager.createDao(connectionSource, UserModel.class);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public User login(String email, String password) {
        try {
            List<UserModel> users = usersDao.queryForEq("email", email);
            if (users.size() != 0 && users.get(0).getPassword().equals(password)) {
                UserModel user = users.get(0);
                return new User(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
            }
            return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean registry(String firstName, String lastName, String email, String password) {
        try {
            if (!checkEmail(email)) {
                return false;
            }
            UserModel newUser = new UserModel(firstName, lastName, email, password);
            usersDao.create(newUser);
            Integer id = newUser.getId();
            String mainPath = "./files/";
            File theDir = new File(mainPath);
            if(!theDir.exists())
                theDir.mkdir();

            String path = "./files/user_" + id;
            theDir = new File(path);
            return theDir.mkdir();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public UserModel getUser(Integer id) {
        try {
            return usersDao.queryForEq("id", id).get(0);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean checkEmail(String email) throws SQLException {
        return usersDao.queryForEq("email", email).isEmpty();
    }
}
