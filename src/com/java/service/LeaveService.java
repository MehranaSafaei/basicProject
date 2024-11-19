package com.java.service;

import com.java.dao.LeaveDao;
import com.java.dao.PersonnelDao;
import com.java.entity.Leave;
import com.java.entity.Personnel;

import java.sql.SQLException;
import java.util.List;

public class LeaveService {

    private final LeaveDao leaveDAO = new LeaveDao();
    private final PersonnelDao personnelDao = new PersonnelDao();


    public void saveLeave(Leave leave, Personnel personnel) {
        leaveDAO.addLeave(leave, personnel);
    }

    public List<Leave> getAllLeaves() {
        List<Leave> leaveList = leaveDAO.findAllLeaves();
        for (Leave leave : leaveList) {
            System.out.println(leaveList);
        }
        return leaveList;
    }


    public List<Leave> getListLeaveOfPersonnel(Personnel personnel) throws SQLException {
        return leaveDAO.findListLeaveOfPersonnel(personnel);
    }



//        public List<Leave> getLeavesByPersonnel(Personnel personnel) {
//            return leaveDAO.getLeavesByPersonnel(personnel);
//    }

//        public List<Leave> getAllLeave(Personnel personnel) {
//            return leaveDAO.getLeavesByPersonnel(personnel);
//        }
       /* public void saveLeave(Leave leave, Personnel personnel) {
            Personnel personnel1 = personnelDao.findByNationalCode(personnel.getNationalCode());
            if (personnel1 == null) {
                System.out.println("NationalCode not found.");
            } else {
                leave.setPersonnel(personnel1);
                leaveDAO.addLeave(leave, personnel1);
            } */

}
