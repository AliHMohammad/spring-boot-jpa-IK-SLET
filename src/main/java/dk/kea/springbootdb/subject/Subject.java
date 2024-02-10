package dk.kea.springbootdb.subject;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private int ECTSPoints;

    public Subject() {

    }

    public Subject(String title, int ECTSPoints) {
        this.title = title;
        this.ECTSPoints = ECTSPoints;
    }

    public Subject(Long id, String title, int ECTSPoints) {
        this.id = id;
        this.title = title;
        this.ECTSPoints = ECTSPoints;
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

    public int getECTSPoints() {
        return ECTSPoints;
    }

    public void setECTSPoints(int ECTSPoints) {
        this.ECTSPoints = ECTSPoints;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", ECTSPoints=" + ECTSPoints +
                '}';
    }

    public void createSubject(Subject subject) {
    }
}
