package model;

import model.database.DataBase;

import java.sql.SQLException;

import static utils.HtmlTextFormatter.formatContent;

public class StoredModel {
    private DataBase database;


    public void deleteSavedInfo(String title) throws SQLException {
        database.deleteEntry(title);
    }

    public Object[] getSavedTitles() throws SQLException {
        return database.getTitles().stream().sorted().toArray();
    }

    public void saveLocally(String title, String content) throws SQLException {
        database.saveInfo(title, content);
    }

    public void saveStoredInfo(String title, String extract) throws SQLException {
        database.saveInfo(title, extract);
    }

    public String getExtract(String title) throws SQLException {
        return database.getExtract(title);
    }

    public void setDatabase(DataBase database) {
        this.database = database;
    }


}
