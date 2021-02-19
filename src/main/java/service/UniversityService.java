package service;

import dao.UniversityDao;
import dao.UniversityDaoImpl;
import entity.Faculty;
import entity.Student;
import entity.University;
import exception.NoSuchFacultyException;

import java.util.List;

public class UniversityService {
    private UniversityDao universityDao = new UniversityDaoImpl();

    public UniversityService() {
    }

    public void createUniversity(University university) {
        universityDao.createUniversity(university);
    }

    public void openFaculty(Faculty faculty, String universityName) {
        universityDao.openFaculty(faculty, universityName);
    }

    public List<Student> getAllStudentsFromFaculty(int facultyId) throws NoSuchFacultyException {
        return universityDao.getAllStudentsFromFaculty(facultyId);
    }

    public void renameFaculty(int facultyId, String facultyName) throws NoSuchFacultyException {
        universityDao.renameFaculty(facultyId, facultyName);
    }

    public void closeFaculty(int facultyId) throws NoSuchFacultyException {
        universityDao.closeFaculty(facultyId);
    }

    public University getAllFacultiesFromUniversity(String universityName) {
        return universityDao.getAllFacultiesFromUniversity(universityName);
    }

    public Faculty getFaculty(int facultyId) throws NoSuchFacultyException {
        return universityDao.getFaculty(facultyId);
    }
}
