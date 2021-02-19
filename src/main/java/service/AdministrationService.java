package service;

import dao.AdministrationDao;
import dao.AdministrationDaoImpl;
import entity.Administration;
import entity.Student;
import exception.NoSuchAdministrationException;
import exception.NoSuchStudentException;

import javax.validation.Valid;

public class AdministrationService {

    private AdministrationDao administrationDao = new AdministrationDaoImpl();

    public AdministrationService() {
    }

    public void appointAdministration(@Valid Administration administration) {
        administrationDao.appointAdministration(administration);
    }

    public void fireAdministration(String universityName) throws NoSuchAdministrationException {
        administrationDao.fireAdministration(universityName);
    }

    public void updateAdministration(int administrationId, @Valid Administration administration) throws NoSuchAdministrationException {
        administrationDao.updateAdministration(administrationId, administration);
    }

    public void matriculatedStudent(@Valid Student student, String universityName) {
        administrationDao.matriculatedStudent(student, universityName);
    }

    public void expelStudent(int studentId) throws NoSuchStudentException {
        administrationDao.expelStudent(studentId);
    }
}
