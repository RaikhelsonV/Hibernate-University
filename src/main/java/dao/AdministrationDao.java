package dao;

import entity.Administration;
import entity.Student;
import exception.NoSuchAdministrationException;
import exception.NoSuchStudentException;

public interface AdministrationDao {

    void appointAdministration(Administration administration);

    void fireAdministration(String universityName) throws NoSuchAdministrationException;

    void updateAdministration(int administrationId, Administration administration) throws NoSuchAdministrationException;

    void matriculatedStudent(Student student, String universityName);

    void expelStudent(int studentId) throws NoSuchStudentException;

}
