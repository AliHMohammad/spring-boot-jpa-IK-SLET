package dk.kea.springbootdb.subject;


import jakarta.persistence.*;

@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    private int ects;

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

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", ects=" + ects +
                '}';
    }
}
