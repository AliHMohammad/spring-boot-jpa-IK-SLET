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


        //Vi laver et Pageable object ud fra pagenum, pagesize, sortby og sortdir (Direction)
        Pageable pageable = PageRequest.of(
                pageNum.orElse(0),
                pageSize.orElse(10),
                direction,
                sortBy.orElse("id")
        );

        //Vi har lavet vores egen query, der modtager en string og Pageable. Se repository
        if (filterBy.isPresent()) return studentRepository.findStudentByNameContainingIgnoreCase(filterBy.get(), pageable);

        return studentRepository.findAll(pageable);
    }

    public Student addNewStudent(Student student) {
        //Vi validerer om email allerede eksisterer i databasen.
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());

        //Hvis den gør, så throw
        if (studentOptional.isPresent()){
            throw new IllegalStateException("email taken");
        }

        return studentRepository.save(student);
    }

    public Optional<Student> deleteStudent(long studentId) {
        //Vi returnerer det slettede person til frontend, for at at vise, hvad der er blevet slettet
        Optional<Student> studentInDb = studentRepository.findById(studentId);

        if (studentInDb.isPresent()) {
            studentRepository.delete(studentInDb.get());
        }

        return studentInDb;
    }

    @Transactional
    public Optional<Student> updateStudent(long id, Student updatedStudent) {
        Optional<Student> studentInDb = studentRepository.findById(id);

        if (studentInDb.isEmpty()) {
            return studentInDb;
        }

        //Vi bruger dens setter til at opdatere
        //Ændringen gemmer i db'en
        //HUSK @Transactional på metoden for at det virker
        studentInDb.get().setEmail(updatedStudent.getEmail());
        studentInDb.get().setName(updatedStudent.getName());
        studentInDb.get().setDateOfBirth(updatedStudent.getDateOfBirth());

        return studentInDb;
    }

    public Optional<Student> getSingleStudent(long id) {
        return studentRepository.findById(id);
    }
}
