package dk.kea.springbootdb.teacher;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dk.kea.springbootdb.subject.Subject;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate dateOfBirth;

    //En teacher har mange subjects. En subject har en teacher. OneToMany
    //mappeedBy-værdien skal være lig med navnet på fielden i Subject-klassen
    //@JsonIgnore for at undgå rekursion ved get-request
    @JsonIgnore
    @OneToMany(mappedBy = "teacher")
    private Set<Subject> subjects = new HashSet<>();

    @Transient
    private int age;

    public int getAge() {
        return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public Set<Subject> getSubjects() {
        return subjects;
    }

}
