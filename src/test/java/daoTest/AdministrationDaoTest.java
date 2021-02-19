package daoTest;

import account.FinalFields;
import entity.*;
import exception.NoSuchAdministrationException;
import exception.NoSuchStudentException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.AdministrationService;
import util.HibernateSessionFactoryUtil;

import static org.junit.jupiter.api.Assertions.*;

public class AdministrationDaoTest {
    private AdministrationService administrationService;
    private FinalFields finalFields;
    private Session session;
    private Transaction transaction;
    private University university;
    private Administration administration;
    private Student student;
    private int administrationId;
    private int studentId;
    private String lastName;
    private String firstName;
    private String academicDegree;
    private String speciality;

    @BeforeEach
    public void init() {
        administrationService = new AdministrationService();
        finalFields = new FinalFields();
        session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        transaction = session.beginTransaction();
        administrationId = 1;
        studentId = 1;
        lastName = "lastName";
        firstName = "firstName";
        academicDegree = "acadDegree";
        speciality = "spes";
        university = new University(finalFields.UNIVERSITY_NAME);
        administration = new Administration(lastName, firstName, academicDegree, speciality, university);
        student = new Student(lastName, firstName);
    }

    @AfterEach
    public void tearDown() {
        transaction.commit();
        session.close();
    }

    @Test
    public void appointAdministration() {
        session.save(administration);
        assertEquals(administrationId, administration.getId());
    }

    @Test
    public void updateAdministration() {
        Administration updatedAdministration = session.get(Administration.class, administrationId);
        updatedAdministration.setLastName(administration.getLastName());
        updatedAdministration.setFirstName(administration.getFirstName());
        updatedAdministration.setAcademicDegree(administration.getAcademicDegree());
        updatedAdministration.setSpeciality(administration.getSpeciality());
        session.saveOrUpdate(updatedAdministration);
        Administration actualAdministration = session.find(Administration.class, administrationId);
        assertEquals(updatedAdministration.getLastName(), actualAdministration.getLastName());
        assertEquals(updatedAdministration.getFirstName(), actualAdministration.getFirstName());
        assertEquals(updatedAdministration.getAcademicDegree(), actualAdministration.getAcademicDegree());
        assertEquals(updatedAdministration.getSpeciality(), actualAdministration.getSpeciality());
    }

    @Test
    void updateAdministration_ThrowsException_IfAdministrationIsMissing() {
        Exception exception = assertThrows(NoSuchAdministrationException.class, () ->
                administrationService.updateAdministration(administrationId, null));
        String expectedMessage = String.format("Unable to update this administration with id:%d", administrationId);
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void matriculatedStudent() {
        University university = session.get(University.class, finalFields.UNIVERSITY_NAME);
        university.addStudent(student);
        session.persist(university);
        assertEquals(studentId, student.getId());
    }

    @Test
    public void expelStudent() {
        Student student = session.get(Student.class, studentId);
        Schedule schedule = student.getSchedule();
        schedule.setStudent(null);
        student.setSchedule(null);
        session.saveOrUpdate(student);
        session.delete(student);
        Student deletedStudent = session.find(Student.class, studentId);
        assertNull(deletedStudent);
    }

    @Test
    void expelStudent_ThrowsException_IfStudentIsMissing() {
        Exception exception = assertThrows(NoSuchStudentException.class, () ->
                administrationService.expelStudent(studentId));
        String expectedMessage = String.format("Unable to expel this student with studentId:%d", studentId);
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void fireAdministration() {
        University university = session.get(University.class, finalFields.UNIVERSITY_NAME);
        Administration administration = university.getAdministration();
        university.setAdministration(null);
        session.delete(administration);
        Administration deletedAdministration = session.find(Administration.class, administrationId);
        assertNull(deletedAdministration);
    }

    @Test
    void fireAdministration_ThrowsException_IfAdministrationIsMissing() {
        Exception exception = assertThrows(NoSuchAdministrationException.class, () ->
                administrationService.fireAdministration(finalFields.UNIVERSITY_NAME));
        String expectedMessage = "Unable to fire administration";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
