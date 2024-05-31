package com.edcards.edcards.DataTable.Settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Dictionary;

public class DAL {
    private final String CONNECTION_STRING = "jdbc:mysql://ptgui.myddns.me:3306/db_escola";
    private final String USERNAME = "Litz18";
    private final String PASSWORD = "Gui1803##";

    private Connection connection = null;
    private String tableName = null;

    public DAL(String tableName) {
        try {
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
