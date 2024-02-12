package dk.kea.springbootdb.student;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dk.kea.springbootdb.subject.Subject;
import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private LocalDate dateOfBirth;

    //Vi bruger @JsonIgnore for at undgå rekursion, når vi tilføjer en student til et subject
    //En student kan have mange subjects. En subject kan have mange students. ManyToMany
    @JsonIgnore
    @ManyToMany(mappedBy = "enrolledStudents")
    private Set<Subject> subjects = new HashSet<>();

    //Transient betyder, at den ikke bliver gemt i databasen. Der bliver ikke oprettet en kolonne
    //Fordelen er, at den returnerer age ved get-kald ud fra getAge() metoden, som anvender dob.
    @Transient
    private int age;

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

    public Set<Subject> getSubjects() {
        return subjects;
    }

}
