package com.company.controllers;

import com.company.common.TextFile;
import com.company.models.FileModel;
import com.company.models.UserModel;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilesController {
    private Dao<FileModel, Integer> filesDao;

    public FilesController() {
        String path = "jdbc:sqlite:./src/main/java/database.db";
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(path);
            this.filesDao = DaoManager.createDao(connectionSource, FileModel.class);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Integer createFile(UserModel user, String fileName)  {
        try {
            String path = "./files/user_" + user.getId() + "/" + fileName;
            File file = new File(path);
            FileModel fileModel = new FileModel(user, fileName);
            filesDao.create(fileModel);
            if(file.createNewFile()) {
                return fileModel.getId();
            }
            return null;
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<TextFile> getFilesList(UserModel user) {
        try {
            List<FileModel> files = filesDao.queryForEq("user_id", user);
            List<TextFile> result = new ArrayList<TextFile>();
            for(FileModel file: files) {
                result.add(new TextFile(file.getId(), file.getFileName()));
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new ArrayList<TextFile>();
        }
    }

    public String getFileContent(UserModel user, String fileName) {
        try {
            String path = "./files/user_" + user.getId() + "/" + fileName;
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            fileReader.read(chars);
            return new String(chars);
        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public void modifyFile(UserModel user, String fileName, String text) {
        try {
            String path = "./files/user_" + user.getId() + "/" + fileName;
            File file = new File(path);
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(text);
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
