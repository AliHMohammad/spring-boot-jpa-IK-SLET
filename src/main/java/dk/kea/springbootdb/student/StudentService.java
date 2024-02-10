package dk.kea.springbootdb.student;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        //Vi validerer om email allerede eksisterer i databasen.
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());

        //Hvis den gør, så throw
        if (studentOptional.isPresent()){
            throw new IllegalStateException("email taken");
        }

        studentRepository.save(student);
    }

    public void deleteStudent(long studentId) {
        boolean exists = studentRepository.existsById(studentId);

        if (!exists) {
            throw new IllegalStateException("student with id: " + studentId + " does not exist in the db");
        }

        studentRepository.deleteById(studentId);
    }
}
