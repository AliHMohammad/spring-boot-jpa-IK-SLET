package dk.kea.springbootdb.subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }
    public List<Subject> getSubjects() {
        return subjectRepository.findAll();
    }

    public Subject getSingleSubject(long id) {
        return subjectRepository.findById(id)
                .orElseThrow(()-> new IllegalStateException(
                        "Subject with id " + id + " could not be found"
                ));
    }

    public void createSubject(Subject subject) {
        Subject subjectToCreate = new Subject(subject);
        Optional<Subject> subjectInDb = subjectRepository.findSubjectByTitle(subject.getTitle());

        if (subjectInDb.isPresent()) {
            throw new IllegalStateException("Subject with title " + subjectToCreate.getTitle() + " alrteady exists in db");
        }

        subjectRepository.save(subjectToCreate);
    }
}
