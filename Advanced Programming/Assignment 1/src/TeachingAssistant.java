import java.util.ArrayList;

public class TeachingAssistant  extends Student{

    private Course myTACourse;

    public TeachingAssistant(String email, String pass, int sem, String name, int age, int phoneno) {
        super(email, pass, sem, name, age, phoneno);
        this.myTACourse = null;
    }

    public void getMyTACourse() {
        if(myTACourse!=null) {
            System.out.println("Your TA of the Course : " + myTACourse.getTitle());
        }
        else{
            System.out.println("You're not assigned a course yet!!!");
        }
    }

    public void setMyTACourse(Course myTACourse) {
        this.myTACourse = myTACourse;
    }

    public void viewStudents(){
        Prof prof = myTACourse.getProf();
        ArrayList<EnrolledStudentsPair> esp;
        esp = prof.returnToTA();
        int i=1;
        for( EnrolledStudentsPair e : esp){
            if(e.getCourse()==myTACourse){
                System.out.println(i + ". " + e.getStudent().getName() + " has enrolled for : " + e.getCourse().getTitle());
                i++;
            }
        }
    }

    public void proposeGrades(){
        Prof prof = myTACourse.getProf();
        ArrayList<EnrolledStudentsPair> esp;
        esp = prof.returnToTA();
        while(true){
            viewStudents();
            System.out.println("Enter student number whose grade you want to propose :\n-1 to back ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if(choice==-1) return;
            choice--;
            int i=0;
            Student student = null;
            for( EnrolledStudentsPair e : esp){
                if(e.getCourse()==myTACourse){
                    if(i==choice){
                        student = e.getStudent();
                        break;
                    }
                    i++;
                }
            }
            System.out.println("Enter the Grade you want to Propose 'A' 'B' 'C' 'D' 'F' ");
            String grade = scanner.nextLine();
            prof.setProposedGrades(myTACourse, student, grade);




        }
    }

    @Override
    public String toString(){
        return name;
    }



}
