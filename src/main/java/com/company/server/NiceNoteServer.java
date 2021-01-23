package com.company.server;

import com.company.common.TextFile;
import com.company.controllers.FilesController;
import com.company.controllers.UsersController;
import com.company.models.UserModel;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class NiceNoteServer extends UnicastRemoteObject implements INiceNoteServer {
    UsersController usersController;
    FilesController filesController;

    public NiceNoteServer() throws RemoteException {
        super();
        usersController = new UsersController();
        filesController = new FilesController();
    }

    @Override
    public Integer login(String email, String password) throws RemoteException {
        return usersController.login(email, password);
    }

    @Override
    public boolean registry(String firstName, String lastName, String email, String password) throws RemoteException {
        return usersController.registry(firstName, lastName, email, password);
    }

    @Override
    public Integer createFile(Integer userId, String fileName) throws RemoteException {
        UserModel user = usersController.getUser(userId);
        return filesController.createFile(user, fileName);
    }

    @Override
    public List<TextFile> filesList(Integer userId) throws RemoteException {
        UserModel user = usersController.getUser(userId);
        return filesController.getFilesList(user);
    }

    @Override
    public String readFile(Integer userId, String fileName) throws RemoteException {
        UserModel user = usersController.getUser(userId);
        return filesController.getFileContent(user, fileName);
    }

    @Override
    public void writeFile(Integer userId, String fileName, String text) throws RemoteException {
        UserModel user = usersController.getUser(userId);
        filesController.modifyFile(user, fileName, text);
    }


}
