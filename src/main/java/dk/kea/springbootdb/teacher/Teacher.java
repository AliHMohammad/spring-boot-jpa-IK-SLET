package dk.kea.springbootdb.teacher;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;

@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate dateOfBirth;

    @Transient
    private int age;

    public Teacher() {

    }

    public Teacher(Long id, String name, LocalDate dateOfBirth) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public Teacher(String name, LocalDate dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public Teacher(String name, String dateOfBirthString) {
        this.name = name;
        setDateOfBirth(dateOfBirthString);
    }

    public Teacher(Teacher other) {
        this.name = other.name;
        this.dateOfBirth = other.dateOfBirth;
    }

    public int getAge() {
        return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
    }

    private void setDateOfBirth(String dobString) {
        String[] dateArr = dobString.split("-");
        this.dateOfBirth = LocalDate.of(Integer.parseInt(dateArr[2]), Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[0]));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", age=" + age +
                '}';
    }
}
