package dk.kea.springbootdb.student;


import dk.kea.springbootdb.subject.Subject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping(path = "/api/v1/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<Page<Student>> getStudents(
            @RequestParam Optional<String> sortBy,
            @RequestParam Optional<String> sortDir,
            @RequestParam Optional<Integer> pageNum,
            @RequestParam Optional<Integer> pageSize,
            @RequestParam Optional<String> filterBy
    ) {
        return new ResponseEntity<>(studentService.getStudents(sortBy, sortDir, pageNum, pageSize, filterBy), HttpStatus.OK);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getSingleStudent(@PathVariable("studentId") long id) {
        Optional<Student> studentFound = studentService.getSingleStudent(id);

        return ResponseEntity.of(studentFound);
    }

    @PostMapping
    public ResponseEntity<Student> registerNewStudent(@RequestBody Student student) {
        try {
            Student createdStudent = studentService.addNewStudent(student);

            //Vi bygger en location til response header
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdStudent.getId())
                    .toUri();

            return ResponseEntity.created(location).body(createdStudent);
        } catch (Exception e) {
            HttpStatus httpStatus = e instanceof IllegalStateException ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;
            throw new ResponseStatusException(httpStatus, e.getMessage());
        }
    }

    @DeleteMapping(path = "/{studentId}")
    public ResponseEntity<Student> deleteStudent(@PathVariable("studentId") long id) {
        Optional<Student> studentDeleted = studentService.deleteStudent(id);

        if (studentDeleted.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(studentDeleted.get());
    }

    @PutMapping(path = "/{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable("studentId") long id, @RequestBody Student updatedStudent) {
        Optional<Student> student = studentService.updateStudent(id, updatedStudent);

        return ResponseEntity.of(student);
    }


}
