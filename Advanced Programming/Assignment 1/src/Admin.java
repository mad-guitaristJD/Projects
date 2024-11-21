import java.util.ArrayList;
import java.util.Scanner;

public class Admin extends User{
    Scanner scanner = new Scanner(System.in);



    public void adminDeleteCourse(){
        while(true){
            ArrayList<Course> li  = Course.getAllCourses();
            if(li.size()<1){
                System.out.println("No Course Left To Delete");
                return;
            }
            Course.showCourses();
            int option = scanner.nextInt();
            System.out.println("Enter -1 To Exit");
            if(option==-1) return;
            option--;
            if(option<li.size() && option>-1) {
                Course.deleteCourse(li.get(option));
            }
            else{
                System.out.println("Invalid");
            }
        }
    }

    public void addCourse(){
        // course code, title, professor, timings, credits, semester, prereq
        scanner.nextLine();
        System.out.println("Enter the Course Code");
        String courseCode;
        courseCode = scanner.nextLine();

        System.out.println("Enter the Course Title");
        String title;
        title = scanner.nextLine();

        System.out.println("Enter the Course Professor's Name");
        String professor;
        professor = scanner.nextLine();
        Prof p = new Prof("default", "default", professor);

        System.out.println("Enter the Course Timings");
        String timings;
        timings = scanner.nextLine();

        System.out.println("Enter the Course Number of Credits");
        int credits = scanner.nextInt();

        System.out.println("Enter the Semester to be offered in");
        int sem = scanner.nextInt();

        scanner.nextLine();
        System.out.println("Enter the Course PreReq (the course code)");
        String prereq;
        prereq = scanner.nextLine();
        ArrayList<Course> list = Course.getAllCourses();
        for(Course c : list){
            if(c.getCode() == prereq){
                Course.addCourse(new Course(courseCode, title, p, timings, credits, sem, c));
                return;
            }
        }
        Course.addCourse(new Course(courseCode, title, p, timings, credits, sem, null));

    }

    public static void viewRecords(){
        int i=1;
        ArrayList<Student> stu = StudentManager.getStudents();
        for(Student s : stu){
            System.out.println(i+". Name: "+ s.name+ " Age: " + s.age + " Phone No: " + s.phone);
            i++;
        }
    }

    public void deleteRecord(){
        Scanner scanner = new Scanner(System.in);
        Admin.viewRecords();
        System.out.println("Enter the number to delete\n-1 to return");
        int option = scanner.nextInt();
        if(option==-1) return;
        option--;
        Student s = StudentManager.students.get(option);
        System.out.println("Deleted "+ s.name + " successfully");
        StudentManager.students.remove(option);
        Controller.students.remove(s.getEmail());
    }


    public void resolveComplaint(){
        Complaints.showComplaints();
        System.out.println("Enter the number to resolve complaints");
        int option = scanner.nextInt();
        option--;
        Complaints.complaints.get(option).getComplaint().setStatus("Resolved");
        Complaints.showComplaints();
    }

    private void showGrades(){
        for(int i=0; i<Student.myGrades.size(); i++){
            System.out.println((i+1)+". "+Student.myGrades.get(i));
        }
    }

    public void giveGradesAdmin(){
        while(true){
            if(Student.myGrades.size()<1){
                System.out.println("No Registered Courses by Students");
                break;
            }
            showGrades();
            System.out.println("-1 to exit\nEnter the number whose grade you want to change");
            int choice = scanner.nextInt();
            if(choice==-1)break;
            if(choice>Student.myGrades.size()) break;
            System.out.println("Enter the Grade (A B C D F)");
            scanner.nextLine();
            String grad = scanner.nextLine();
            Student.myGrades.get(choice-1).grade = grad;
            Student std = Student.myGrades.get(choice-1).student;
            std.Completed.add(Student.myGrades.get(choice-1).course);
        }
    }

    protected void createStudent(){
        System.out.println("Enter email");
        String email = scanner.nextLine();
        System.out.println("Enter password");
        String pass = scanner.nextLine();
        System.out.println("Enter semester");
        int sem = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter name");
        String name = scanner.nextLine();
        System.out.println("Enter age");
        int age = scanner.nextInt();
        System.out.println("Enter phoneno");
        int phoneno = scanner.nextInt();

        Student s = new Student(email, pass, sem, name, age, phoneno);
        Controller.students.put(email, s);
        StudentManager.addStudent(s);
        System.out.println(name+" added successfully");
    }




    @Override
    public void display() {
    }


}