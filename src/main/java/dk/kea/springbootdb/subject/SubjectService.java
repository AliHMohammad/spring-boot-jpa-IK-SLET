package dk.kea.springbootdb.subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
