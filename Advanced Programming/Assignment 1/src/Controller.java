import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Controller{
    public static Map<String, Student> students = new HashMap<>();
    private Map<String, Prof> professors = new HashMap<>();
    private Map<String, TeachingAssistant> teachingassistants = new HashMap<>();
    private final String adminEmail = "admin@gmail.com";
    private final String adminPassword = "admin";
    private User currUser = null;



    public Controller(){
        Student example1 = new Student("example1@gmail.com", "abc", 1, "Jaideep", 12, 345678);
        Student example2 = new Student("example2@gmail.com", "abc", 2, "Dahiya", 14, 123456);
        Student example3 = new Student("example3@gmail.com", "abc", 1, "JD", 19, 12345678);
        students.put("example1@gmail.com", example1);
        students.put("example2@gmail.com", example2);
        students.put("example3@gmail.com", example3);
        StudentManager.addStudent(example1);
        StudentManager.addStudent(example2);
        StudentManager.addStudent(example3);

        Prof professor1 = new Prof("example4@gmail.com", "def", "Prof. A");
        Prof professor2 = new Prof("example5@gmail.com", "def", "Prof. B");
        professors.put("example4@gmail.com", professor1);
        professors.put("example5@gmail.com", professor2);
        ProfManager.addProf(professor1);
        ProfManager.addProf(professor2);

        TeachingAssistant example6 = new TeachingAssistant("example6@gmail.com", "ghi", 6, "Rohit", 22, 12345678);
        TeachingAssistant example7 = new TeachingAssistant("example7@gmail.com", "ghi", 6, "Shivansh", 21, 23456789);
        teachingassistants.put("example6@gmail.com", example6);
        teachingassistants.put("example7@gmail.com", example7);
        TeachingAssistantManager.addTA(example6);
        TeachingAssistantManager.addTA(example7);

    }



    public boolean login() {
        boolean logged = false;
        Scanner scanner = new Scanner(System.in);
        while (!logged) {
            int Category;
            try{
                System.out.println("Login as:\n1.Student\n2.Professor\n3.Admin\n4.Teaching Assistant\n5.Back");
                Category = scanner.nextInt();
            }
            catch (InputMismatchException e){
                System.out.println("Please enter a nubmer");
                scanner.next();
                continue;
            }

            if (Category == 1) {
                logged = loginStudent();
                if(logged) {
                    menuStudent();
                    logged = false;
                }

            }
            else if (Category == 2) {
                logged = loginProf();
                if(logged) {
                    menuProf();
                    logged = false;
                }
            }
            else if (Category == 3) {
                logged = loginAdmin();
                if(logged) {
                    menuAdmin();
                    logged = false;
                }
            }
            else if (Category == 4){
                logged = loginTA();
                if(logged){
                    menuTA();
                    logged = false;
                }
            }
            else{
                logged=true;
            }
        }
        return true;
    }

    public boolean loginTA() throws  InvalidLoginException{
        Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Email");
            String email = scanner.nextLine();
            System.out.println("Enter Password");
            String password = scanner.nextLine();

            TeachingAssistant teachingAssistant = teachingassistants.get(email);

            try{
                if (teachingAssistant != null && teachingAssistant.getPassword().equals(password)) {
                    currUser = teachingAssistant;
                    System.out.println("Login success: "+teachingAssistant.getName());
                    return true;
                } else {
                    throw new InvalidLoginException();
                }
            }
            catch (InvalidLoginException e){
                System.out.println("INVALID\nException Catched : " + e);
                return false;
            }

    }

    public boolean loginStudent() throws  InvalidLoginException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Email");
        String email = scanner.nextLine();
        System.out.println("Enter Password");
        String password = scanner.nextLine();

        Student student = students.get(email);

        try{
            if (student != null && student.getPassword().equals(password)) {
                currUser = student;
                System.out.println("Login success: "+student.getName());
                return true;
            } else {
                throw new InvalidLoginException();
            }
        }
        catch (InvalidLoginException e){
            System.out.println("INVALID\nException Catched : " + e);
            return false;
        }

    }

    private boolean loginProf() {
        Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Email");
            String email = scanner.nextLine();
            System.out.println("Enter Password");
            String password = scanner.nextLine();

            Prof prof = professors.get(email);
            try{
                if (prof != null && prof.getPassword().equals(password)) {
                    currUser = prof;
                    System.out.println("Login success: " + prof.getName());
                    return true;
                }
                else {
                    throw new InvalidLoginException();
                }
            }
            catch (InvalidLoginException e){
                System.out.println("INVALID\nException Catched : " + e);
                return false;
            }
    }

    private boolean loginAdmin(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Email");
        String email = scanner.nextLine();
        System.out.println("Enter Password");
        String password = scanner.nextLine();

        try{
            if (adminEmail.equals(email) && adminPassword.equals(password)) {
                currUser = new Admin();
                System.out.println("Login success: Admin");
                return true;
            }
            else {
                throw new InvalidLoginException();
            }
        }
        catch (InvalidLoginException e){
            System.out.println("INVALID\nException Catched : " + e);
            return false;
        }
    }

    private boolean menuStudent(){
        Scanner scanner = new Scanner(System.in);
        Student student = (Student) currUser;
        boolean exit = false;
        while(exit == false){
            int opt;
            try{
                System.out.println("Enter the choice");
                System.out.println("1.View Available Courses\n2.Register for Course\n3.View Schedule");
                System.out.println("4.Track Academic Progress\n5.Drop Course\n6.Complaint\n7.Give Feedback\n8.Back");
                opt = scanner.nextInt();
            }
            catch (InputMismatchException e){
                System.out.println("<-----Please enter a nubmer----->");
                scanner.next();
                continue;
            }
            switch (opt){
                case 1:
                    student.viewCourses();
                    break;
                case 2:
                    student.register();
                    break;
                case 3:
                    student.viewSchedule();
                    break;
                case 4:
                    while(true) {
                        int choice;
                        try {
                            System.out.println("Enter the choice");
                            System.out.println("1.View Grades\n2.View CGPA\n3.Show Completed Course\n4.Back");
                            choice = scanner.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("<-----Please enter a nubmer----->");
                            scanner.next();
                            continue;
                        }
                        if (choice == 4) break;
                        else if(choice == 1) student.showGrades();
                        else if(choice == 2) student.showCgpa();
                        else if(choice == 3) student.showCompleted();
                        else System.out.println("Invalid choice");
                    }
                    break;
                case 5:
                    student.dropCourse();
                    break;
                case 6:
                    while(true){
                        System.out.println("Choose option\n1.Complaint\n2.See Complaints\n3.Back");
                        int option = scanner.nextInt();
                        if(option==3) break;
                        else if(option==1) student.complaint();
                        else if(option==2) student.showComplaints();
                        else System.out.println("Invalid");
                    }
                    break;
                case 7:
                    student.giveFeedback();
                    break;
                case 8:
                    logout();
                    exit = true;
                    break;
                default:
                    System.out.println("invalid");
            }
        }
        return false;


    }

    private void menuProf(){
        Scanner scanner = new Scanner(System.in);
        Prof professor = (Prof) currUser;
        boolean exit = false;
        while(exit == false){
            System.out.println("Enter the choice");
            System.out.println("1.Manage My Courses\n2.View Enrolled Students\n3.See Feedback\n4.Assign TA\n5.View Proposed Grades\n6.Back");
            int opt = scanner.nextInt();
            switch (opt){
                case 1:
                    while(true){
                        int choice;
                        try{
                            System.out.println();
                            System.out.println("Enter your choice");
                            System.out.println("1.View\n2.Update\n3.Back");
                            choice = scanner.nextInt();
                        }
                        catch (InputMismatchException e){
                            System.out.println("<-----Please enter a nubmer----->");
                            scanner.next();
                            continue;
                        }
                        if(choice == 3) break;
                        else if(choice == 1){
                            professor.showCourses();
                        }
                        else if(choice == 2){
                            professor.updateCourse();
                        }
                        else{
                            System.out.println("INVALID");
                        }
                    }
                    break;
                case 2:
                    professor.printEnrolledStudents();
                    break;
                case 3:
                    professor.seeFeedback();
                    break;
                case 4:
                    professor.assignTA();
                    break;
                case 5:
                    professor.seeProposedGrades();
                    break;
                case 6:
                    logout();
                    exit=true;
                    break;
            }
        }


    }



    private void menuAdmin(){
        Scanner scanner = new Scanner(System.in);
        Admin admin = (Admin) currUser;
        boolean exit = false;
        while(exit == false){
            int opt;
            try{
                System.out.println("Enter the choice");
                System.out.println("1.Manage Course Catalog\n2.Manage Student Records\n3.Assign Professors to courses");
                System.out.println("4.Handle Complaints\n5.Logout");
                opt = scanner.nextInt();
            }
            catch (InputMismatchException e){
                System.out.println("<-----Please enter a nubmer----->");
                scanner.next();
                continue;
            }
            switch (opt){
                case 1:
                    while(true){

                        int choice;
                        try{
                            System.out.println();
                            System.out.println("Enter your choice");
                            System.out.println("1.View\n2.Add\n3.Delete\n4.Back");
                            choice = scanner.nextInt();
                        }
                        catch (InputMismatchException e){
                            System.out.println("<-----Please enter a nubmer----->");
                            scanner.next();
                            continue;
                        }
                        if(choice == 4) break;
                        else if(choice == 1){
                            Course.showCourses();
                        }
                        else if(choice == 2){
                            admin.addCourse();
                        }
                        else if(choice == 3){
                            admin.adminDeleteCourse();
                        }
                        else{
                            System.out.println("INVALID");
                        }
                    }
                    break;
                case 2:
                    while(true){
                        int choice;
                        try{
                            System.out.println();
                            System.out.println("Enter your choice");
                            System.out.println("1.View\n2.Delete\n3.Assign/Update Grades\n4.Add a new Student\n5.Back");
                            choice = scanner.nextInt();
                        }
                        catch (InputMismatchException e){
                            System.out.println("<-----Please enter a nubmer----->");
                            scanner.next();
                            continue;
                        }
                        if(choice == 5) break;
                        else if(choice == 1){
                            admin.viewRecords();
                        }
                        else if(choice == 2){
                            admin.deleteRecord();
                        }
                        else if(choice == 3){
                            admin.giveGradesAdmin();
                        }
                        else if(choice == 4){
                            admin.createStudent();
                        }
                        else{
                            System.out.println("INVALID");
                        }
                    }
                    break;
                case 4:
                    while(true){
                        int choice;
                        try{
                            System.out.println();
                            System.out.println("Enter your choice");
                            System.out.println("1.View Complaints\n2.Resolve Complaints\n3.Back");
                            choice = scanner.nextInt();
                        }
                        catch (InputMismatchException e){
                            System.out.println("<-----Please enter a nubmer----->");
                            scanner.next();
                            continue;
                        }
                        if(choice == 3) break;
                        else if(choice == 1){
                            Complaints.showComplaints();
                        }
                        else if(choice == 2){
                            admin.resolveComplaint();
                        }

                        else{
                            System.out.println("INVALID");
                        }
                    }
                    break;
                case 5:
                    logout();
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }


    }


    private boolean menuTA(){
        Scanner scanner = new Scanner(System.in);
        TeachingAssistant teachingAssistant = (TeachingAssistant) currUser;
        boolean exit = false;
        while(exit == false){
            int opt;
            try{
                System.out.println("Enter the choice");
                System.out.println("1.View Available Courses\n2.Register for Course\n3.View Schedule");
                System.out.println("4.Track Academic Progress\n5.Drop Course\n6.Complaint\n7.Give Feedback\n8.TAship\n9.Back");
                opt = scanner.nextInt();
            }
            catch (InputMismatchException e){
                System.out.println("<-----Please enter a nubmer----->");
                scanner.next();
                continue;
            }
            switch (opt){
                case 1:
                    teachingAssistant.viewCourses();
                    break;
                case 2:
                    teachingAssistant.register();
                    break;
                case 3:
                    teachingAssistant.viewSchedule();
                    break;
                case 4:
                    while(true) {
                        int choice;
                        try {
                            System.out.println("Enter the choice");
                            System.out.println("1.View Grades\n2.View CGPA\n3.Show Completed Course\n4.Back");
                            choice = scanner.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("<-----Please enter a nubmer----->");
                            scanner.next();
                            continue;
                        }
                        if (choice == 4) break;
                        else if(choice == 1) teachingAssistant.showGrades();
                        else if(choice == 2) teachingAssistant.showCgpa();
                        else if(choice == 3) teachingAssistant.showCompleted();
                        else System.out.println("Invalid choice");
                    }
                    break;
                case 5:
                    teachingAssistant.dropCourse();
                    break;
                case 6:
                    while(true){
                        System.out.println("Choose option\n1.Complaint\n2.See Complaints\n3.Back");
                        int option = scanner.nextInt();
                        if(option==3) break;
                        else if(option==1) teachingAssistant.complaint();
                        else if(option==2) teachingAssistant.showComplaints();
                        else System.out.println("Invalid");
                    }
                    break;
                case 7:
                    teachingAssistant.giveFeedback();
                    break;
                case 8:
                    while(true){
                        teachingAssistant.getMyTACourse();
                        System.out.println("Choose option\n1.View Students\n2.Propose Grades\n3.Back");
                        int option = scanner.nextInt();
                        if(option==3) break;
                        else if(option==1) teachingAssistant.viewStudents();
                        else if(option==2) teachingAssistant.proposeGrades();
                        else System.out.println("Invalid");
                    }
                    break;
                case 9:
                    logout();
                    exit = true;
                    break;
                default:
                    System.out.println("invalid");
            }
        }
        return false;


    }



    public void logout(){
        System.out.println("logged out");
        currUser = null;
    }
}