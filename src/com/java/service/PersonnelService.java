package com.java.service;

import com.java.dao.PersonnelDao;
import com.java.entity.Personnel;
import com.java.utils.DataStore;


import java.util.List;

public class PersonnelService {

    private PersonnelDao personnelDao = new PersonnelDao();

    public void save(Personnel personnel) {
        personnelDao.add(personnel);
    }

    public List<Personnel> getAllPersonnel() {
        List<Personnel> personnelList = personnelDao.findAllPersonnel();
        for (Personnel p : personnelList) {
            System.out.println(p);
        }
        return personnelList;
    }
    public Personnel updatePersonnel(Personnel personnel) {
        return personnelDao.update(personnel);
    }


    public Personnel getPersonnelCode(Long personnelCode) {
        return personnelDao.findByPersonnelCode(personnelCode);
    }
}
