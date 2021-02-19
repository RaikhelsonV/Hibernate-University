package dao;

import account.FinalFields;
import entity.*;
import exception.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateSessionFactoryUtil;

import java.util.List;

public class FacultyDaoImpl implements FacultyDao {
    FinalFields finalFields = new FinalFields();

    @Override
    public void openDepartment(int facultyId, Department department) throws NoSuchFacultyException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Faculty faculty = session.get(Faculty.class, facultyId);
        if (faculty == null) {
            throw new NoSuchFacultyException(String.format("There is no faculty with this id:%d", facultyId));
        }
        faculty.addDepartment(department);
        session.persist(faculty);
        transaction.commit();
        System.out.println(String.format("Department %s was opened successfully!", department.getName()));
        session.close();
    }

    @Override
    public void renameDepartment(int departmentId, String departmentName) throws NoSuchDepartmentException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Department department = session.get(Department.class, departmentId);
        if (department == null) {
            throw new NoSuchDepartmentException(String.format("Unable to rename this department with id:%d", departmentId));
        }
        department.setName(departmentName);
        session.saveOrUpdate(department);
        transaction.commit();
        System.out.println(String.format("Department %s was updated successfully!", department.getName()));
        session.close();
    }

    @Override
    public void addScheduleToDepartment(int departmentId, Schedule schedule) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Department department = session.get(Department.class, departmentId);
        schedule.setDepartment(department);
        session.persist(schedule);
        session.persist(department);
        transaction.commit();
        System.out.println(String.format("Schedule %d was added successfully!", schedule.getId()));
        session.close();
    }

    @Override
    public void addLectureToSchedule(int teacherId, int scheduleId) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Schedule schedule = session.get(Schedule.class, scheduleId);
        Teacher teacher = session.get(Teacher.class, teacherId);
        Lecture lecture = teacher.getLecture();
        lecture.addSchedule(schedule);
        session.persist(schedule);
        session.persist(lecture);
        transaction.commit();
        System.out.println(String.format("Schedule %d was added successfully!", schedule.getId()));
        session.close();
    }

    @Override
    public void assignStudentToDepartmentAndDistributeToGroup(int studentId, int departmentId) throws NoSuchGroupException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Department department = session.get(Department.class, departmentId);
        Student student = session.get(Student.class, studentId);
        Schedule schedule = department.getSchedule();
        student.setSchedule(schedule);

        List<StudyGroup> groups = department.getGroups();
        if (groups.size() == 0) {
            throw new NoSuchGroupException(String.format("The department %d has no groups", departmentId));
        }
        for (int i = 0; i < groups.size(); ) {

            StudyGroup studyGroup = groups.get(i);
            int amount = studyGroup.getAmount();

            if (amount <= finalFields.MAX_STUDENT_IN_GROUP) {
                studyGroup.addStudent(student);
                studyGroup.setAmount(++amount);
                session.persist(studyGroup);
                System.out.println(String.format("Student %d was successfully enrolled in the group %s!", studentId, studyGroup.getName()));
                break;
            } else if (amount > finalFields.MAX_STUDENT_IN_GROUP) {
                i++;
            } else {
                System.out.println(String.format("The group %s is full or student %d is already enrolled in the group.", studyGroup.getName(), studentId));
            }
        }
        session.persist(department);
        System.out.println(String.format("Student %s was successfully enrolled in the schedule %d.", student.getLastName(), schedule.getId()));

        transaction.commit();
        session.close();
    }

    @Override
    public void closeDepartment(int departmentId) throws NoSuchDepartmentException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Department department = session.get(Department.class, departmentId);
        if (department == null) {
            throw new NoSuchDepartmentException(String.format("Unable to close this department with id:%d", departmentId));
        }
        List<StudyGroup> groups = department.getGroups();
        for (StudyGroup studyGroup : groups) {
            List<Student> studentsOfGroup = studyGroup.getStudents();
            for (Student student : studentsOfGroup) {
                student.setStudyGroup(null);
                session.persist(studyGroup);

                Schedule schedule = student.getSchedule();
                schedule.setStudent(null);
                student.setSchedule(null);
                session.persist(student);

                session.saveOrUpdate(department);
            }
        }
        session.delete(department);
        transaction.commit();
        System.out.println(String.format("Department %s was closed!", department.getName()));
        session.close();
    }

    @Override
    public List<Student> getAllStudentsFromDepartment(int departmentId) throws NoSuchDepartmentException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Department department = session.get(Department.class, departmentId);
        if (department == null) {
            throw new NoSuchDepartmentException(String.format("There is no department with this id:%d", departmentId));
        }
        List<StudyGroup> groups = department.getGroups();
        List<Student> students = null;
        for (int i = 0; i < groups.size(); i++) {
            for (StudyGroup studyGroup : groups) {
                students = studyGroup.getStudents();
                System.out.println(students);
            }
        }
        transaction.commit();
        session.close();
        return students;
    }

    @Override
    public Faculty getAllDepartmentsFromFaculty(int facultyId) throws NoSuchFacultyException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Faculty faculty = session.get(Faculty.class, facultyId);
        if (faculty == null) {
            throw new NoSuchFacultyException(String.format("There is no faculty with this id:%d", facultyId));
        }
        for (Department departments : faculty.getDepartments()) {
            System.out.println(departments);
        }
        transaction.commit();
        session.close();
        return faculty;
    }

    @Override
    public Department getDepartment(int departmentId) throws NoSuchDepartmentException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Department department = session.get(Department.class, departmentId);
        if (department == null) {
            throw new NoSuchDepartmentException(String.format("There is no department with this id:%d", departmentId));
        }
        System.out.println(department);
        transaction.commit();
        session.close();
        return department;
    }

    @Override
    public void hireTeacher(int facultyId, Teacher teacher) throws NoSuchFacultyException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Faculty faculty = session.get(Faculty.class, facultyId);
        if (faculty == null) {
            throw new NoSuchFacultyException(String.format("There is no faculty with this id:%d", facultyId));
        }
        faculty.addTeacher(teacher);
        session.persist(faculty);
        transaction.commit();
        System.out.println(String.format("Teacher %s was hired successfully!", teacher.getLastName()));
        session.close();
    }

    @Override
    public void updateTeacher(int teacherId, Teacher teacher) throws NoSuchTeacherException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Teacher updatedTeacher = session.get(Teacher.class, teacherId);
        if (updatedTeacher == null) {
            throw new NoSuchTeacherException(String.format("Unable to update this teacher with id:%d", teacherId));
        }
        updatedTeacher.setLastName(teacher.getLastName());
        updatedTeacher.setFirstName(teacher.getFirstName());
        updatedTeacher.setAcademicDegree(teacher.getAcademicDegree());
        updatedTeacher.setExperience(teacher.getExperience());
        updatedTeacher.setEmail(teacher.getEmail());
        session.saveOrUpdate(updatedTeacher);
        transaction.commit();
        System.out.println(String.format("Teacher %s was updated successfully!", updatedTeacher.getLastName()));
        session.close();
    }

    @Override
    public void fireTeacher(int teacherId) throws NoSuchTeacherException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Teacher teacher = session.get(Teacher.class, teacherId);
        if (teacher == null) {
            throw new NoSuchTeacherException(String.format("Unable to fire this teacher with id:%d", teacherId));
        }

        Lecture lecture = teacher.getLecture();
        lecture.setSchedules(null);
        session.persist(lecture);

        session.delete(teacher);
        transaction.commit();
        System.out.println(String.format("Teacher %s was fired!", teacher.getLastName()));
        session.close();
    }

    @Override
    public Faculty getAllTeachersFromFaculty(int facultyId) throws NoSuchFacultyException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Faculty faculty = session.get(Faculty.class, facultyId);
        if (faculty == null) {
            throw new NoSuchFacultyException(String.format("There is no faculty with this id:%d", facultyId));
        }
        for (Teacher teachers : faculty.getTeachers()) {
            System.out.println(teachers);
        }
        transaction.commit();
        session.close();
        return faculty;
    }

    @Override
    public Teacher getTeacher(int teacherId) throws NoSuchTeacherException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Teacher teacher = session.get(Teacher.class, teacherId);
        if (teacher == null) {
            throw new NoSuchTeacherException(String.format("There is no teacher with this id:%d", teacherId));
        }
        System.out.println(teacher);
        transaction.commit();
        session.close();
        return teacher;
    }

    @Override
    public Lecture getLectureOfTeacher(int teacherId) throws NoSuchTeacherException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Teacher teacher = session.get(Teacher.class, teacherId);
        if (teacher == null) {
            throw new NoSuchTeacherException(String.format("There is no teacher with this id:%d", teacherId));
        }
        Lecture lecture = teacher.getLecture();
        System.out.println(lecture);
        transaction.commit();
        session.close();
        return lecture;
    }
}
