package com.java.dao;

import com.java.entity.Leave;
import com.java.entity.Personnel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeaveDao {

    private DbConnection dbConnection = new DbConnection();

    public void addLeave(Leave leave, Personnel personnel) {
        String sql = "INSERT INTO `leave` (startDate, endDate, personelId) VALUES (?, ?, ?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(1, Date.valueOf(leave.getStartDate()));
            preparedStatement.setDate(2, Date.valueOf(leave.getEndDate()));
            preparedStatement.setLong(3, personnel.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Leave> findAllLeaves() {
        String sql = "SELECT * FROM `leave`";
        List<Leave> leaveList = new ArrayList<>();

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Leave leave = new Leave();
                leave.setStartDate(resultSet.getDate("startDate").toLocalDate());
                leave.setEndDate(resultSet.getDate("endDate").toLocalDate());
                leave.setPersonnelId(resultSet.getLong("personelId"));
                leaveList.add(leave);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return leaveList;
    }

    public List<Leave> findListLeaveOfPersonnel(Personnel personnel) throws SQLException {
        String sql = "SELECT * FROM `leave` WHERE `leave`.personnelId = ?";
        List<Leave> leaveList = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, personnel.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Leave leave = new Leave();
                leave.setStartDate(resultSet.getDate(1).toLocalDate());
                leave.setEndDate(resultSet.getDate(1).toLocalDate());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaveList;
    }


}

//        public List<Leave> getLeavesByPersonnel(Personnel personnel) {
//
//    }


//    public List<Leave> getLeavesByPersonnel(Personnel personnel) {
//        return dataStore.findLeavesByPersonnel(personnel);
//
//    }


