package com.java.service;

import com.java.dao.PersonnelDao;
import com.java.entity.Personnel;


import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PersonnelService {

    private PersonnelDao personnelDao = new PersonnelDao();

    public PersonnelService() throws SQLException, ClassNotFoundException {
    }

    public void save(Personnel personnel) throws SQLException {
        personnelDao.insert(personnel);
    }

    public List<Personnel> getAllPersonnel() throws SQLException {
        List<Personnel> personnelList = personnelDao.getAll();
        for (Personnel p : personnelList) {
            System.out.println(p);
        }
        return personnelList;
    }
    public Personnel updatePersonnel(Personnel personnel) throws SQLException {
        return personnelDao.update(personnel);
    }


    public Optional<Personnel> getPersonnelCode(Long personnelCode) {
        return personnelDao.findByPersonnelCode(personnelCode);
    }

    public List<String> getCartesianProductPersonnelLeave() throws SQLException {
        return personnelDao.findCartesianProductPersonnelLeave();
    }
}
