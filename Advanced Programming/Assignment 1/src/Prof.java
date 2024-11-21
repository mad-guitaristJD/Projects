import java.util.*;

interface profFunc{
    void showCourses();
    void updateCourse();
}


public class Prof extends User implements profFunc{
    private String name;
    public static ArrayList<Course> profMyCourse = new ArrayList<>(); //Course prof is teaching
    private HashMap<Course, Prof> myCourse = new HashMap<>();
    private ArrayList<EnrolledStudentsPair> esp = new ArrayList<>();
    public ArrayList<Pair> proposedGrades = new ArrayList<>(); //Propsosed grades by TAs


    public Prof(String email, String pass, String name) {
        super(email, pass);
        this.name = name;
    }

    public  String getName() {
        return name;
    }


    @Override
    public void showCourses() {
        HashMap<Course, Prof> profCourseMap= Course.getProfCourse();
        for (Map.Entry<Course, Prof> entry : profCourseMap.entrySet()) {
            if(entry.getValue()==this){
                myCourse.put(entry.getKey(), entry.getValue());
                if(!profMyCourse.contains(entry.getKey())) profMyCourse.add(entry.getKey());
            }
        }
        
        int i=1;
        for (Map.Entry<Course, Prof> entry : myCourse.entrySet()){
            Course c = entry.getKey();
            System.out.println(i+". Title : " + c.getTitle()+" Credits : " + c.getCredits()+" Timings : " + c.getTimings()+" Syllabus : " + c.getSyllabus());
            i++;
        }
    }

    public void assignTA(){
        System.out.println("My Courses : ");
        showCourses();
        System.out.println("Available TA :");
        TeachingAssistantManager.display();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Course Choice");
        int courseOption = scanner.nextInt();
        scanner.nextLine();
        courseOption--;
        System.out.println("Enter TA name");
        String taOption = scanner.nextLine();
        TeachingAssistant ta = TeachingAssistantManager.getTA(taOption);
        TeachingAssistantManager.linkTA(ta, profMyCourse.get(courseOption));
        System.out.println("Linked");


        //get list of TA

    }

    public ArrayList<EnrolledStudentsPair> returnToTA(){
        return esp;
    }

    public void printEnrolledStudents(){
        if(esp.isEmpty()){
            System.out.println("No one has enrolled in your course yet");
            return;
        }
        System.out.println("Enrolled Students : ");
        for(int i=0; i<esp.size(); i++){
            System.out.println(i+1 + ". " + esp.get(i).getStudent().getName() + " has enrolled for : " + esp.get(i).getCourse().getTitle() );
        }

    }

    public void enrolledStudents(Student student, Course course){
        esp.add(new EnrolledStudentsPair(student, course));
    }



    @Override
    public void updateCourse(){
        Scanner scanner = new Scanner(System.in);
        int choice, option;
        while(true){
            System.out.println("Input what you want to update");
            System.out.println("1.Credits\n2.Timings\n3.Syllabus\n4.Back");
            choice = scanner.nextInt();
            if(choice==4) break;
            else if(choice==1){
                showCourses();
                Course c = null;
                System.out.println("Enter the Course index number");
                option = scanner.nextInt();
                int i=1;
                for (Map.Entry<Course, Prof> entry : myCourse.entrySet()){
                    if(i==option) {
                        c = entry.getKey();
                        break;
                    }
                    i++;
                }
                System.out.println("Enter the credits ");
                int cred = scanner.nextInt();
                c.setCredits(cred);
            }
            else if(choice==2){
                showCourses();
                Course c = null;
                System.out.println("Enter the Course index number");
                option = scanner.nextInt();
                scanner.nextLine();
                int i=1;
                for (Map.Entry<Course, Prof> entry : myCourse.entrySet()){
                    if(i==option) {
                        c = entry.getKey();
                        break;
                    }
                    i++;
                }
                System.out.print("Enter the new timings: ");
                String time  = scanner.nextLine();
                c.setTimings(time);
            }
            else if(choice==3){
                showCourses();
                Course c = null;
                System.out.println("Enter the Course index number");
                option = scanner.nextInt();
                scanner.nextLine();
                int i=1;
                for (Map.Entry<Course, Prof> entry : myCourse.entrySet()){
                    if(i==option) {
                        c = entry.getKey();
                        break;
                    }
                    i++;
                }
                System.out.print("Enter the new Syllbus: ");
                String syl  = scanner.nextLine();
                c.setSyllabus(syl);
            }


        }

    }

    public void seeFeedback(){
//        System.out.println("yes reached here");


        HashMap<Course, ArrayList<Feedback>> maps = Course.getFeedMap();

        for (Course c : profMyCourse) {
            ArrayList<Feedback> f = maps.get(c);

            System.out.println("Feedback for the course " + c + ":");

            // Check if the feedback list is null
            if (f != null && !f.isEmpty()) {
                for (Feedback g : f) {
                    System.out.println("\t\t" + g.getFeedback());
                }
            } else {
                System.out.println("\t\tNo feedback available.");
            }
        }



//        HashMap<Course, ArrayList<Feedback>> maps = Course.getFeedMap();
//        for(Course c : profCourse){
//            ArrayList<Feedback> f = maps.get(c);
//            System.out.println("Feedback for the course " + c);
//            for (Feedback g : f){
//                System.out.println("\t\t"+g.getFeedback());
//            }
//        }
    }

    public void setProposedGrades(Course course, Student student, String grade){
        proposedGrades.add(new Pair(student , course, grade));
    }

    public void seeProposedGrades(){
        if(proposedGrades.isEmpty()){
            System.out.println("Nothing proposed");
            return;
        }
        for(Pair pair : proposedGrades){
            System.out.println("Proposed Grade for " + pair.getStudent().getName() + " for the course " + pair.getCourse().getTitle() + " is : " + pair.getGrade());
        }

    }



    @Override
    public String toString(){
        return name;
    }

    @Override
    public void display() {

    }
}