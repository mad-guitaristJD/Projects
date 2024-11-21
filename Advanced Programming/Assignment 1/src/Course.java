import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

class Pair {
    Student student;
    String grade;
    Course course;

    public Pair(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.grade = "NOT GRADED";
    }

    public Pair(Student student, Course course, String grade){
        this.student = student;
        this.course = course;
        this.grade = grade;
    }

    public Student getStudent() {
        return student;
    }

    public String getGrade() {
        return grade;
    }

    public Course getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return (student.name + ' ' + course.getCode() + " " + grade);
    }
}


public class Course{
    private String code;
    private String title;
    private Prof professor;
    private String timings;
    private int credits;
    private int semester;
    private Course prereq;
    private static ArrayList<Course> allCourses = new ArrayList<>();
    public static HashMap<Course, Prof> profCourse = new HashMap<>();
    public static ArrayList<Feedback> feedList = new ArrayList<>();
    public static HashMap<Course, ArrayList<Feedback>> feedMap = new HashMap<>();
    private String syllabus;
    int enrollmentLimit;
    private int currenroll;
    String officeHours;
    LocalDateTime dropDateTime;



    static {
        Course CS101 = new Course("CS101", "Introduction to Programming", ProfManager.getProfs().get(0), "Mon 4-5",4, 1, null );
        allCourses.add(CS101);
        Course CS102 =new Course( "CS102", "Data Structures and Algorithms", ProfManager.getProfs().get(0), "Mon 4-5",4, 1, CS101);
        allCourses.add(CS102);
        Course CS201 = new Course("CS201", "Advanced Programming", ProfManager.getProfs().get(0), "Mon Wed 3:00-4:00" , 4 , 1, CS102);
        allCourses.add(CS201);
        Course M101 = new Course( "M101", "Linear Algebra", ProfManager.getProfs().get(1), "Mon 4-5",4, 1, null);
        allCourses.add(M101);
        Course M102 = new Course( "M102", "Probability and Statistics", ProfManager.getProfs().get(0), "Mon Thur 9:30-11:00", 4, 2, M101);
        allCourses.add(M102);
        Course M103  = new Course("M103", "Multivariate Calculus", ProfManager.getProfs().get(1), "Thur 8:30 -:30", 4, 2, M102);
        allCourses.add(M103);
        Course DES101 = new Course( "DES101", "HCI", ProfManager.getProfs().get(0), "Wed Fri 3:00-4:00", 4, 1, null);
        allCourses.add(DES101);
        Course CS301 = new Course( "CS301", "just trial", ProfManager.getProfs().get(1), "Wed Fri 3:00-4:00", 4, 6, null);
        allCourses.add(CS301);
        Course DES301 = new Course( "DES301", "just trial 2", ProfManager.getProfs().get(0), "Tue thur 3:00-4:00", 4, 6, null);
        allCourses.add(DES301);


        profCourse.put(CS101,ProfManager.getProfs().get(0));
        profCourse.put(CS102,ProfManager.getProfs().get(0));
        profCourse.put(CS201,ProfManager.getProfs().get(0));
        profCourse.put(M101,ProfManager.getProfs().get(1));
        profCourse.put(M102,ProfManager.getProfs().get(0));
        profCourse.put(M103,ProfManager.getProfs().get(1));
        profCourse.put(DES101,ProfManager.getProfs().get(0));

        DES101.currenroll=60; //check enroll
        M103.dropDateTime = LocalDateTime.of(2024, 9, 28, 14, 30);

    }



    public LocalDateTime getDropDateTime() {
        return dropDateTime;
    }

    public int getCurrenroll() {
        return currenroll;
    }

    public static HashMap<Course, ArrayList<Feedback>> getFeedMap() {
        return feedMap;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public static HashMap<Course, Prof> getProfCourse() {
        return profCourse;
    }

    public Course(String code, String title, Prof professor, String timings, int credits, int semester, Course prereq) {
        this.code = code;
        this.title = title;
        this.professor = professor;
        this.timings = timings;
        this.credits = credits;
        this.semester = semester;
        this.prereq = prereq;
        syllabus = "Not decided by the prof";
        enrollmentLimit = 60;
        officeHours = "Sat 4-5pm";
        currenroll=0;
        dropDateTime=null;
    }

    public static void showCourses(){
        for(int i=0; i<allCourses.size(); i++){
            System.out.println((i+1) +" " + allCourses.get(i));
        }
    }

    public static void deleteCourse(Course c){
        System.out.println("Successfully Deleted "+c);
        allCourses.remove(c);
    }

    public static ArrayList<Course> getAllCourses(){
        return allCourses;
    }

    public static void addCourse(Course c){
        allCourses.add(c);
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public static void createFeedback(Course c , Feedback f){
        try{
            feedMap.get(c).add(f);
        }
        catch(Exception e){
            ArrayList<Feedback> feed = new ArrayList<>();
            feed.add(f);
            feedMap.put(c, feed);
        }

    }

    public int getSemester() {
        return semester;
    }

    public int getCredits() {
        return credits;
    }

    public String getTimings() {
        return timings;
    }

    public String getProfessor() {
        return professor.getName();
    }

    public Prof getProf(){
        return professor;
    }

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    public Course getPrereq() {
        return prereq;
    }

    @Override
    public String toString(){
        String  str = "Code: "+code+" Title: "+title+" Professor: "+professor;
        return str;
    }
}