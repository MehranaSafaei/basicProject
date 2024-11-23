package com.java.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class ConnectDao<T> {

    private DbConnection dbConnection; // استفاده از DbConnection
    protected Connection connection;

    public ConnectDao() throws ClassNotFoundException, SQLException {
        dbConnection = new DbConnection(); // ایجاد نمونه DbConnection
        connection = dbConnection.getConnection(); // دریافت اتصال از استخر
    }

    public abstract Optional<T> insert(T entity) throws SQLException;

    public abstract List<String> getCartesianProductPersonnelLeave() throws SQLException;

    public abstract T update(T entity) throws SQLException;

    public abstract void delete(long id) throws SQLException;

    public abstract List<T> getAll() throws SQLException;

    public abstract Optional<T> getById(long id) throws SQLException;

    // متد برای آزادکردن اتصال
    protected void releaseConnection() {
        if (connection != null) {
            dbConnection.releaseConnection(connection);
        }
    }
}