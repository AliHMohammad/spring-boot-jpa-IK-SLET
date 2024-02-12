package dk.kea.springbootdb.teacher;

import dk.kea.springbootdb.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    private TeacherRepository teacherRepository;


    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getTeachers() {
        return teacherRepository.findAll();
    }

    public Optional<Teacher> getSingleTeacher(long id) {
        return teacherRepository.findById(id);
    }

    public Teacher createTeacher(Teacher newTeacher) {

        boolean exists = teacherRepository.existsByName(newTeacher.getName());

        if (exists) throw new IllegalStateException("Teacher already exists");

        return teacherRepository.save(newTeacher);
    }

    public Optional<Teacher> deleteTeacher(long id) {
        //Vi returnerer det slettede person til frontend, for at at vise, hvad der er blevet slettet
        Optional<Teacher> teacherInDb = teacherRepository.findById(id);

        if (teacherInDb.isPresent()) {
            teacherRepository.deleteById(id);
        }

        return teacherInDb;
    }

    @Transactional
    public Optional<Teacher> updateTeacher(long id, Teacher teacher) {

        Optional<Teacher> teacherInDb = teacherRepository.findById(id);

        if (teacherInDb.isEmpty()) {
            return teacherInDb;
        }

        //Vi bruger dens setter til at opdatere
        //Ændringen gemmer i db'en
        //HUSK @Transactional på metoden for at det virker uden at vi skal gemme igen

        teacherInDb.get().setName(teacher.getName());
        teacherInDb.get().setDateOfBirth(teacher.getDateOfBirth());
        return teacherInDb;
    }


}
