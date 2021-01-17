package com.company.server;

import com.company.controllers.UsersController;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class NiceNoteServer extends UnicastRemoteObject implements INiceNoteServer {
    UsersController usersController;

    public NiceNoteServer() throws RemoteException {
        super();
        usersController = new UsersController();
    }

    @Override
    public Integer login(String email, String password) throws RemoteException {
        return usersController.login(email, password);
    }

    @Override
    public boolean registry(String firstName, String lastName, String email, String password) throws RemoteException {
        return usersController.registry(firstName, lastName, email, password);
    }
}
