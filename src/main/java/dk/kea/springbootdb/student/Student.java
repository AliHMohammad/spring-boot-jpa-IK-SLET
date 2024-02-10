package dk.kea.springbootdb.student;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table
public class Student {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private LocalDate dateOfBirth;

    //Transient betyder, at den ikke bliver gemt i databasen. Der bliver ikke oprettet en kolonne
    @Transient
    private int age;

    public Student(long id, String name, String email, LocalDate dateOfBirth) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public Student() {

    }

    public Student(String name, String email, LocalDate dateOfBirth) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public Student(String name, String email, String dateOfBirthString) {
        this.name = name;
        this.email = email;
        setDateOfBirth(dateOfBirthString);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge() {
        return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
    }

    private void setDateOfBirth(String dobString) {
        String[] dateArr = dobString.split("-");
        this.dateOfBirth = LocalDate.of(Integer.parseInt(dateArr[2]), Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[0]));
    }

    @Override
    public String toString() {
        return "Student { " +
                "id= " + id +
                ", name= '" + name + '\'' +
                ", email= '" + email + '\'' +
                ", dateOfBirth= " + dateOfBirth +
                '}';
    }
}
