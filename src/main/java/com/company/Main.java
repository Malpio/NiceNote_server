package com.company;

import com.company.models.FileModel;
import com.company.models.UserModel;
import com.company.server.INiceNoteServer;
import com.company.server.NiceNoteServer;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;


public class Main {

    public static void main(String[] args) {
        try {
            createTables();
            runServer();
        } catch (RemoteException | SQLException ex) {
            try {
                runServer();
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
        }
    }

    public static void runServer() throws RemoteException {
        INiceNoteServer server = new NiceNoteServer();
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("NiceNoteServer", server);
    }

    public static void createTables() throws SQLException {
        String path = "jdbc:sqlite:./src/main/java/database.db";
        ConnectionSource connectionSource = new JdbcConnectionSource(path);

        TableUtils.createTable(connectionSource, UserModel.class);
        TableUtils.createTable(connectionSource, FileModel.class);
    }
}
