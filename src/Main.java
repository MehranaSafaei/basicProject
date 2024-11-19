    import com.java.entity.Leave;
    import com.java.entity.Personnel;
    import com.java.service.LeaveService;
    import com.java.service.PersonnelService;

    import java.sql.SQLException;
    import java.time.LocalDate;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Scanner;

    public class Main {
        public static void main(String[] args) throws SQLException {
            Scanner scanner = new Scanner(System.in);
            PersonnelService personnelService = new PersonnelService();
            LeaveService leaveService = new LeaveService();

            while (true) {
                showMenu();
                try {
                    int choice = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice) {
                        case 1:
                            Personnel personnel = new Personnel();

                            System.out.println("Enter name:");
                            personnel.setUsername(scanner.nextLine());

                            System.out.println("Enter nationalCode:");
                            personnel.setPersonnelCode(scanner.nextLong());
                            scanner.nextLine();

                            System.out.println("Enter phone number:");
                            personnel.setMobile(scanner.nextLine());

                            System.out.println("Enter email:");
                            personnel.setEmail(scanner.nextLine());

                            personnelService.save(personnel);
                            System.out.println("Added successfully.");
                            break;
                        case 2:
                            System.out.println("List of personnel: ");
                            personnelService.getAllPersonnel();
                            break;
                        case 3:
                            System.out.println("Update Personnel");
                            System.out.println("Enter personnelCode to update: ");
                            long personnelCode1 = scanner.nextLong();
                            scanner.nextLine();

                            Personnel existingPersonnel = personnelService.getPersonnelCode(personnelCode1);
                            if (existingPersonnel != null) {
                                System.out.println("Current Details: " + existingPersonnel);

                                System.out.println("Enter new username: ");
                                String newUsername = scanner.nextLine();

                                System.out.println("Enter new mobile: ");
                                String newMobile = scanner.nextLine();

                                System.out.println("Enter new email: ");
                                String newEmail = scanner.nextLine();

                                existingPersonnel.setUsername(newUsername);
                                existingPersonnel.setMobile(newMobile);
                                existingPersonnel.setEmail(newEmail);

                                personnelService.updatePersonnel(existingPersonnel);
                            } else {
                                System.out.println("Personnel not found.");
                            }
                            break;

                        case 4:
                            System.out.println("Enter personnelCode: ");
                            long personnelCode = scanner.nextLong();


                            Personnel foundPersonnel = personnelService.getPersonnelCode(personnelCode);
                            if (foundPersonnel != null) {
                                Leave leave = new Leave();
                                System.out.println("Enter start date (YYYY-MM-DD): ");
                                leave.setStartDate(LocalDate.parse(scanner.next()));

                                System.out.println("Enter end date (YYYY-MM-DD): ");
                                leave.setEndDate(LocalDate.parse(scanner.next()));

                                leaveService.saveLeave(leave, foundPersonnel);
                                System.out.println("Leave added successfully.");
                            } else {
                                System.out.println("Personnel not found.");
                            }
                            break;
                        case 5:
                            System.out.println("List of leave: ");
                            List<Leave> leaveList = leaveService.getAllLeaves();
                            for (Leave leave : leaveList) {
                                System.out.println(leave);
                            }
                            break;
                        case 6:
                            System.out.println("Exiting program.");
                            scanner.close();
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again ...");
                            break;
                    }

                } catch (Exception e) {
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
                    6. Exit  
                    """);
        }
    }