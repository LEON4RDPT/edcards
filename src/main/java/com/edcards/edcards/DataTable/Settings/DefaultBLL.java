package com.edcards.edcards.DataTable.Settings;


import java.sql.*;
import java.util.*;

public class DefaultBLL extends DAL {
    public DefaultBLL(String cat) {
        super(cat);
    }

    public void deleteOneRow(String identity, Object identityObject) {
        Connection connection = getConnection();
        if (connection == null) {
            return;
        }
        try {
            String querry = "DELETE FROM " + getTableName() + " WHERE " + formatCondition(identity, identityObject);
            var reader = connection.createStatement().executeQuery(querry);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public List<Object> getList(String column, String identity, Object identityOb) {
        Connection connection = getConnection();
        if (connection == null) {
            return null;
        }
        try {
            String query = "SELECT " + column + " FROM " + getTableName() + formatCondition(identity, identityOb);
            var statement = connection.createStatement();
            var reader = statement.executeQuery(query);
            List<Object> results = new ArrayList<>();
            while (reader.next()) {
                results.add(reader.getObject(column));
            }
            return results;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object getOne(String column, String identity, Object identityOb) {
        Connection connection = getConnection();
        if (connection == null) {
            return null;
        }
        try {
            String query = "SELECT " + column + " FROM " + getTableName() + formatCondition(identity, identityOb);
            var reader = getConnection().createStatement().executeQuery(query);
            if (reader.next()) {
                return reader.getObject(column);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Object> getAllinOne(String columnCondition, Object objectCondition) {
        Connection connection = getConnection();
        if (connection == null) {
            return null;
        }

        try {
            String query = "SELECT * FROM " + getTableName() + formatCondition(columnCondition, objectCondition);
            var reader = getConnection().createStatement().executeQuery(query);

            ResultSetMetaData metaData = reader.getMetaData();
            int columnCount = metaData.getColumnCount();

            Map<String, Object> rowMap = new HashMap<>();

            if (reader.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    Object columnValue = reader.getObject(i);
                    String columnName = metaData.getColumnName(i);

                    rowMap.put(columnName, columnValue);
                }
            }

            reader.close();
            return rowMap;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Map<String, Object>> getAll(String columnCondition, Object objectCondition) {
        Connection connection = getConnection();
        if (connection == null) {
            return null;
        }

        try {
            String query = "SELECT * FROM " + getTableName() + formatCondition(columnCondition, objectCondition);
            var reader = getConnection().createStatement().executeQuery(query);

            ResultSetMetaData metaData = reader.getMetaData();
            int columnCount = metaData.getColumnCount();

            List<Map<String, Object>> rows = new ArrayList<>();

            while (reader.next()) {
                Map<String, Object> rowMap = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    Object columnValue = reader.getObject(i);
                    String columnName = metaData.getColumnName(i);
                    rowMap.put(columnName, columnValue);
                }
                rows.add(rowMap);
            }

            reader.close();
            return rows;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Object> getAll(String atributeName, String joinTableSQL) {
        Connection connection = getConnection();
        if (connection == null) {
            return null;
        }

        try {
            String query = "SELECT " + atributeName + " FROM " + getTableName() + " " + joinTableSQL;
            var reader = getConnection().createStatement().executeQuery(query);

            ResultSetMetaData metaData = reader.getMetaData();
            int columnCount = metaData.getColumnCount();

            List<Object> rows = new ArrayList<>();

            while (reader.next()) {
                rows.add(reader.getObject("codigo"));
            }

            reader.close();
            return rows;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Object> getAll(String atributeName) {
        Connection connection = getConnection();
        if (connection == null) {
            return null;
        }

        try {
            String query = "SELECT " + atributeName + " FROM " + getTableName();
            var reader = getConnection().createStatement().executeQuery(query);

            ResultSetMetaData metaData = reader.getMetaData();
            int columnCount = metaData.getColumnCount();

            List<Object> rows = new ArrayList<>();

            while (reader.next()) {
                rows.add(reader.getObject("codigo"));
            }

            reader.close();
            return rows;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Map<String, Object>> getAll() {
        Connection connection = getConnection();
        if (connection == null) {
            return null;
        }

        try {
            String query = "SELECT * FROM " + getTableName();
            var reader = getConnection().createStatement().executeQuery(query);

            ResultSetMetaData metaData = reader.getMetaData();
            int columnCount = metaData.getColumnCount();

            List<Map<String, Object>> rows = new ArrayList<>();

            while (reader.next()) {
                Map<String, Object> rowMap = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    Object columnValue = reader.getObject(i);
                    String columnName = metaData.getColumnName(i);
                    rowMap.put(columnName, columnValue);
                }
                rows.add(rowMap);
            }

            reader.close();
            return rows;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Map<String, Object>> getAllOrdered(String orderByColumn, String sortOrder) {
        Connection connection = getConnection();
        if (connection == null) {
            return null;
        }

        try {
            // Construct the query with ORDER BY clause
            String query = "SELECT * FROM " + getTableName() + " ORDER BY " + orderByColumn + " " + sortOrder;
            var reader = connection.createStatement().executeQuery(query);

            ResultSetMetaData metaData = reader.getMetaData();
            int columnCount = metaData.getColumnCount();

            List<Map<String, Object>> rows = new ArrayList<>();

            while (reader.next()) {
                Map<String, Object> rowMap = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    Object columnValue = reader.getObject(i);
                    String columnName = metaData.getColumnName(i);
                    rowMap.put(columnName, columnValue);
                }
                rows.add(rowMap);
            }

            reader.close();
            return rows;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Map<String, Object>> getAllOrdered(String orderByColumn, String sortOrder, Object obCondition, String ColCondition) {
        Connection connection = getConnection();
        if (connection == null) {
            return null;
        }

        try {
            // Construct the query with ORDER BY clause
            String query = "SELECT * FROM " + getTableName() + " " + DefaultBLL.formatDifCondition(orderByColumn, obCondition) + " ORDER BY " + orderByColumn + " " + sortOrder;
            var reader = connection.createStatement().executeQuery(query);

            ResultSetMetaData metaData = reader.getMetaData();
            int columnCount = metaData.getColumnCount();

            List<Map<String, Object>> rows = new ArrayList<>();

            while (reader.next()) {
                Map<String, Object> rowMap = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    Object columnValue = reader.getObject(i);
                    String columnName = metaData.getColumnName(i);
                    rowMap.put(columnName, columnValue);
                }
                rows.add(rowMap);
            }

            reader.close();
            return rows;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteALL() {
        Connection connection = getConnection();
        if (connection == null) {
            return;
        }

        try {
            String sql = "DELETE FROM " + getTableName();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String column, Object columnOb) {
        Connection connection = getConnection();
        if (connection == null) {
            return;
        }
        try {
            String sql = "DELETE FROM " + getTableName() + formatCondition(column, columnOb);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setOne(String column, Object columnOb, String columnCondition, Object columnConditionOb) {
        Connection connection = getConnection();
        if (connection == null) {
            return;
        }
        try {
            String sql = "UPDATE " + getTableName() + " SET " + column + " = ? " + formatCondition(columnCondition, columnConditionOb);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, columnOb);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void setOne(String column, Object columnOb) {
        Connection connection = getConnection();
        if (connection == null) {
            return;
        }
        try {
            String sql = "UPDATE " + getTableName() + " SET " + column + " = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void insert(Map<String, Object> columnValues) {
        Connection connection = getConnection();
        if (connection == null) {
            return;
        }
        try {
            StringBuilder columns = new StringBuilder();
            StringBuilder values = new StringBuilder();

            for (String column : columnValues.keySet()) {
                columns.append(column).append(", ");
                values.append("?, ");
            }
            columns.setLength(columns.length() - 2);
            values.setLength(values.length() - 2);

            String sql = "INSERT INTO " + getTableName() + " (" + columns + ") VALUES (" + values + ")";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set parameter values
            int index = 1;
            for (Object value : columnValues.values()) {
                statement.setObject(index++, value);
            }

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int insertAndGetId(Map<String, Object> columnValues) {
        Connection connection = getConnection();
        if (connection == null) {
            return 0;
        }
        try {
            int idRow = 0;
            StringBuilder columns = new StringBuilder();
            StringBuilder values = new StringBuilder();

            for (String column : columnValues.keySet()) {
                columns.append(column).append(", ");
                values.append("?, ");
            }
            columns.setLength(columns.length() - 2);
            values.setLength(values.length() - 2);

            String sql = "INSERT INTO " + getTableName() + " (" + columns + ") VALUES (" + values + ")";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Set parameter values
            int index = 1;
            for (Object value : columnValues.values()) {
                statement.setObject(index++, value);
            }

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                idRow = rs.getInt(1);
            }

            statement.close();
            return idRow;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean hasRows(String column, Object columnOb) {
        Connection connection = getConnection();
        if (connection == null) {
            return false;
        }
        try {
            String query = "SELECT * FROM " + getTableName() + formatCondition(column, columnOb);
            var reader = getConnection().createStatement().executeQuery(query);
            return reader.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasObject(String column, Object columnOb, String identity, Object idIdentity) {
        Connection connection = getConnection();
        if (connection == null) {
            return false;
        }
        try {
            Dictionary<String, Object> condicions = new Hashtable<>();
            condicions.put(column, columnOb);
            condicions.put(identity, idIdentity);
            String query = "SELECT * FROM " + getTableName() + formatConditions(condicions);
            var reader = getConnection().createStatement().executeQuery(query);
            return reader.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
