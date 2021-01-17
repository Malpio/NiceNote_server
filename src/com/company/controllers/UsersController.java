package com.company.controllers;

import com.company.models.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class UsersController {
    private Dao<User, Integer> usersDao;

    public UsersController() {
        String path = "jdbc:sqlite:./src/database.db";
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(path);
            this.usersDao = DaoManager.createDao(connectionSource, User.class);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Integer login(String email, String password) {
        try {
            List<User> users = usersDao.queryForEq("email", email);
            if (users.size() != 0 && users.get(0).getPassword().equals(password)) {
                return users.get(0).getId();
            }
            return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean registry(String firstName, String lastName, String email, String password) {
        try {
            User newUser = new User(firstName, lastName, email, password);
            usersDao.create(newUser);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
