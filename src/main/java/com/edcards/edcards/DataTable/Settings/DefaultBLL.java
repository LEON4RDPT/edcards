package com.edcards.edcards.DataTable.Settings;


import java.sql.*;
import java.sql.Date;
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

    public List<Object> getListByCustomQuery(String query) {
        Connection connection = getConnection();
        if (connection == null) {
            return null;
        }

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            List<Object> results = new ArrayList<>();
            while (resultSet.next()) {
                int columnCount = resultSet.getMetaData().getColumnCount();
                if(columnCount > 1){
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.put(resultSet.getMetaData().getColumnName(i), resultSet.getObject(i));
                    }
                    results.add(row);
                }else{
                    results.add(resultSet.getObject(1));
                }
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

    public Object getOneWhereType(String column, String identity, Object identityOb, int tipo) {
        Connection connection = getConnection();
        if (connection == null) {
            return null;
        }
        try {
            String query = "SELECT " + column + " FROM " + getTableName()
                    + formatCondition(identity, identityOb) + " AND tipo = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, tipo);
                try (ResultSet reader = statement.executeQuery()) {
                    if (reader.next()) {
                        return reader.getObject(column);
                    }
                }
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
    public List<Map<String, Object>> getAll(String columnCondition, Date objectCondition) {
        Connection connection = getConnection();
        if (connection == null) {
            return null;
        }
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM " + getTableName() + " WHERE " + columnCondition + " = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, objectCondition); // Set the date parameter
            resultSet = preparedStatement.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            List<Map<String, Object>> rows = new ArrayList<>();

            while (resultSet.next()) {
                Map<String, Object> rowMap = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    Object columnValue = resultSet.getObject(i);
                    String columnName = metaData.getColumnName(i);
                    rowMap.put(columnName, columnValue);
                }
                rows.add(rowMap);
            }

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

    public List<Integer> getUsersByTipo(int tipo) {
        Connection connection = getConnection(); // Assumes you have this method
        if (connection == null) {
            return Collections.emptyList();
        }

        String sql = "SELECT num FROM " + getTableName() + " WHERE tipo = ?"; // Use getTableName()

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, tipo);

            try (ResultSet reader = statement.executeQuery()) {
                List<Integer> userNums = new ArrayList<>();
                while (reader.next()) {
                    userNums.add(reader.getInt("num"));
                }
                return userNums;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Object> getAllWhereType(String attributeName, int tipo) {
        Connection connection = getConnection();
        if (connection == null) {
            return null;
        }

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT " + attributeName + " FROM " + getTableName() + " WHERE tipo = ?")) {

            statement.setInt(1, tipo); // Set tipo as int
            try (ResultSet reader = statement.executeQuery()) {

                List<Object> rows = new ArrayList<>();
                while (reader.next()) {
                    rows.add(reader.getObject(attributeName)); // Get value from specified column
                }
                return rows;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle this appropriately in production code
            return null;
        }
    }

    public List<Object> getAllWhereType(String attributeName, String stringCondition, Object obCondition) {
        Connection connection = getConnection();
        if (connection == null) {
            return null;
        }

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT " + attributeName + " FROM " + getTableName() + formatCondition(stringCondition, obCondition));) {

            try (ResultSet reader = statement.executeQuery()) {

                List<Object> rows = new ArrayList<>();
                while (reader.next()) {
                    rows.add(reader.getObject(attributeName)); // Get value from specified column
                }
                return rows;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle this appropriately in production code
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

    public void updateRow(String column, Object newValue, String conditionColumn, Object conditionValue) {
        Connection connection = getConnection();
        if (connection == null) {
            return; // Handle the connection failure
        }

        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE " + getTableName() + " SET " + column + " = ? WHERE " + conditionColumn + " = ?")) {

            statement.setObject(1, newValue);
            statement.setObject(2, conditionValue);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error properly in a production environment
        }
    }

    public void setOneCustom(String column, Object columnValue, String columnCondition, Object columnConditionValue) {
        Connection connection = getConnection();
        if (connection == null) {
            return;
        }

        try {
            // 1. Retrieve User Type
            String userTypeSql = "SELECT tipo FROM " + getTableName() + " WHERE " + columnCondition + " = ?";
            try (PreparedStatement userTypeStmt = connection.prepareStatement(userTypeSql)) {
                userTypeStmt.setObject(1, columnConditionValue);
                try (ResultSet rs = userTypeStmt.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException("User not found."); // Or handle as appropriate
                    }
                    String userType = rs.getString("tipo");

                    // 2. Perform Validation (Only for 'num' column)
                    if (column.equals("num")) {
                        String validationSql;
                        if (userType.equals("aluno")) {
                            validationSql = "SELECT COUNT(*) FROM usuario WHERE num = ? AND tipo = 'aluno' AND id <> ?";
                        } else {
                            validationSql = "SELECT COUNT(*) FROM usuario WHERE num = ? AND (tipo = 'admin' OR tipo = 'funcionario') AND id <> ?";
                        }

                        try (PreparedStatement validationStmt = connection.prepareStatement(validationSql)) {
                            validationStmt.setObject(1, columnValue);
                            validationStmt.setObject(2, columnConditionValue);

                            try (ResultSet validationRs = validationStmt.executeQuery()) {
                                validationRs.next();
                                int count = validationRs.getInt(1);
                                if (count > 0) {
                                    throw new SQLException("Número já existe para outro usuário do mesmo tipo.");
                                }
                            }
                        }
                    }
                }
            }

            // 3. Update if Validation Passes
            String sql = "UPDATE " + getTableName() + " SET " + column + " = ? " + formatCondition(columnCondition, columnConditionValue);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setObject(1, columnValue);
                statement.executeUpdate();
            }

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
            String testsql = "SELECT COUNT(*) FROM usuario WHERE num = ? AND tipo = 'aluno'\"";
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

    public boolean hasRows(String column, Date columnDate) {
        Connection connection = getConnection();
        if (connection == null) {
            return false;
        }
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM " + getTableName() + " WHERE " + column + " = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, columnDate); // Set the date parameter
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
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
