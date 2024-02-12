package dk.kea.springbootdb.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/teachers")
public class TeacherController {

    private TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getTeachers() {
        return new ResponseEntity<>(teacherService.getTeachers(), HttpStatus.OK);
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<Teacher> getSingleTeacher(@PathVariable("teacherId") long id) {
        Optional<Teacher> teacher = teacherService.getSingleTeacher(id);

        if (teacher.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(teacher.get());
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
    public ResponseEntity<Teacher> deleteTeacher(@PathVariable("teacherId") long id) {
        Optional<Teacher> teacherDeleted = teacherService.deleteTeacher(id);

        if (teacherDeleted.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(teacherDeleted.get());
    }

    @PutMapping("/{teacherId}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable("teacherId") long id, @RequestBody Teacher teacher) {
        Optional<Teacher> resultTeacher = teacherService.updateTeacher(id, teacher);

        if (resultTeacher.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(resultTeacher.get());
    }
}
