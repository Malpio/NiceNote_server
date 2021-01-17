package com.company;

import com.company.models.User;
import com.company.server.INiceNoteServer;
import com.company.server.NiceNoteServer;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;


public class Main {

    public static void main(String[] args) throws SQLException, RemoteException {
        INiceNoteServer server = new NiceNoteServer();
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("NiceNoteServer", server);
//        String path = "jdbc:sqlite:./src/database.db";
//        ConnectionSource connectionSource = new JdbcConnectionSource(path);
//        Dao<User, Integer> accountDao = DaoManager.createDao(connectionSource, User.class);
//
//        TableUtils.createTable(connectionSource, User.class);
    }
}
