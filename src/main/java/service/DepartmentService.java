package service;

import dao.DepartmentDao;
import dao.DepartmentDaoImpl;
import entity.*;
import exception.*;

import javax.validation.Valid;
import java.util.List;

public class DepartmentService {
    private DepartmentDao departmentDao = new DepartmentDaoImpl();

    public DepartmentService() {
    }

    public void openGroup(int departmentId, @Valid StudyGroup studyGroup) throws NoSuchDepartmentException {
        departmentDao.openGroup(departmentId, studyGroup);
    }

    public void renameGroup(int groupId, String groupName) throws NoSuchGroupException {
        departmentDao.renameGroup(groupId, groupName);
    }

    public void closeGroup(int studyGroupId) throws NoSuchGroupException {
        departmentDao.closeGroup(studyGroupId);
    }

    public University getAllStudents(String universityName) {
        return departmentDao.getAllStudents(universityName);
    }

    public List<Student> getAllStudentsFromGroup(int groupId) throws NoSuchGroupException {
        return departmentDao.getAllStudentsFromGroup(groupId);
    }

    public void renameStudent(int studentId, String studentLastName, String studentFirstName) throws NoSuchStudentException {
        departmentDao.renameStudent(studentId, studentLastName, studentFirstName);
    }

    public Department getAllGroupsFromDepartment(int departmentId) throws NoSuchDepartmentException {
        return departmentDao.getAllGroupsFromDepartment(departmentId);
    }

    public StudyGroup getStudyGroup(int groupId) throws NoSuchGroupException {
        return departmentDao.getStudyGroup(groupId);
    }

    public void updateSchedule(int scheduleId, @Valid Schedule schedule) throws NoSuchScheduleException {
        departmentDao.updateSchedule(scheduleId, schedule);
    }

}
