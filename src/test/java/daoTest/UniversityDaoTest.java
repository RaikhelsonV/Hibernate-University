package daoTest;

import account.FinalFields;
import entity.*;
import exception.NoSuchFacultyException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import service.UniversityService;
import util.HibernateSessionFactoryUtil;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UniversityDaoTest {
    private UniversityService universityService;
    private FinalFields finalFields;
    private University university;
    private Faculty faculty;
    private int expectedFacultyId;
    private String expectedFacultyName;
    private String facultyRename;
    private Session session;
    private Transaction transaction;


    @BeforeEach
    public void init() {
        universityService = new UniversityService();
        finalFields = new FinalFields();
        session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        transaction = session.beginTransaction();
        expectedFacultyId = 2;
        expectedFacultyName = "Faculty11";
        facultyRename = "RenameOfFaculty1";
        faculty = new Faculty(expectedFacultyName);
        university = new University(finalFields.UNIVERSITY_NAME);
    }

    @AfterEach
    public void tearDown() {
        transaction.commit();
        session.close();
    }

    @Test
    public void Create_University() {
        session.save(university);
        String actualName = university.getName();
        assertEquals(finalFields.UNIVERSITY_NAME, actualName);
    }

    @Test
    public void Open_Faculty() {
        University university = session.get(University.class, finalFields.UNIVERSITY_NAME);
        university.addFaculty(faculty);
        session.persist(university);

        String actualName = faculty.getName();
        assertEquals(expectedFacultyName, actualName);
    }

    @Test
    public void Rename_Faculty() {
        Faculty faculty = session.get(Faculty.class, expectedFacultyId);
        faculty.setName(facultyRename);
        session.saveOrUpdate(faculty);

        String actualName = faculty.getName();
        assertEquals(facultyRename, actualName);

    }

    @Test
    void RenameFaculty_ThrowsException_IfFacultyIsMissing() {
        Exception exception = assertThrows(NoSuchFacultyException.class, () ->
                universityService.renameFaculty(expectedFacultyId, null));
        String expectedMessage = String.format("Unable to rename this faculty with id:%d", expectedFacultyId);
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void Get_Faculty_ById() {
        Faculty faculty = session.get(Faculty.class, expectedFacultyId);
        assertEquals(expectedFacultyId, faculty.getId());
    }

    @Test
    public void Close_Faculty() {
        Faculty faculty = session.get(Faculty.class, expectedFacultyId);
        List<Department> departments = faculty.getDepartments();
        for (int i = 0; i < departments.size(); i++) {
            for (Department department : departments) {
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
                        session.saveOrUpdate(faculty);
                    }
                }
            }
        }
        session.delete(faculty);
        Faculty deletedFaculty = session.find(Faculty.class, expectedFacultyId);
        assertNull(deletedFaculty);

    }

    @Test
    void CloseFaculty_ThrowsException_IfFacultyIsMissing() {
        Exception exception = assertThrows(NoSuchFacultyException.class, () -> universityService.closeFaculty(expectedFacultyId));
        String expectedMessage = String.format("Unable to close this faculty with id:%d", expectedFacultyId);
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}

