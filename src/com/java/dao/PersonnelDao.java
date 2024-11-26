package com.java.dao;

import com.java.entity.Personnel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonnelDao extends ConnectDao<Personnel> {

    // SQL queries
    private static final String INSERT = "INSERT INTO personnel (personnelCode, username, mobile, email) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE personnel SET username = ?, mobile = ?, email = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM personnel WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM personnel";
    private static final String SELECT_BY_ID = "SELECT * FROM personnel WHERE id = ?";
    private static final String SELECT_BY_USERNAME = "SELECT * FROM personnel WHERE username = ?";
    private static final String SELECT_BY_CARTESIAN = "SELECT p.username, l.startDate, l.endDate FROM personnel as p CROSS JOIN leaves as l";
    private static final String SELECT_BY_PERSONNEL_CODE = "SELECT * FROM personnel WHERE personnelCode = ?";

    public PersonnelDao() throws ClassNotFoundException, SQLException {
        super();
    }

    @Override
    public Optional<Personnel> insert(Personnel entity) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, entity.getPersonnelCode());
            statement.setString(2, entity.getUsername());
            statement.setString(3, entity.getMobile());
            statement.setString(4, entity.getEmail());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                    return Optional.of(entity);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<String> getCartesianProductPersonnelLeave() throws SQLException {
        return List.of();
    }

    public Optional<Personnel> findPersonnelByUsername(String username) throws SQLException {
        Personnel personnel = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_USERNAME)) {

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                personnel = new Personnel();
                personnel.setId(resultSet.getLong("id"));
                personnel.setUsername(resultSet.getString("username"));
                personnel.setMobile(resultSet.getString("mobile"));
                personnel.setEmail(resultSet.getString("email"));
                personnel.setPersonnelCode(resultSet.getLong("personnelCode"));
                System.out.println("Personnel found: " + personnel.getUsername()); // for log
            } else {
                System.out.println("No personnel found with username: " + username); // log for not found personnel
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(personnel);
    }


    @Override
    public List<String> findCartesianProductPersonnelLeave() throws SQLException {
        List<String> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_CARTESIAN);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String personnelLeavePair = String.format("Personnel: %s, Leave: %s to %s",
                        resultSet.getString("username"),
                        resultSet.getDate("startDate"),
                        resultSet.getDate("endDate"));
                result.add(personnelLeavePair);
            }
        }
        return result;
    }


    @Override
    public Personnel update(Personnel entity) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, entity.getUsername());
            statement.setString(2, entity.getMobile());
            statement.setString(3, entity.getEmail());
            statement.setLong(4, entity.getId());
            statement.executeUpdate();
            return entity;
        }
    }

    @Override
    public void delete(long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public List<Personnel> getAll() throws SQLException {
        List<Personnel> personnelList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Personnel personnel = new Personnel();
                personnel.setId(resultSet.getLong("id"));
                personnel.setPersonnelCode(resultSet.getLong("personnelCode"));
                personnel.setUsername(resultSet.getString("username"));
                personnel.setMobile(resultSet.getString("mobile"));
                personnel.setEmail(resultSet.getString("email"));
                personnelList.add(personnel);
            }
        }
        return personnelList;
    }

    @Override
    public Optional<Personnel> getById(long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Personnel personnel = new Personnel();
                    personnel.setId(resultSet.getLong("id"));
                    personnel.setPersonnelCode(resultSet.getLong("personnelCode"));
                    personnel.setUsername(resultSet.getString("username"));
                    personnel.setMobile(resultSet.getString("mobile"));
                    personnel.setEmail(resultSet.getString("email"));
                    return Optional.of(personnel);
                }
            }
        }
        return Optional.empty();
    }
    public Optional<Personnel> findByPersonnelCode(Long personnelCode) {
        Personnel personnel = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_PERSONNEL_CODE)) {

            preparedStatement.setLong(1, personnelCode);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                personnel = new Personnel();
                personnel.setId(resultSet.getLong("id"));
                personnel.setPersonnelCode(resultSet.getLong("personnelCode"));
                personnel.setUsername(resultSet.getString("username"));
                personnel.setMobile(resultSet.getString("mobile"));
                personnel.setEmail(resultSet.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Logging the exception
        }

        return Optional.ofNullable(personnel);  // Return Optional directly
    }


    //    private final DbConnection dbConnection = new DbConnection();
    //    private final LeaveDao leaveDao = new LeaveDao();
    //
    //    public void add(Personnel personnel) {
    //        String INSERT = "INSERT INTO personnel (personnelCode, username, mobile, email) VALUES (?, ?, ?, ?)";
    //        try (Connection connection = dbConnection.getConnection();
    //             PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
    //
    //            preparedStatement.setLong(1, personnel.getPersonnelCode());
    //            preparedStatement.setString(2, personnel.getUsername());
    //            preparedStatement.setString(3, personnel.getMobile());
    //            preparedStatement.setString(4, personnel.getEmail());
    //
    //            preparedStatement.executeUpdate();
    //
    //            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
    //            if (generatedKeys.next()) {
    //                personnel.setId(generatedKeys.getLong(1));
    //            }
    //
    //            System.out.println("Personnel added successfully.");
    //        } catch (SQLException e) {
    //            e.printStackTrace();
    //        }
    //    }
    //
    //
    //
    //    public List<Personnel> findAllPersonnel() {
    //        String SELECT_ALL = "SELECT * FROM personnel";
    //        List<Personnel> personnelList = new ArrayList<>();
    //
    //        try (Connection connection = dbConnection.getConnection();
    //             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
    //             ResultSet resultSet = preparedStatement.executeQuery()) {
    //
    //            while (resultSet.next()) {
    //                Personnel personnel = new Personnel();
    //                personnel.setId(resultSet.getLong("id"));
    //                personnel.setPersonnelCode(resultSet.getLong("personnelCode"));
    //                personnel.setUsername(resultSet.getString("username"));
    //                personnel.setMobile(resultSet.getString("mobile"));
    //                personnel.setEmail(resultSet.getString("email"));
    ////                personnel.getLeaveList(leaveDao.getAllLeave());
    //                personnelList.add(personnel);
    //            }
    //
    //        } catch (SQLException e) {
    //            e.printStackTrace();
    //        }
    //
    //        return personnelList;
    //    }
    //
    //    public Personnel update(Personnel personnel) {
    //        String UPDATE = "UPDATE personnel SET username = ?, mobile = ?, email = ? WHERE personnelCode = ?";
    //        try (Connection connection = dbConnection.getConnection();
    //             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
    //            preparedStatement.setString(1, personnel.getUsername());
    //            preparedStatement.setString(2, personnel.getMobile());
    //            preparedStatement.setString(3, personnel.getEmail());
    //            preparedStatement.setLong(4, personnel.getPersonnelCode());
    //
    //            int rowsUpdated = preparedStatement.executeUpdate();
    //
    //            if (rowsUpdated > 0) {
    //                System.out.println("Personnel updated successfully.");
    //            } else {
    //                System.out.println("No personnel found with the given personnelCode.");
    //            }
    //        } catch (SQLException e) {
    //            e.printStackTrace();
    //        }
    //        return personnel;
    //    }
    //
    //

    //
    //        public Personnel findByPersonnelCode(Long personnelCode) {
    //            return null;
    //        }

    //        public Personnel findByPersonnelCode(long personnelCode) {
    //            return dataStore.findByPersonnelCode(personnelCode);
    //        }


    //    public Personnel findByUserName(String userName) {
    //        return dataStore.findByUserName(userName);
    //    }
 //   public Optional<Personnel> findByPersonnelByUsername(String username) throws SQLException {
//        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_USERNAME)) {
//            statement.setString(1, username);
//            try (ResultSet resultSet = statement.executeQuery()) {
//                if (resultSet.next()) {
//                    Personnel personnel = new Personnel();
//                    personnel.setId(resultSet.getLong("id"));
//                    personnel.setUsername(resultSet.getString("username"));
//                    personnel.setMobile(resultSet.getString("mobile"));
//                    personnel.setEmail(resultSet.getString("email"));
//                    personnel.setPersonnelCode(resultSet.getLong("personnelCode"));
//                    return Optional.of(personnel);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return Optional.empty();
//    }


}











