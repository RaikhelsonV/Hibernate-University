package daoTest;

import account.FinalFields;
import entity.*;
import exception.NoSuchDepartmentException;
import exception.NoSuchFacultyException;
import exception.NoSuchGroupException;
import exception.NoSuchTeacherException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import service.FacultyService;
import util.HibernateSessionFactoryUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class FacultyDaoTest {
    private FacultyService facultyService;
    private FinalFields finalFields;
    private Session session;
    private Transaction transaction;
    private Department department;
    private Schedule schedule;
    private Teacher teacher;
    private Lecture lecture;
    private int expectedFacultyId;
    private int expectedDepartmentId;
    private int expectedScheduleId;
    private int expectedTeacherId;
    private int expectedStudentId;
    private int expectedLectureId;
    private int expectedGroupId;
    private String expectedDepartmentName;
    private String renameDepartment;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private int audience;
    private String lastName;
    private String firstName;
    private int experience;
    private String academicDegree;
    private String email;
    private String lectureName;


    @BeforeEach
    public void init() {
        facultyService = new FacultyService();
        session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        transaction = session.beginTransaction();
        expectedFacultyId = 2;
        expectedDepartmentId = 2;
        expectedDepartmentName = "Dep1";
        renameDepartment = "renameDep1";
        expectedStudentId = 2;
        expectedScheduleId = 1;
        expectedTeacherId = 1;
        expectedLectureId = 1;
        expectedGroupId = 1;
        date = LocalDate.parse("2020-12-12");
        start = LocalTime.parse("10:00");
        end = LocalTime.parse("11:00");
        audience = 100;
        lastName = "nameRe";
        firstName = "nameRe";
        academicDegree = "degree";
        experience = 4;
        email = "email";
        lectureName = "nameRe";
        lecture = new Lecture(lectureName);
        teacher = new Teacher(lastName, firstName, academicDegree, experience, email, lecture);
        schedule = new Schedule(date, audience, start, end);
        department = new Department(expectedDepartmentName);
    }

    @AfterEach
    public void tearDown() {
        transaction.commit();
        session.close();

    }

    @Test
    public void openDepartment() {
        Faculty faculty = session.get(Faculty.class, expectedFacultyId);
        faculty.addDepartment(department);
        session.persist(faculty);
        assertEquals(expectedDepartmentId, department.getId());
    }

    @Test
    void openDepartment_ThrowsException_IfFacultyIsMissing() {
        Exception exception = assertThrows(NoSuchFacultyException.class, () ->
                facultyService.openDepartment(expectedFacultyId, department));
        String expectedMessage = String.format("There is no faculty with this id:%d", expectedFacultyId);
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void renameDepartment() {
        Department department = session.get(Department.class, expectedDepartmentId);
        department.setName(renameDepartment);
        session.saveOrUpdate(department);
        assertEquals(renameDepartment, department.getName());
    }

    @Test
    void renameDepartment_ThrowsException_IfDepartmentIsMissing() {
        Exception exception = assertThrows(NoSuchDepartmentException.class, () ->
                facultyService.renameDepartment(expectedDepartmentId, null));
        String expectedMessage = String.format("Unable to rename this department with id:%d", expectedDepartmentId);
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void addScheduleToDepartment() {
        Department department = session.get(Department.class, expectedDepartmentId);
        schedule.setDepartment(department);
        session.persist(schedule);
        session.persist(department);
        assertEquals(expectedDepartmentId, schedule.getDepartment().getId());
    }

    @Test
    public void getDepartment() {
        Department department = session.get(Department.class, expectedDepartmentId);
        assertEquals(expectedDepartmentId, department.getId());
    }

    @Test
    void getDepartment_ThrowsException_IfDepartmentIsMissing() {
        Exception exception = assertThrows(NoSuchDepartmentException.class, () -> facultyService.getDepartment(expectedDepartmentId));
        String expectedMessage = String.format("There is no department with this id:%d", expectedDepartmentId);
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void hireTeacher() {
        Faculty faculty = session.get(Faculty.class, expectedFacultyId);
        faculty.addTeacher(teacher);
        session.persist(faculty);
        List<Teacher> teachers = faculty.getTeachers();
        for (Teacher actualTeacher : teachers) {
            assertEquals(expectedTeacherId, actualTeacher.getId());
        }
    }

    @Test
    void hireTeacher_ThrowsException_IfFacultyIsMissing() {
        Exception exception = assertThrows(NoSuchFacultyException.class, () -> facultyService.hireTeacher(expectedFacultyId, teacher));
        String expectedMessage = String.format("There is no faculty with this id:%d", expectedFacultyId);
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void updateTeacher() {
        Teacher updatedTeacher = session.get(Teacher.class, expectedTeacherId);
        updatedTeacher.setLastName(teacher.getLastName());
        updatedTeacher.setFirstName(teacher.getFirstName());
        updatedTeacher.setAcademicDegree(teacher.getAcademicDegree());
        updatedTeacher.setExperience(teacher.getExperience());
        updatedTeacher.setEmail(teacher.getEmail());
        session.saveOrUpdate(updatedTeacher);

        Teacher updatedActualTeacher = session.find(Teacher.class, expectedTeacherId);

        assertEquals(updatedTeacher.getLastName(), updatedActualTeacher.getLastName());
        assertEquals(updatedTeacher.getFirstName(), updatedActualTeacher.getFirstName());
        assertEquals(updatedTeacher.getAcademicDegree(), updatedActualTeacher.getAcademicDegree());
        assertEquals(updatedTeacher.getExperience(), updatedActualTeacher.getExperience());
        assertEquals(updatedTeacher.getEmail(), updatedActualTeacher.getEmail());
    }

    @Test
    void updateTeacher_ThrowsException_IfTeacherIsMissing() {
        Exception exception = assertThrows(NoSuchTeacherException.class, () ->
                facultyService.updateTeacher(expectedTeacherId, null));
        String expectedMessage = String.format("Unable to update this teacher with id:%d", expectedTeacherId);
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getTeacher() {
        Teacher teacher = session.get(Teacher.class, expectedTeacherId);
        assertEquals(expectedTeacherId, teacher.getId());
    }

    @Test
    void getTeacher_ThrowsException_IfTeacherIsMissing() {
        Exception exception = assertThrows(NoSuchTeacherException.class, () -> facultyService.getTeacher(expectedTeacherId));
        String expectedMessage = String.format("There is no teacher with this id:%d", expectedTeacherId);
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getLectureOfTeacher() {
        Teacher teacher = session.get(Teacher.class, expectedTeacherId);
        Lecture lecture = teacher.getLecture();
        assertEquals(expectedLectureId, lecture.getId());
    }

    @Test
    void getLectureOfTeacher_ThrowsException_IfTeacherIsMissing() {
        Exception exception = assertThrows(NoSuchTeacherException.class, () -> facultyService.getTeacher(expectedTeacherId));
        String expectedMessage = String.format("There is no teacher with this id:%d", expectedTeacherId);
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void addLectureToSchedule() {
        Schedule schedule = session.get(Schedule.class, expectedScheduleId);
        Teacher teacher = session.get(Teacher.class, expectedTeacherId);
        Lecture lecture = teacher.getLecture();
        lecture.addSchedule(schedule);
        session.persist(schedule);
        session.persist(lecture);

        List<Schedule> schedules = lecture.getSchedules();
        for (Schedule actualSchedule : schedules) {
            assertEquals(expectedScheduleId, actualSchedule.getId());
        }
    }

    @Test
    public void assignStudentToDepartmentAndDistributeToGroup() {
        Department department = session.get(Department.class, expectedDepartmentId);
        Student student = session.get(Student.class, expectedStudentId);
        Schedule schedule = department.getSchedule();
        student.setSchedule(schedule);
        int actualScheduleId = student.getSchedule().getId();
        assertEquals(expectedScheduleId, actualScheduleId);

        List<StudyGroup> groups = department.getGroups();
        for (int i = 0; i < groups.size(); ) {

            StudyGroup studyGroup = groups.get(i);
            int amount = studyGroup.getAmount();

            if (amount <= finalFields.MAX_STUDENT_IN_GROUP) {
                studyGroup.addStudent(student);
                studyGroup.setAmount(++amount);
                session.persist(studyGroup);
                int actualGroupId = student.getStudyGroup().getId();
                assertEquals(expectedGroupId, actualGroupId);
                break;
            } else if (amount > finalFields.MAX_STUDENT_IN_GROUP) {
                i++;
            } else {
                System.out.println(String.format("The group %s is full or student %d is already enrolled in the group.", studyGroup.getName(), expectedStudentId));
            }
        }
        session.persist(department);
    }

    @Test
    void distributeToGroup_ThrowsException_IfGroupIsMissing() {
        Exception exception = assertThrows(NoSuchGroupException.class, () ->
                facultyService.assignStudentToDepartment(expectedStudentId, expectedDepartmentId));
        String expectedMessage = String.format("The department %d has no groups", expectedDepartmentId);
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    public void closeDepartment() {
        Department department = session.get(Department.class, expectedDepartmentId);
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
        Department deletedDepartment = session.find(Department.class, expectedDepartmentId);
        assertNull(deletedDepartment);
    }

    @Test
    void CloseDepartment_ThrowsException_IfDepartmentIsMissing() {
        Exception exception = assertThrows(NoSuchDepartmentException.class, () -> facultyService.closeDepartment(expectedDepartmentId));
        String expectedMessage = String.format("Unable to close this department with id:%d", expectedDepartmentId);
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void fireTeacher() {
        Teacher teacher = session.get(Teacher.class, expectedTeacherId);
        Lecture lecture = teacher.getLecture();
        lecture.setSchedules(null);
        session.persist(lecture);
        session.delete(teacher);
        Teacher deletedTeacher = session.find(Teacher.class, expectedTeacherId);
        assertNull(deletedTeacher);
    }

    @Test
    void fireTeacher_ThrowsException_IfTeacherIsMissing() {
        Exception exception = assertThrows(NoSuchTeacherException.class, () -> facultyService.fireTeacher(expectedTeacherId));
        String expectedMessage = String.format("Unable to fire this teacher with id:%d", expectedTeacherId);
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
