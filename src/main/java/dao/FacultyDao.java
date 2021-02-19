package dao;

import entity.*;
import exception.*;

import java.util.List;

public interface FacultyDao {

    void openDepartment(int facultyId, Department department) throws NoSuchFacultyException;

    void addScheduleToDepartment(int departmentId, Schedule schedule) throws NoSuchScheduleException, NoSuchDepartmentException;

    void addLectureToSchedule(int teacherId, int scheduleId);

    void assignStudentToDepartmentAndDistributeToGroup(int studentId, int departmentId) throws NoSuchGroupException;

    List<Student> getAllStudentsFromDepartment(int departmentId) throws NoSuchDepartmentException;

    void closeDepartment(int departmentId) throws NoSuchDepartmentException;

    void renameDepartment(int departmentId, String departmentName) throws NoSuchDepartmentException;

    Faculty getAllDepartmentsFromFaculty(int facultyId) throws NoSuchFacultyException;

    Department getDepartment(int departmentId) throws NoSuchDepartmentException;

    void hireTeacher(int facultyId, Teacher teacher) throws NoSuchFacultyException;

    void updateTeacher(int teacherId, Teacher teacher) throws NoSuchTeacherException;

    void fireTeacher(int teacherId) throws NoSuchTeacherException;

    Faculty getAllTeachersFromFaculty(int facultyId) throws NoSuchFacultyException;

    Teacher getTeacher(int teacherId) throws NoSuchTeacherException;

    Lecture getLectureOfTeacher(int teacherId) throws NoSuchTeacherException;

}
