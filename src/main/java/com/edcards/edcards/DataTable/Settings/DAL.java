package com.edcards.edcards.DataTable.Settings;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Dictionary;
import java.util.Properties;

public class DAL {
    private String CONNECTION_STRING;
    private String USERNAME;
    private String PASSWORD;

    private Connection connection = null;
    private String tableName = null;

    private void loadDatabaseProperties() throws IOException {
        Properties properties = new Properties();
        try (InputStream is = getClass().getResourceAsStream("/settings.properties")) {
            properties.load(is);
            CONNECTION_STRING = properties.getProperty("database.url");
            USERNAME = properties.getProperty("database.username");
            PASSWORD = properties.getProperty("database.password");
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        }
    }

    public DAL(String tableName) {
        try {
            loadDatabaseProperties();
            this.connection = DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
            this.tableName = tableName;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public String getTableName() {
        return this.tableName;
    }

    public static String formatCondition(String fieldName, Object value) {
        if (fieldName == null || fieldName.isBlank() || value == null)
            return "";

        var condition = " WHERE " + fieldName + " = ";
        if (value instanceof String)
            condition += "'" + value + "'";
        else if (value instanceof Boolean)
            condition += (boolean) value ? 1 : 0;
        else
            condition += value;

        return condition;
    }

    public static String formatDifCondition(String fieldName, Object value) {
        if (fieldName == null || fieldName.isBlank() || value == null)
            return "";

        var condition = " WHERE " + fieldName + " <> ";
        if (value instanceof String)
            condition += "'" + value + "'";
        else if (value instanceof Boolean)
            condition += (boolean) value ? 1 : 0;
        else
            condition += value;

        return condition;
    }

    public static String formatConditions(
            Dictionary<String, Object> conditions) {
        if (conditions.isEmpty())
            return "";

        var conditionsStr = new StringBuilder();
        conditionsStr.append(" WHERE ");

        var fields = conditions.keys();
        var count = 0;
        while (fields.hasMoreElements()) {
            if (count > 0)
                conditionsStr.append(" AND ");
            var field = fields.nextElement();
            conditionsStr
                    .append(field)
                    .append(" = ")
                    .append(formatConditionValue(conditions.get(field)));
            count++;
        }

        return conditionsStr.toString();
    }

    private static String formatConditionValue(Object value) {
        if (value instanceof String)
            return "'" + value + "'";
        if (value instanceof Boolean)
            return (boolean) value ? "1" : "0";
        return value.toString();
    }


}
