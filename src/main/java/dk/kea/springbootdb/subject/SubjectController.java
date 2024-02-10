package dk.kea.springbootdb.subject;

import dk.kea.springbootdb.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public ResponseEntity<List<Subject>> getSubjects() {
        try {
            return new ResponseEntity<>(subjectService.getSubjects(), HttpStatus.OK);
        } catch (Exception e) {
            HttpStatus httpStatus = e instanceof IllegalStateException ? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR;
            throw new ResponseStatusException(httpStatus, e.getMessage());
        }
    }

    @GetMapping("/{subjectId}")
    public ResponseEntity<Subject> getSingleSubject(@PathVariable("subjectId") long id) {
        try {
            return new ResponseEntity<>(subjectService.getSingleSubject(id), HttpStatus.OK);
        } catch (Exception e) {
            HttpStatus httpStatus = e instanceof IllegalStateException ? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR;
            throw new ResponseStatusException(httpStatus, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Subject> createSubject(@RequestBody Subject subject) {
        try {
            return new ResponseEntity<>(subjectService.createSubject(subject), HttpStatus.CREATED);
        } catch (Exception e) {
            HttpStatus httpStatus = e instanceof IllegalStateException ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;
            throw new ResponseStatusException(httpStatus, e.getMessage());
        }

    }

    @DeleteMapping("/{subjectId}")
    public ResponseEntity<Void> deleteSubject(@PathVariable("subjectId") long id) {
        try {
            subjectService.deleteSubject(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            HttpStatus httpStatus = e instanceof IllegalStateException ? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR;
            throw new ResponseStatusException(httpStatus, e.getMessage());
        }
    }

    @PutMapping("/{subjectId}")
    public ResponseEntity<Subject> updateSubject(@PathVariable("subjectId") long id, @RequestBody Subject updatedSubject) {
        try {
            return new ResponseEntity<>(subjectService.updateSubject(id, updatedSubject), HttpStatus.OK);
        } catch (Exception e) {
            HttpStatus httpStatus = e instanceof IllegalStateException ? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR;
            throw new ResponseStatusException(httpStatus, e.getMessage());
        }
    }

}
