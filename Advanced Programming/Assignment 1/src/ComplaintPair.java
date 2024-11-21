public class ComplaintPair{
    Student student;
    Complaints complaint;

    public ComplaintPair(Student student, Complaints complaint) {
        this.student = student;
        this.complaint = complaint;
    }

    public Complaints getComplaint() {
        return complaint;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setComplaint(Complaints complaint) {
        this.complaint = complaint;
    }

    @Override
    public String toString(){
        return (student.getName()+ ": " + complaint.getDetails() + "\n\t\tStatus: " + complaint.getStatus());
    }
}