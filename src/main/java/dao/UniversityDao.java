package dao;

import entity.Faculty;
import entity.Student;
import entity.University;
import exception.NoSuchFacultyException;

import java.util.List;

public interface UniversityDao {

    void createUniversity(University university);

    void openFaculty(Faculty faculty, String universityName);

    void renameFaculty(int facultyId, String facultyName) throws NoSuchFacultyException;

    List<Student> getAllStudentsFromFaculty(int facultyId) throws NoSuchFacultyException;

    void closeFaculty(int facultyId) throws NoSuchFacultyException;

    University getAllFacultiesFromUniversity(String universityName);

    Faculty getFaculty(int facultyId) throws NoSuchFacultyException;

}
