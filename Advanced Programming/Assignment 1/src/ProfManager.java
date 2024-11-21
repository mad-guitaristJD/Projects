import java.util.ArrayList;

public class ProfManager{
    public static ArrayList<Prof> profs = new ArrayList<>();

    public static void addProf(Prof p){
        profs.add(p);
    }

    public static ArrayList<Prof> getProfs() {
        return profs;
    }


}