package dk.kea.springbootdb.subject;


import dk.kea.springbootdb.student.Student;
import dk.kea.springbootdb.teacher.Teacher;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private int ects;

    @ManyToMany
    @JoinTable(
            name = "students_subjects",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> enrolledStudents = new HashSet<>();

    //Mange subjects har en teacher. En teacher har mange subjects. ManyToOne
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;



    public Subject() {

    }

    public Subject(Subject other) {
        this.title = other.title;
        this.ects = other.ects;
    }

    public Subject(String title, int ects) {
        this.title = title;
        this.ects = ects;
    }

    public Subject(Long id, String title, int ects) {
        this.id = id;
        this.title = title;
        this.ects = ects;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEcts() {
        return ects;
    }

    public void setEcts(int ects) {
        this.ects = ects;
    }

    public void enrollStudent(Student student) {
        this.enrolledStudents.add(student);
    }

    public void assignTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setEnrolledStudents(Set<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public Set<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", ects=" + ects +
                '}';
    }
}
