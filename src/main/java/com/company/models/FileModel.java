package com.company.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "files")
public class FileModel {
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    private Integer id;

    @DatabaseField(foreign = true,foreignAutoCreate = true, foreignAutoRefresh = true)
    private UserModel user;

    @DatabaseField(canBeNull = false)
    private String fileName;

    public FileModel(UserModel user, String fileName) {
        this.user = user;
        this.fileName = fileName;
    }
    public FileModel() {}

    public Integer getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public UserModel getUser() {
        return user;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
