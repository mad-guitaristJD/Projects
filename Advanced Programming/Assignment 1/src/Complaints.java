import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Complaints{
    private String details;
    private String status;
    public static ArrayList<ComplaintPair> complaints = new ArrayList<>();

    public static void addComplaint(ComplaintPair p){
        complaints.add(p);
    }

    public static void showComplaints(){
        int i=0;
        for(ComplaintPair c : complaints){
            System.out.println((i+1)+". "+c);
            i++;
        }
    }

    public String getDetails() {
        return details;
    }

    public String getStatus() {
        return status;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString(){
        String str = getDetails() + " \nSTATUS: " + getStatus();
        return str;
    }
}