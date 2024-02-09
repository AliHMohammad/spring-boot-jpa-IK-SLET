package dk.kea.springbootdb.student;


import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentService {



    public List<Student> getStudents() {
        return List.of(
                new Student(1L, "Mariam", "mariam@gmail.com", LocalDate.of(2000,9,12))
        );
    }
}
