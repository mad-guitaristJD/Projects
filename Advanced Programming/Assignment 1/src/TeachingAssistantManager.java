import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeachingAssistantManager{
    public static ArrayList<TeachingAssistant> teachingAssistants = new ArrayList<>();
    public static Map<TeachingAssistant, Course> linkingTA = new HashMap<>();

    public static void addTA(TeachingAssistant p){
        teachingAssistants.add(p);
    }

    public static ArrayList<TeachingAssistant> getTA() {
        return teachingAssistants;
    }

    public static void display(){
        int i=1;
        for(TeachingAssistant ta : teachingAssistants){
            System.out.println(ta.name);
            i++;
        }
    }

    public static TeachingAssistant getTA(String name){
        TeachingAssistant t = null;
        for(TeachingAssistant ta : teachingAssistants){
            if(ta.getName().equals(name)){
                t = ta;
            }
        }
        return t;
    }

    public static void linkTA(TeachingAssistant ta, Course course){
        linkingTA.put(ta, course);
        ta.setMyTACourse(course);
    }


}