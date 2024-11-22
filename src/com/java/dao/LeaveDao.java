package com.java.dao;

import com.java.entity.Leave;
import com.java.entity.Personnel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LeaveDao extends ConnectDao<Leave> {

    // Update column names based on actual database schema
    private static final String INSERT = "INSERT INTO `leave` (startDate, endDate, personelId) VALUES (?, ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM `leave`";
    private static final String SELECT_BY_PERSONNEL = "SELECT * FROM `leave` WHERE personelId = ?"; // Check if 'personnelId' is correct

    public LeaveDao() throws ClassNotFoundException, SQLException {
        // Initialize connection or any setup
    }

    public void addLeave(Leave leave, Optional<Personnel> personnel) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDate(1, Date.valueOf(leave.getStartDate(resultSet.getDate("startDate"))));
            preparedStatement.setDate(2, Date.valueOf(leave.getEndDate(resultSet.getDate("endDate"))));
            // Ensure personnel is present and safely get its id
            personnel.ifPresent(p -> {
                try {
                    preparedStatement.setLong(3, p.getId());
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Leave> getAllLeaves() {
        List<Leave> leaveList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Leave leave = new Leave();
                leave.getStartDate(resultSet.getDate("startDate").toLocalDate());
                leave.getEndDate(resultSet.getDate("endDate").toLocalDate());
                leave.setPersonnelId(resultSet.getLong("personnelId"));
                leaveList.add(leave);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaveList;
    }

    public List<Leave> findListLeaveOfPersonnel(Personnel personnel) {
        List<Leave> leaveList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_PERSONNEL)) {
            preparedStatement.setLong(1, personnel.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Leave leave = new Leave();
                    leave.setStartDate(resultSet.getDate("startDate").toLocalDate());
                    leave.setEndDate(resultSet.getDate("endDate").toLocalDate());
                    leave.setPersonnelId(resultSet.getLong("personnelId")); // Ensure this matches exactly
                    leaveList.add(leave);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaveList;
    }

    @Override
    public Optional<Leave> insert(Leave entity) throws SQLException {
        // Implement your insert logic based on your requirements
        return Optional.empty();
    }

    @Override
    public Leave update(Leave entity) throws SQLException {
        // Implement your update logic based on your requirements
        return null;
    }

    @Override
    public void delete(long id) throws SQLException {
        // Implement your delete logic based on your requirements
    }

    @Override
    public List<Leave> getAll() throws SQLException {
        return getAllLeaves();
    }

    @Override
    public Optional<Leave> getById(long id) throws SQLException {
        // Implement a method to find a leave by its ID if needed
        return Optional.empty();
    }
}