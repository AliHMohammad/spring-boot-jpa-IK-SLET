package dk.kea.springbootdb.student;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Page<Student> getStudents(
            Optional<String> sortBy,
            Optional<String> sortDir,
            Optional<Integer> pageNum,
            Optional<Integer> pageSize,
            Optional<String> filterBy
    ) {
        //Querien sortDir skal enten være 'desc' or 'asc' i postman
        Sort.Direction direction = sortDir.map(String::toUpperCase)
                .filter("DESC"::equals)
                .map(x -> Sort.Direction.DESC)
                .orElse(Sort.Direction.ASC);

        Pageable pageable = PageRequest.of(
                pageNum.orElse(0),
                pageSize.orElse(10),
                direction,
                sortBy.orElse("id")
        );

        if (filterBy.isPresent()){
            return studentRepository.findStudentByNameContainingIgnoreCase(filterBy.orElse(""), pageable);
        }

        return studentRepository.findAll(pageable);
    }

    public Student addNewStudent(Student student) {
        //Vi validerer om email allerede eksisterer i databasen.
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());

        //Hvis den gør, så throw
        if (studentOptional.isPresent()){
            throw new IllegalStateException("email taken");
        }

        Student studentToCreate = new Student(student);
        return studentRepository.save(studentToCreate);
    }

    public void deleteStudent(long studentId) {
        boolean exists = studentRepository.existsById(studentId);

        if (!exists) {
            throw new IllegalStateException("student with id: " + studentId + " does not exist in the db");
        }

        studentRepository.deleteById(studentId);
    }

    @Transactional
    public Student updateStudent(long id, Student updatedStudent) {
        Student studentInDb = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "Student with id " + id + " does not exist"
                ));

        //Vi bruger dens setter til at opdatere
        //Ændringen gemmer i db'en
        //HUSK @Transactional på metoden for at det virker
        studentInDb.setEmail(updatedStudent.getEmail());
        studentInDb.setName(updatedStudent.getName());
        studentInDb.setDateOfBirth(updatedStudent.getDateOfBirth());

        return studentInDb;
    }

    public Student getSingleStudent(long id) {
        return studentRepository.findById(id)
                .orElseThrow(()-> new IllegalStateException(
                        "Student with id " + id + " does not exist"
                ));
    }
}
