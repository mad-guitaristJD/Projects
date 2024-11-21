import java.util.ArrayList;

public class StudentManager{
    public static ArrayList<Student> students = new ArrayList<>();

    public static void addStudent(Student s){
        students.add(s);
    }

    public static ArrayList<Student> getStudents() {
        return students;
    }


}