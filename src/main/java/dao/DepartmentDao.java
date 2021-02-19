package dao;

import entity.*;
import exception.*;

import java.util.List;

public interface DepartmentDao {

    void openGroup(int departmentId, StudyGroup studyGroup) throws NoSuchDepartmentException;

    void renameGroup(int groupId, String groupName) throws NoSuchGroupException;

    void closeGroup(int groupId) throws NoSuchGroupException;

    University getAllStudents(String universityName);

    List<Student> getAllStudentsFromGroup(int groupId) throws NoSuchGroupException;

    void renameStudent(int studentId, String studentLastName, String studentFirstName) throws NoSuchStudentException;

    Department getAllGroupsFromDepartment(int departmentId) throws NoSuchDepartmentException;

    StudyGroup getStudyGroup(int groupId) throws NoSuchGroupException;

    void updateSchedule(int scheduleId, Schedule schedule) throws NoSuchScheduleException;

}
