    package com.java.dao;

    import com.java.entity.Leave;
    import com.java.entity.Personnel;

    import java.sql.*;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Optional;

    public class LeaveDao extends ConnectDao<Leave> {

        // SQL queries
        private static final String INSERT = "INSERT INTO leaves (startDate, endDate, personelId) VALUES (?, ?, ?)";
        private static final String SELECT_ALL = "SELECT * FROM leaves";
        private static final String SELECT_BY_PERSONNEL = "SELECT l.startDate, l.endDate, l.personelId \n" +
                "FROM leaves l \n" +
                "JOIN personnel p ON l.personelId = p.id \n" +
                "WHERE p.username = ?\n";

        public LeaveDao() throws ClassNotFoundException, SQLException {
            super();
        }

        public void addLeave(Leave leave, Optional<Personnel> personnel) throws SQLException {
            if (personnel.isEmpty()) {
                throw new IllegalArgumentException("Personnel cannot be null.");
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setDate(1, Date.valueOf(leave.getStartDate()));
                preparedStatement.setDate(2, Date.valueOf(leave.getEndDate()));
                preparedStatement.setLong(3, personnel.get().getId());
                preparedStatement.executeUpdate();
            }
        }



        public List<Leave> getAllLeaves() throws SQLException {
            List<Leave> leaveList = new ArrayList<>();
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Leave leave = new Leave();
                    leave.setStartDate(resultSet.getDate("startDate").toLocalDate());
                    leave.setEndDate(resultSet.getDate("endDate").toLocalDate());
                    leave.setPersonnelId(resultSet.getLong("personelId"));
                    leaveList.add(leave);
                }
            }
            return leaveList;
        }

        public List<Leave> findListLeaveOfPersonnel(Personnel personnel) throws SQLException {
            List<Leave> leaveList = new ArrayList<>();
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_PERSONNEL)) {
                preparedStatement.setString(1,personnel.getUsername());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Leave leave = new Leave();
                        leave.setStartDate(resultSet.getDate("startDate").toLocalDate());
                        leave.setEndDate(resultSet.getDate("endDate").toLocalDate());
                        leave.setPersonnelId(resultSet.getLong("personelId"));
                        leaveList.add(leave);
                    }
                }
            }
            return leaveList;
        }

        @Override
        public Optional<Leave> insert(Leave entity) {
            // Not implemented
            return Optional.empty();
        }

        @Override
        public List<String> getCartesianProductPersonnelLeave() throws SQLException {
            return List.of();
        }

        @Override
        public List<String> findCartesianProductPersonnelLeave() throws SQLException {
            return List.of();
        }

        @Override
        public Leave update(Leave entity) {
            // Not implemented
            return null;
        }

        @Override
        public void delete(long id) {
            // Not implemented
        }

        @Override
        public List<Leave> getAll() throws SQLException {
            return getAllLeaves();
        }

        @Override
        public Optional<Leave> getById(long id) {
            // Not implemented
            return Optional.empty();
        }
    }
