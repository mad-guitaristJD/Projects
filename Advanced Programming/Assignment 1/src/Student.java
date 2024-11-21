import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Scanner;

public class Student extends User{
    protected String name;
    protected int age;
    protected int phone;
    private int credits=20;
    private int usedCredits=0;
    private int sem;
    public static ArrayList<Pair> myGrades= new ArrayList<>();
    public ArrayList<Course> AvailableCourse = new ArrayList<>(); // All courses of all sem
    public ArrayList<Course> MyCourse = new ArrayList<>(); // Registered Courses
    public ArrayList<Course> Completed = new ArrayList<>(); //Completed Courses by the Student
    public ArrayList<ComplaintPair> studentComplaints = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    public String getName() {
        return name;
    }

    public Student(String email, String pass, int sem, String name, int age, int phoneno){
        super(email,pass);
        this.sem = sem;
        this.name = name;
        this.age = age;
        this.phone = phoneno;

        Complaints c = new Complaints();
        c.setDetails("DEFAULT COMPLAINT FOR TEST");
        c.setStatus("Pending");
        ComplaintPair temp = new ComplaintPair(this, c);
        Complaints.addComplaint(temp);
        studentComplaints.add(temp);


    }

    protected void complaint(){
        System.out.println("Type Your Complaint: ");
        String details = scanner.nextLine();
        Complaints complaint = new Complaints();
        complaint.setDetails(details);
        ComplaintPair temp = new ComplaintPair(this, complaint);
        complaint.setStatus("Pending");
        System.out.println("Complaint status: Pending ");
        studentComplaints.add(temp);
        Complaints.addComplaint(temp);

    }

    protected void showComplaints(){
        if(studentComplaints.isEmpty()){
            System.out.println("No Complaints Found");
        }
        else{
            for(ComplaintPair com : studentComplaints){
                System.out.println(com);
            }
        }
    }

    protected void viewCourses(){
        ArrayList<Course> CoursesThisSem = new ArrayList<>();
        AvailableCourse = Course.getAllCourses();
        for(Course c : AvailableCourse){
            if(c.getSemester() == sem){
                CoursesThisSem.add(c);
            }
        }
        System.out.println("\nCourses for your current sem: "+sem+"\n");
        for(int i=0; i<CoursesThisSem.size(); i++){
            System.out.println((i+1)+" "+CoursesThisSem.get(i));
        }
    }

    private void showCoursesThisSem(){  //show the courses form Coursesthisem
        ArrayList<Course> CoursesThisSem = new ArrayList<>();
        AvailableCourse = Course.getAllCourses();
        for(Course c : AvailableCourse){
            if(c.getSemester() == sem){
                CoursesThisSem.add(c);
            }
        }
        System.out.println("\nLeft Courses: ");
        int i=0;
        for(Course c : CoursesThisSem){
            System.out.println((i+1)+" " +c);
            i++;
        }
    }

    private void showMyCourses(){  //shows the registered courses from MyCourses
        int i=0;
        System.out.println("Registered Courses");
        for(Course c : MyCourse){
            System.out.println((i+1)+" " +c);
            i++;
        }
    }

    protected void register() throws CourseFullException {
        ArrayList<Course> CoursesThisSem = new ArrayList<>();
        AvailableCourse = Course.getAllCourses();
        for(Course c : AvailableCourse){
            if(c.getSemester() == sem){
                CoursesThisSem.add(c);
            }
        }
        Scanner scanner = new Scanner(System.in);
        int choice;

        while(true) {
            if(credits<2){
                System.out.println("NO MORE CREDITS LEFT");
                break;
            }
            showCoursesThisSem();
            System.out.println("Enter the Course you want to choose\nCredits left: " + (20 - usedCredits) + "\n-1 to exit");
            choice = scanner.nextInt();
            if(choice == -1) break;
            try{
                if(CoursesThisSem.get(choice-1).getCurrenroll()+1>CoursesThisSem.get(choice-1).enrollmentLimit) {
                    throw new CourseFullException();
                }
            }
            catch (CourseFullException e){
                System.out.println(e + " caught");
                System.out.println("Course Limit is full!");
                break;
            }
            if (choice<=CoursesThisSem.size()) {
                if(!MyCourse.contains(CoursesThisSem.get(choice-1))) {
                    MyCourse.add(CoursesThisSem.get(choice - 1));
                    usedCredits += CoursesThisSem.get(choice - 1).getCredits();
                    credits -= CoursesThisSem.get(choice - 1).getCredits();
                    Pair p = new Pair(this, CoursesThisSem.get(choice - 1));
                    myGrades.add(p);
                    Prof prof = CoursesThisSem.get(choice-1).getProf();
                    prof.enrolledStudents(this, CoursesThisSem.get(choice-1));
                }
                else{
                    System.out.println("Already Registered");
                }
            }
            else{
                System.out.println("INVALID");
            }

        }
        showMyCourses();
        System.out.println(" ");
    }

    protected void viewSchedule(){
        //course name, class timings , professor name
        System.out.println("");
        System.out.println("Here is your weekly schedule");
        for(Course c : MyCourse){
            System.out.println("Course: "+c.getTitle()+" Timings: "+c.getTimings()+" Prof: " + c.getProfessor());
        }
        System.out.println("");
    }

    protected void showGrades(){
        for(Pair my : myGrades){
            if(my.student==this) {
                System.out.println(my);
            }
        }
    }

    protected void showCgpa(){
        // credits of the course gradepoint of the course
        ArrayList<Pair> gradeList = myGrades;
        float totalCredits=0;
        float num = 0;
        for(Pair list : gradeList){
            if(!list.grade.equals("NOT GRADED")){
                totalCredits+=list.course.getCredits();
                switch (list.grade){
                    case "A":
                        num+=list.course.getCredits()*10;
                        break;
                    case "B":
                        num+=list.course.getCredits()*8;
                        break;
                    case "C":
                        num+=list.course.getCredits()*6;
                        break;
                    case "D":
                        num+=list.course.getCredits()*4;
                        break;
                    case "F":
                        num+=list.course.getCredits()*0;
                        break;
                    default:
                        break;
                }
            }
        }
        if(totalCredits==0){
            System.out.println("Result Not Declared");
            return;
        }
        float ans = num/totalCredits;
        String formatAns = String.format("%.2f", ans);
        System.out.println("CGPA: " + formatAns);

    }

    protected void showCompleted(){
        if(Completed.size()<1) {
            System.out.println("No Completed Courses");
            return;
        }
        int i=1;
        System.out.println("Completed Course");
        for(Course c : Completed){
            System.out.println(i+". "+c);
            i++;
        }
        if(Completed.size()==MyCourse.size()){
            sem++;
        }
        MyCourse.clear();
        credits=20;
        usedCredits=0;
    }

    protected void dropCourse() throws DropDeadlinePassedException{
        showMyCourses();
        Course temp;
        int choice;
        while(true){
            if(MyCourse.size()==0){
                System.out.println("No registered Courses");
                return;
            }
            System.out.println("Enter the Course Number to Drop\n-1 to go back");
            choice = scanner.nextInt();
            try {
                if (choice == -1) break;
                Instant currentInstant = Instant.now();
                if(MyCourse.get(choice-1).getDropDateTime()!=null && MyCourse.get(choice-1).getDropDateTime().isBefore(currentInstant.atZone(ZoneId.systemDefault()).toLocalDateTime())){
                    throw new DropDeadlinePassedException();
                }
                else if (choice <= MyCourse.size()) {
                    usedCredits -= MyCourse.get(choice - 1).getCredits();
                    credits += MyCourse.get(choice - 1).getCredits();
                    MyCourse.remove(choice - 1);
                    showMyCourses();
                }
            }
            catch (DropDeadlinePassedException e){
                System.out.println("Drop Deadline Over\nException caught : " + e);
            }
        }
    }

    public void giveFeedback(){
        showCompleted();
        if(Completed.isEmpty()) return;
        System.out.println("Enter you the course you'd like to give feedback for");
        int choice = scanner.nextInt();
        scanner.nextLine();
        choice--;
        Course c = Completed.get(choice);
        System.out.println("Enter your feedback");
        try{
            int rating = scanner.nextInt();
            scanner.nextLine();
            Feedback<Integer> feedback = new Feedback<>(rating);
            System.out.println(feedback.getFeedback()+" Recorded");
            Course.createFeedback(c, feedback);
        }
        catch (Exception e){
            String string = scanner.nextLine();
            Feedback<String> feedback = new Feedback<>(string);
            System.out.println(feedback.getFeedback()+" Recorded");
            Course.createFeedback(c, feedback);
        }

    }




    @Override
    public void display() {
        System.out.println();
    }


}