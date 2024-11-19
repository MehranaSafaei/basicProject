package com.java.utils;


import com.java.entity.Leave;
import com.java.entity.Personnel;

import java.util.*;

public class DataStore {

    private static final List<Personnel> personnelList = new ArrayList<>();
    private static final Map<Personnel, List<Leave>> leaveMap = new HashMap<>();


    public void addPersonnel(Personnel personal) {
        personnelList.add(personal);
    }

    public List<Personnel> findAll() {
        return new ArrayList<>(personnelList);
    }

    public void addLeave(Leave leave, Personnel personnel) {
        Personnel person = findByPersonnelCode(personnel.getPersonnelCode());
        if (person != null) {
            leaveMap.putIfAbsent(person, new ArrayList<>());
            leaveMap.get(person).add(leave);
            if (person.getLeaveList() == null || person.getLeaveList().isEmpty()) {
                System.out.println(Collections.singletonList(leave));
            } else {
                person.getLeaveList().add(leave);
            }
            System.out.println("leave added successfully");
        } else {
            System.out.println("personnel not found with personnelCode: " + personnel.getPersonnelCode());
        }
    }


    public Personnel findByPersonnelCode(Long personnelCode) {
        for (Personnel personnel : personnelList) {
            if (personnel.getPersonnelCode().equals(personnelCode)) {
                return personnel;
            } else
                System.out.println("not found");

        }
        return null;
    }
//
//        public List<Leave> findLeavesByPersonnel(Personnel personnel) {
//            for (Leave leave : leaveMap.get(personnel)) {
//                if (leave.getPersonnel().equals(personnel)) {
//                    return leaveMap.get(personnel);
//                }
//            }
//            return null;
//        }

    //    public List<Leave> findLeavesByPersonnel(Personnel personnel) {
    //        return null;
    //    }




      /*  public static Personnel findPersonnel(Long id) {
            for (Personnel personnel : personnelList) {
                if (personnel.getId() == id) {
                    return personnel;
                }
            }
            return null;

        } */
}
