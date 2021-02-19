package daoTest;

import entity.*;
import exception.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.DepartmentService;
import util.HibernateSessionFactoryUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DepartmentDaoTest {
    private DepartmentService departmentService;
    private Session session;
    private Transaction transaction;
    private StudyGroup studyGroup;
    private Schedule schedule;
    private int expectedDepartmentId;
    private int expectedStudentId;
    private int expectedGroupId;
    private int expectedScheduleId;
    private String expectedGroupName;
    private String renameGroup;
    private String studentLastName;
    private String studentFirstName;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private int audience;
    private int amount;


    @BeforeEach
    public void init() {
        departmentService = new DepartmentService();
        session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        transaction = session.beginTransaction();
        expectedGroupId = 1;
        expectedDepartmentId = 1;
        expectedStudentId = 1;
        expectedScheduleId = 1;
        expectedGroupName = "group1";
        renameGroup = "renameGroup1";
        studentFirstName = "rename1";
        studentLastName = "lasr1";
        date = LocalDate.parse("2025-12-12");
        start = LocalTime.parse("12:00");
        end = LocalTime.parse("13:00");
        audience = 177;
        amount = 10;
        schedule = new Schedule(date, audience, start, end);
        studyGroup = new StudyGroup(expectedGroupName, amount);
    }

    @AfterEach
    public void tearDown() {
        transaction.commit();
        session.close();
    }

    @Test
    public void openGroup() {
        Department department = session.get(Department.class, expectedDepartmentId);
        department.addGroup(studyGroup);
        session.persist(department);
        assertEquals(expectedGroupId, studyGroup.getId());
    }

    @Test
    void openGroup_ThrowsException_IfDepartmentIsMissing() {
        Exception exception = assertThrows(NoSuchDepartmentException.class, () ->
                departmentService.openGroup(expectedDepartmentId, studyGroup));
        String expectedMessage = String.format("There is no department with this id:%d", expectedDepartmentId);
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void renameGroup() {
        StudyGroup studyGroup = session.get(StudyGroup.class, expectedGroupId);
        studyGroup.setName(renameGroup);
        session.saveOrUpdate(studyGroup);
        assertEquals(renameGroup, studyGroup.getName());
    }

    @Test
    void renameGroup_ThrowsException_IfGroupIsMissing() {
        Exception exception = assertThrows(NoSuchGroupException.class, () ->
                departmentService.renameGroup(expectedGroupId, null));
        String expectedMessage = String.format("Unable to rename this group with id:%d", expectedGroupId);
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void renameStudent() {
        Student student = session.get(Student.class, expectedStudentId);
        student.setLastName(studentLastName);
        student.setFirstName(studentFirstName);
        session.saveOrUpdate(student);
        assertEquals(studentLastName, student.getLastName());
        assertEquals(studentFirstName, student.getFirstName());
    }

    @Test
    void renameStudent_ThrowsException_IfStudentIsMissing() {
        Exception exception = assertThrows(NoSuchStudentException.class, () ->
                departmentService.renameStudent(expectedStudentId, null, null));
        String expectedMessage = String.format("Unable to rename this student with id:%d", expectedStudentId);
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getStudyGroup() {
        StudyGroup studyGroup = session.get(StudyGroup.class, expectedGroupId);
        assertEquals(expectedGroupId, studyGroup.getId());
    }

    @Test
    void getStudyGroup_ThrowsException_IfGroupIsMissing() {
        Exception exception = assertThrows(NoSuchGroupException.class, () ->
                departmentService.getStudyGroup(expectedGroupId));
        String expectedMessage = String.format("There is no group with this id:%d", expectedStudentId);
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void updateSchedule() {
        Schedule updatedSchedule = session.get(Schedule.class, expectedScheduleId);
        updatedSchedule.setDate(schedule.getDate());
        updatedSchedule.setAudience(schedule.getAudience());
        updatedSchedule.setStartOfLecture(schedule.getStartOfLecture());
        updatedSchedule.setEndOfLecture(schedule.getEndOfLecture());
        session.saveOrUpdate(updatedSchedule);
        Schedule actualSchedule = session.find(Schedule.class, expectedScheduleId);
        assertEquals(updatedSchedule.getDate(), actualSchedule.getDate());
        assertEquals(updatedSchedule.getAudience(), actualSchedule.getAudience());
        assertEquals(updatedSchedule.getStartOfLecture(), actualSchedule.getStartOfLecture());
        assertEquals(updatedSchedule.getEndOfLecture(), actualSchedule.getEndOfLecture());
    }

    @Test
    void updateSchedule_ThrowsException_IfScheduleIsMissing() {
        Exception exception = assertThrows(NoSuchScheduleException.class, () ->
                departmentService.updateSchedule(expectedScheduleId, null));
        String expectedMessage = String.format("Unable to update this schedule with id:%d", expectedScheduleId);
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void closeGroup() {
        StudyGroup studyGroup = session.get(StudyGroup.class, expectedGroupId);
        List<Student> students = studyGroup.getStudents();
        for (Student student : students) {
            student.setStudyGroup(null);
            session.persist(studyGroup);
        }
        session.persist(studyGroup);
        session.delete(studyGroup);
        StudyGroup deletedGroup = session.find(StudyGroup.class, expectedGroupId);
        assertNull(deletedGroup);
    }

    @Test
    void closeGroup_ThrowsException_IfGroupIsMissing() {
        Exception exception = assertThrows(NoSuchGroupException.class, () ->
                departmentService.closeGroup(expectedGroupId));
        String expectedMessage = String.format("Unable to close this group with id:%d", expectedGroupId);
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}
