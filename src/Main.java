import com.java.entity.Leave;
import com.java.entity.Personnel;
import com.java.service.LeaveService;
import com.java.service.PersonnelService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        PersonnelService personnelService = new PersonnelService();
        LeaveService leaveService = new LeaveService();

        while (true) {
            showMenu();
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                switch (choice) {
                    case 1: // Add Personnel
                        Personnel personnel = new Personnel();

                        System.out.println("Enter name:");
                        personnel.setUsername(scanner.nextLine());

                        System.out.println("Enter nationalCode:");
                        personnel.setPersonnelCode(scanner.nextLong());
                        scanner.nextLine(); // Consume newline

                        System.out.println("Enter phone number:");
                        personnel.setMobile(scanner.nextLine());

                        System.out.println("Enter email:");
                        personnel.setEmail(scanner.nextLine());

                        personnelService.save(personnel);
                        System.out.println("Added successfully.");
                        break;

                    case 2: // List Personnel
                        System.out.println("List of personnel: ");
                        List<Personnel> personnelList = personnelService.getAllPersonnel();
                        for (Personnel p : personnelList) {
                            System.out.println(p);
                        }
                        break;

                    case 3: // Update Personnel
                        System.out.println("Update Personnel");
                        System.out.println("Enter personnelCode to update: ");
                        long personnelCodeToUpdate = scanner.nextLong();
                        scanner.nextLine(); // Consume newline

                        Optional<Personnel> existingPersonnel = personnelService.getPersonnelCode(personnelCodeToUpdate);
                        if (existingPersonnel.isPresent()) {
                            Personnel p = existingPersonnel.get();
                            System.out.println("Current Details: " + p);

                            System.out.println("Enter new username (leave blank to keep current): ");
                            String newUsername = scanner.nextLine();
                            if (!newUsername.isEmpty()) {
                                p.setUsername(newUsername);
                            }

                            System.out.println("Enter new mobile (leave blank to keep current): ");
                            String newMobile = scanner.nextLine();
                            if (!newMobile.isEmpty()) {
                                p.setMobile(newMobile);
                            }

                            System.out.println("Enter new email (leave blank to keep current): ");
                            String newEmail = scanner.nextLine();
                            if (!newEmail.isEmpty()) {
                                p.setEmail(newEmail);
                            }

                            personnelService.updatePersonnel(p);
                            System.out.println("Personnel updated successfully.");
                        } else {
                            System.out.println("Personnel not found.");
                        }
                        break;

                    case 4: // Add Leave
                        System.out.println("Enter personnelCode: ");
                        long personnelCodeForLeave = scanner.nextLong();
                        scanner.nextLine(); // Consume newline

                        Optional<Personnel> foundPersonnel = personnelService.getPersonnelCode(personnelCodeForLeave);
                        if (foundPersonnel.isPresent()) {
                            Leave leave = new Leave();
                            System.out.println("Enter start date (YYYY-MM-DD): ");
                            leave.setStartDate(LocalDate.parse(scanner.nextLine()));

                            System.out.println("Enter end date (YYYY-MM-DD): ");
                            leave.setEndDate(LocalDate.parse(scanner.nextLine()));

                            leaveService.saveLeave(leave, foundPersonnel);
                            System.out.println("Leave added successfully.");
                        } else {
                            System.out.println("Personnel not found.");
                        }
                        break;

                    case 5: // Show Leaves
                        System.out.println("List of leave: ");
                        List<Leave> leaveList = leaveService.getAllLeaves();
                        for (Leave leave : leaveList) {
                            System.out.println(leave);
                        }
                        break;
                    case 6:
                        // Show Cartesian Product
                        System.out.println("Cartesian Product of Personnel and Leaves:");
                        List<String> cartesianProduct = personnelService.getCartesianProductPersonnelLeave();
                        for (String pair : cartesianProduct) {
                            System.out.println(pair);
                        }
                        break;

                    case 7: // Exit
                        System.out.println("Exiting program.");
                        scanner.close();
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again ...");
                        break;
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void showMenu() {
        System.out.println("""  
                1. Add Personnel  
                2. List Personnel   
                3. Update Personnel   
                4. Add Leave  
                5. Show Leaves  
                6. Show Cartesian Product
                7. Exit  
                """);
    }
}