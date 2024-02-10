package dk.kea.springbootdb.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    //Vi har defineret vores egen metode (SQL-kald), som vi kan bruge i vores service-lag

    //Nedenstående er implicit
    //SELECT * FROM student WHERE email = ?
    Optional<Student> findStudentByEmail(String email);

    //Vi kan også være mere eksplicitte og definere præcist hvordan
    //Querien skal se ud til metoden med @Query()
    @Query("select s from Student s where s.name = ?1")
    Optional<Student> findStudentByName(String name);
}


