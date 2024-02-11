package dk.kea.springbootdb.teacher;

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

    public Teacher getSingleTeacher(long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Teacher by id " + id + " does not exist"));
    }

    public Teacher createTeacher(Teacher newTeacher) {
        Teacher teacherInstance = new Teacher(newTeacher);

        boolean exists = teacherRepository.existsByName(teacherInstance.getName());

        if (exists) throw new IllegalStateException("Teacher already exists");

        return teacherRepository.save(teacherInstance);
    }

    public void deleteTeacher(long id) {
        boolean exists = teacherRepository.existsById(id);

        if (!exists) throw new IllegalStateException("Teacher with id " + id + " does not exist");

        teacherRepository.deleteById(id);
    }

    @Transactional
    public Teacher updateTeacher(long id, Teacher teacher) {
        Teacher updatedTeacher = new Teacher(teacher);
        Teacher teacherInDb = teacherRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Teacher by id " + id + " does not exist"));;


        teacherInDb.setName(updatedTeacher.getName());
        teacherInDb.setDateOfBirth(updatedTeacher.getDateOfBirth());
        return teacherInDb;
    }


}
