public class profCoursePair{
    public Prof professor;
    public Course course;

    public profCoursePair(Prof professor, Course course) {
        this.professor = professor;
        this.course = course;
    }

    public Prof getProfessor() {
        return professor;
    }

    public Course getCourse() {
        return course;
    }
}
