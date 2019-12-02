package me.Hazz.HazzBot.Variables;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

import java.sql.*;
import java.util.Calendar;


public class MySQLAdapter {
    private String DB_NAME;
    private String DB_USER;
    private String DB_ADRES;
    private int DB_PORT;
    private String DB_PASSWORD;
    private Connection c;

    public MySQLAdapter(String server, int port, String databaseUser, String databasePassword, String databaseName) {
        DB_ADRES = server;
        DB_USER = databaseUser;
        DB_PASSWORD = databasePassword;
        DB_NAME = databaseName;
        DB_PORT = port;
    }

    private Connection createConnection() {

        try {
            MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
            dataSource.setUser(DB_USER);
            dataSource.setPassword(DB_PASSWORD);
            dataSource.setServerName(DB_ADRES);
            dataSource.setPort(DB_PORT);
            dataSource.setDatabaseName(DB_NAME);
            dataSource.setZeroDateTimeBehavior("convertToNull");
            dataSource.setUseUnicode(true);
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Cant connect to db");
            try {
                throw new Exception("Cant connect to db");
            } catch (Exception ex) {

            }
        }
        return null;
    }

    public Connection getConnection() {
        if (c == null) {
            c = createConnection();
        }
        try {
            if (c.isClosed())
                c = createConnection();
        } catch (Throwable t) {
        }
        return c;
    }

    public ResultSet select(String sql, Object... params) throws SQLException {
        PreparedStatement query;
        query = getConnection().prepareStatement(sql);
        resolveParameters(query, params);
        return query.executeQuery();
    }

    private void resolveParameters(PreparedStatement query, Object... params) throws SQLException {
        int index = 1;
        for (Object p : params) {
            if (p instanceof String) {
                query.setString(index, (String) p);
            } else if (p instanceof Integer) {
                query.setInt(index, (int) p);
            } else if (p instanceof Long) {
                query.setLong(index, (Long) p);
            } else if (p instanceof Double) {
                query.setDouble(index, (double) p);
            } else if (p instanceof java.sql.Date) {
                java.sql.Date d = (java.sql.Date) p;
                Timestamp ts = new Timestamp(d.getTime());
                query.setTimestamp(index, ts);
            } else if (p instanceof java.util.Date) {
                java.util.Date d = (java.util.Date) p;
                Timestamp ts = new Timestamp(d.getTime());
                query.setTimestamp(index, ts);
            } else if (p instanceof Calendar) {
                Calendar cal = (Calendar) p;
                Timestamp ts = new Timestamp(cal.getTimeInMillis());
                query.setTimestamp(index, ts);
            } else if (p == null) {
                query.setNull(index, Types.NULL);
            } else {
                try {
                    throw new Exception("Broken fucking database");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            index++;
        }
    }

    public int query(String sql, Object... params) throws SQLException {
        try (PreparedStatement query = getConnection().prepareStatement(sql)) {
            resolveParameters(query, params);
            return query.executeUpdate();
        }
    }

    public int insert(String sql, Object... params) throws SQLException {
        try (PreparedStatement query = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            resolveParameters(query, params);
            query.executeUpdate();
            ResultSet rs = query.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }
}