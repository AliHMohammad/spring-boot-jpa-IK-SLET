package dk.kea.springbootdb.student;


import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
            @RequestParam Optional<Integer> pageSize
            ) {
        try {
            return new ResponseEntity<>(studentService.getStudents(sortBy, sortDir, pageNum, pageSize), HttpStatus.OK);
        } catch (Exception e) {
            HttpStatus httpStatus = e instanceof IllegalStateException ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;
            throw new ResponseStatusException(httpStatus, e.getMessage());
        }
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getSingleStudent(@PathVariable("studentId") long id) {
        try {
            return new ResponseEntity<>(studentService.getSingleStudent(id), HttpStatus.OK);
        } catch (Exception e) {
            HttpStatus httpStatus = e instanceof IllegalStateException ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;
            throw new ResponseStatusException(httpStatus, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Student> registerNewStudent(@RequestBody Student student) {
        try {
            return new ResponseEntity<>(studentService.addNewStudent(student), HttpStatus.CREATED);
        } catch (Exception e) {
            HttpStatus httpStatus = e instanceof IllegalStateException ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;
            throw new ResponseStatusException(httpStatus, e.getMessage());
        }
    }

    @DeleteMapping(path = "/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("studentId") long id) {
        try {
            studentService.deleteStudent(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            HttpStatus httpStatus = e instanceof IllegalStateException ? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR;
            throw new ResponseStatusException(httpStatus, e.getMessage());
        }
    }

    @PutMapping(path = "/{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable("studentId") long id, @RequestBody Student updatedStudent) {
        try {
            return new ResponseEntity<>(studentService.updateStudent(id, updatedStudent), HttpStatus.OK);
        } catch (Exception e) {
            HttpStatus httpStatus = e instanceof IllegalStateException ? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR;
            throw new ResponseStatusException(httpStatus, e.getMessage());
        }
    }


}
