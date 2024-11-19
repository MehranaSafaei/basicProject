package com.java.dao;

import com.java.entity.Personnel;
import com.java.utils.DataStore;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonnelDao {


    private DbConnection dbConnection = new DbConnection();

    public void add(Personnel personnel) {
        String INSERT = "INSERT INTO personnel (personnelCode, username, mobile, email) VALUES (?, ?, ?, ?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setLong(1, personnel.getPersonnelCode());
            preparedStatement.setString(2, personnel.getUsername());
            preparedStatement.setString(3, personnel.getMobile());
            preparedStatement.setString(4, personnel.getEmail());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                personnel.setId(generatedKeys.getLong(1));
            }

            System.out.println("Personnel added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public List<Personnel> findAllPersonnel() {
        String SELECT_ALL = "SELECT * FROM personnel";
        List<Personnel> personnelList = new ArrayList<>();

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Personnel personnel = new Personnel();
                personnel.setId(resultSet.getLong("id"));
                personnel.setPersonnelCode(resultSet.getLong("personnelCode"));
                personnel.setUsername(resultSet.getString("username"));
                personnel.setMobile(resultSet.getString("mobile"));
                personnel.setEmail(resultSet.getString("email"));
                personnelList.add(personnel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return personnelList;
    }

    public Personnel update(Personnel personnel) {
        String UPDATE = "UPDATE personnel SET username = ?, mobile = ?, email = ? WHERE personnelCode = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, personnel.getUsername());
            preparedStatement.setString(2, personnel.getMobile());
            preparedStatement.setString(3, personnel.getEmail());
            preparedStatement.setLong(4, personnel.getPersonnelCode()); // باید personnelCode مقداردهی شده باشد.

            int rowsUpdated = preparedStatement.executeUpdate(); // متد executeUpdate را اضافه کنید

            if (rowsUpdated > 0) {
                System.out.println("Personnel updated successfully.");
            } else {
                System.out.println("No personnel found with the given personnelCode.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personnel;
    }


    public Personnel findByPersonnelCode(Long personnelCode) {
        String sql = "SELECT id,username,mobile,email,personnelCode FROM personnel WHERE personnelCode = ?";
        Personnel personnel = null;
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, personnelCode);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                personnel = new Personnel();
                personnel.setId(resultSet.getLong(1));
                personnel.setUsername(resultSet.getString(2));
                personnel.setMobile(resultSet.getString(3));
                personnel.setEmail(resultSet.getString(4));
                personnel.setPersonnelCode(resultSet.getLong(5));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return personnel;
    }
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


}











