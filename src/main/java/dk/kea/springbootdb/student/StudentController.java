package dk.kea.springbootdb.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(path = "/api/v1/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getStudents() {
        try {
            return new ResponseEntity<>(studentService.getStudents(), HttpStatus.OK);
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
