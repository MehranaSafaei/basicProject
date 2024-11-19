package com.java.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DbConnection {

    private List<Connection> connections = new ArrayList<Connection>();
    private String URL = "jdbc:mysql://localhost:3306/mydb";
    private String USER = "root";
    private String PASSWORD = "Aa@123456";
    private int INITIAL_POOL_SIZE = 10;

    public DbConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                connections.add(createConnection());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public synchronized Connection getConnection() {
        if (connections.isEmpty()) {
            try {
                return createConnection();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return connections.remove(connections.size() - 1);
        }
    }

//    public synchronized void releaseConnection(Connection connection) {
//        if (connection != null) {
//            connections.add(connection);
//        }
//    }
//
//    public void closeAllConnections() {
//        for (Connection connection : connections) {
//            try {
//                if (connection != null && !connection.isClosed()) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                System.out.println("Error closing connection: " + e.getMessage());
//            }
//        }
//        connections.clear();
//    }


}
