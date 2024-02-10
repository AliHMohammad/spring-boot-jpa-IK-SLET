package dk.kea.springbootdb.subject;

import dk.kea.springbootdb.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<Subject> getSubjects() {
        return subjectService.getSubjects();
    }

    @GetMapping("/{subjectId}")
    public Subject getSingleSubject(@PathVariable("subjectId") long id) {
        return subjectService.getSingleSubject(id);
    }

    @PostMapping
    public void createSubject(@RequestBody Subject subject) {
        subject.createSubject(subject);
    }

    @DeleteMapping("/{subjectId}")
    public void deleteSubject(@PathVariable("subjectId") long id) {

    }
}
