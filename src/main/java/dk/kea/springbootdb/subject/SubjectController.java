package dk.kea.springbootdb.subject;

import dk.kea.springbootdb.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/subjects")
public class SubjectController {

    private final SubjectService subjectService;


    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public ResponseEntity<List<Subject>> getSubjects() {
        return new ResponseEntity<>(subjectService.getSubjects(), HttpStatus.OK);
    }

    @GetMapping("/{subjectId}")
    public ResponseEntity<Subject> getSingleSubject(@PathVariable("subjectId") long id) {
        Optional<Subject> subjectInDb = subjectService.getSingleSubject(id);

        return ResponseEntity.of(subjectInDb);
    }

    @PostMapping
    public ResponseEntity<Subject> createSubject(@RequestBody Subject subject) {
        try {
            Subject createdSubject = subjectService.createSubject(subject);

            //Vi bygger en location til response header
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdSubject.getId())
                    .toUri();

            return ResponseEntity.created(location).body(createdSubject);
        } catch (Exception e) {
            HttpStatus httpStatus = e instanceof IllegalStateException ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;
            throw new ResponseStatusException(httpStatus, e.getMessage());
        }

    }

    @DeleteMapping("/{subjectId}")
    public ResponseEntity<Subject> deleteSubject(@PathVariable("subjectId") long id) {
        Optional<Subject> subjectDeleted = subjectService.deleteSubject(id);

        if (subjectDeleted.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{subjectId}")
    public ResponseEntity<Subject> updateSubject(@PathVariable("subjectId") long id, @RequestBody Subject updatedSubject) {

        Optional<Subject> subject = subjectService.updateSubject(id, updatedSubject);

        if (subject.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(subject.get());
    }

    @PutMapping("/{subjectId}/students/{studentId}")
    public ResponseEntity<Subject> enrollStudentToSubject(
            @PathVariable("subjectId") long subjectId,
            @PathVariable("studentId") long studentId
    ) {
        try {
            return ResponseEntity.ok(subjectService.enrollStudentToSubject(subjectId, studentId));
        } catch (Exception e) {
            HttpStatus httpStatus = e instanceof IllegalStateException ? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR;
            throw new ResponseStatusException(httpStatus, e.getMessage());
        }
    }

    @PutMapping("/{subjectId}/teachers/{teacherId}")
    public ResponseEntity<Subject> enrollTeacherToSubject(
            @PathVariable("subjectId") long subjectId,
            @PathVariable("teacherId") long teacherId
    ) {
        try {
            return ResponseEntity.ok(subjectService.enrollTeacherToSubject(subjectId, teacherId));
        } catch (Exception e) {
            HttpStatus httpStatus = e instanceof IllegalStateException ? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR;
            throw new ResponseStatusException(httpStatus, e.getMessage());
        }
    }

}
