package dk.kea.springbootdb.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/teachers")
public class TeacherController {

    private TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getTeachers() {
        try {
            return new ResponseEntity<>(teacherService.getTeachers(), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<Teacher> getSingleTeacher(@PathVariable("teacherId") long id) {
        try {
            return new ResponseEntity<>(teacherService.getSingleTeacher(id), HttpStatus.OK);
        } catch (Exception e) {
            HttpStatus httpStatus = e instanceof IllegalStateException ? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR;
            throw new ResponseStatusException(httpStatus, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher newTeacher) {
        try {
            return new ResponseEntity<>(teacherService.createTeacher(newTeacher), HttpStatus.CREATED);
        } catch (Exception e) {
            HttpStatus httpStatus = e instanceof IllegalStateException ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;
            throw new ResponseStatusException(httpStatus, e.getMessage());
        }
    }

    @DeleteMapping("/{teacherId}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable("teacherId") long id) {
        try {
            teacherService.deleteTeacher(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            HttpStatus httpStatus = e instanceof IllegalStateException ? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR;
            throw new ResponseStatusException(httpStatus, e.getMessage());
        }
    }

    @PutMapping("/{teacherId}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable("teacherId") long id, @RequestBody Teacher teacher) {
        try {
            return new ResponseEntity<>(teacherService.updateTeacher(id, teacher), HttpStatus.OK);
        } catch (Exception e) {
            HttpStatus httpStatus = e instanceof IllegalStateException ? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR;
            throw new ResponseStatusException(httpStatus, e.getMessage());
        }
    }
}
