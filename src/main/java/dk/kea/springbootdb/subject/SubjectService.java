package dk.kea.springbootdb.subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Subject createSubject(Subject subject) {
        Subject subjectToCreate = new Subject(subject);
        Optional<Subject> subjectInDb = subjectRepository.findSubjectByTitle(subjectToCreate.getTitle());

        if (subjectInDb.isPresent()) {
            throw new IllegalStateException("Subject with title " + subjectToCreate.getTitle() + " alrteady exists in db");
        }

        return subjectRepository.save(subjectToCreate);
    }

    public void deleteSubject(long id) {
        boolean exists = subjectRepository.existsById(id);

        if (!exists) throw new IllegalStateException("Subject with id " + id + " does not exist");

        subjectRepository.deleteById(id);
    }

    @Transactional
    public Subject updateSubject(long id, Subject updatedSubject) {
        Subject subjectInDb = subjectRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "Subject with id " + id + " does not exist"
                ));

        subjectInDb.setTitle(updatedSubject.getTitle());
        subjectInDb.setEcts(updatedSubject.getEcts());

        return subjectInDb;
    }
}
