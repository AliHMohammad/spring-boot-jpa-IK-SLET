package dk.kea.springbootdb.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    //Vi har defineret vores egen metode (SQL-kald), som vi kan bruge i vores service-lag
    //SELECT * FROM student WHERE email = ?
    Optional<Student> findStudentByEmail(String email);
}
