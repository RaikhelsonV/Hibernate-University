package service;

import dao.FacultyDao;
import dao.FacultyDaoImpl;
import entity.*;
import exception.*;

import javax.validation.Valid;
import java.util.List;

public class FacultyService {
    private FacultyDao facultyDao = new FacultyDaoImpl();

    public FacultyService() {
    }

    public void openDepartment(int facultyId, @Valid Department department) throws NoSuchFacultyException {
        facultyDao.openDepartment(facultyId, department);
    }

    public void addScheduleToDepartment(int departmentId, @Valid Schedule schedule) throws NoSuchScheduleException, NoSuchDepartmentException {
        facultyDao.addScheduleToDepartment(departmentId, schedule);
    }

    public void addLectureToSchedule(int teacherId, int scheduleId) {
        facultyDao.addLectureToSchedule(teacherId, scheduleId);
    }

    public void assignStudentToDepartment(int studentId, int departmentId) throws NoSuchGroupException {
        facultyDao.assignStudentToDepartmentAndDistributeToGroup(studentId, departmentId);
    }

    public List<Student> getAllStudentsFromDepartment(int departmentId) throws NoSuchDepartmentException {
        return facultyDao.getAllStudentsFromDepartment(departmentId);
    }

    public void renameDepartment(int departmentId, String departmentName) throws NoSuchDepartmentException {
        facultyDao.renameDepartment(departmentId, departmentName);
    }

    public void closeDepartment(int departmentId) throws NoSuchDepartmentException {
        facultyDao.closeDepartment(departmentId);
    }

    public Faculty getAllDepartmentsFromFaculty(int facultyId) throws NoSuchFacultyException {
        return facultyDao.getAllDepartmentsFromFaculty(facultyId);
    }

    public Department getDepartment(int departmentId) throws NoSuchDepartmentException {
        return facultyDao.getDepartment(departmentId);
    }

    public void hireTeacher(int facultyId, @Valid Teacher teacher) throws NoSuchFacultyException {
        facultyDao.hireTeacher(facultyId, teacher);
    }

    public void updateTeacher(int teacherId, Teacher teacher) throws NoSuchTeacherException {
        facultyDao.updateTeacher(teacherId, teacher);
    }

    public void fireTeacher(int teacherId) throws NoSuchTeacherException {
        facultyDao.fireTeacher(teacherId);
    }

    public Faculty getAllTeachersFromFaculty(int facultyId) throws NoSuchFacultyException {
        return facultyDao.getAllTeachersFromFaculty(facultyId);
    }

    public Teacher getTeacher(int teacherId) throws NoSuchTeacherException {
        return facultyDao.getTeacher(teacherId);
    }

    public Lecture getLectureOfTeacher(int teacherId) throws NoSuchTeacherException {
        return facultyDao.getLectureOfTeacher(teacherId);
    }
}
