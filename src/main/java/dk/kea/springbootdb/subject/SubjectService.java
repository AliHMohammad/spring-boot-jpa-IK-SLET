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

    public Optional<Subject> getSingleSubject(long id) {
        return subjectRepository.findById(id);
    }

    public Subject createSubject(Subject subject) {

        Optional<Subject> subjectInDb = subjectRepository.findSubjectByTitle(subject.getTitle());

        if (subjectInDb.isPresent()) {
            throw new IllegalStateException("Subject with title " + subject.getTitle() + " alrteady exists in db");
        }

        return subjectRepository.save(subject);
    }

    public Optional<Subject> deleteSubject(long id) {
        Optional<Subject> subjectInDb = subjectRepository.findById(id);

        if (subjectInDb.isPresent()) {
            subjectRepository.delete(subjectInDb.get());
        }

        return subjectInDb;
    }

    @Transactional
    public Optional<Subject> updateSubject(long id, Subject updatedSubject) {
        Optional<Subject> subjectInDb = subjectRepository.findById(id);

        if (subjectInDb.isEmpty()) {
            return subjectInDb;
        }

        //Vi bruger dens setter til at opdatere
        //Ændringen gemmer i db'en
        //HUSK @Transactional på metoden for at det virker
        subjectInDb.get().setTitle(updatedSubject.getTitle());
        subjectInDb.get().setEcts(updatedSubject.getEcts());

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
