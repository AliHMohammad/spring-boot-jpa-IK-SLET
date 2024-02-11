package dk.kea.springbootdb.subject;

import dk.kea.springbootdb.student.Student;
import dk.kea.springbootdb.student.StudentRepository;
import dk.kea.springbootdb.teacher.Teacher;
import dk.kea.springbootdb.teacher.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public SubjectService(SubjectRepository subjectRepository, StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
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

    public Subject enrollStudentToSubject(long subjectId, long studentId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalStateException(
                        "Subject with id " + subjectId + " does not exist"
                ));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "Student with id " + studentId + " does not exist"
                ));

        subject.enrollStudent(student);

        //Husk at save dine ændringer
        return subjectRepository.save(subject);
    }

    public Subject enrollTeacherToSubject(long subjectId, long teacherId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalStateException(
                        "Subject with id " + subjectId + " does not exist"
                ));

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalStateException(
                        "Student with id " + teacherId + " does not exist"
                ));

        subject.assignTeacher(teacher);

        //Husk at save dine ændringer
        return subjectRepository.save(subject);
    }
}
