package dao;

import entity.*;
import exception.NoSuchFacultyException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateSessionFactoryUtil;

import java.util.List;

public class UniversityDaoImpl implements UniversityDao {
    @Override
    public void createUniversity(University university) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(university);
        transaction.commit();
        System.out.println(String.format("University %s was created successfully!", university.getName()));
        session.close();
    }

    @Override
    public void openFaculty(Faculty faculty, String universityName) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        University university = session.get(University.class, universityName);
        university.addFaculty(faculty);
        session.persist(university);
        transaction.commit();
        System.out.println(String.format("Faculty %s was opened successfully!", faculty.getName()));
        session.close();
    }

    @Override
    public void renameFaculty(int facultyId, String facultyName) throws NoSuchFacultyException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Faculty faculty = session.get(Faculty.class, facultyId);
        if (faculty == null) {
            throw new NoSuchFacultyException(String.format("Unable to rename this faculty with id:%d", facultyId));
        }
        faculty.setName(facultyName);
        session.saveOrUpdate(faculty);
        transaction.commit();
        System.out.println(String.format("Faculty %s was updated successfully!", faculty.getName()));
        session.close();
    }

    @Override
    public void closeFaculty(int facultyId) throws NoSuchFacultyException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Faculty faculty = session.get(Faculty.class, facultyId);
        if (faculty == null) {
            throw new NoSuchFacultyException(String.format("Unable to close this faculty with id:%d", facultyId));
        }
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
        transaction.commit();
        System.out.println(String.format("Faculty %s was closed!", faculty.getName()));
        session.close();
    }

    @Override
    public List<Student> getAllStudentsFromFaculty(int facultyId) throws NoSuchFacultyException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Faculty faculty = session.get(Faculty.class, facultyId);
        if (faculty == null) {
            throw new NoSuchFacultyException(String.format("There is no faculty with this id:%d", facultyId));
        }
        List<Department> departments = faculty.getDepartments();
        List<Student> students = null;
        for (int i = 0; i < departments.size(); i++) {
            for (Department department : departments) {
                List<StudyGroup> groups = department.getGroups();
                for (StudyGroup studyGroup : groups) {
                    students = studyGroup.getStudents();
                    System.out.println(students);
                }
            }
        }
        transaction.commit();
        session.close();
        return students;
    }

    @Override
    public University getAllFacultiesFromUniversity(String universityName) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        University university = session.get(University.class, universityName);
        for (Faculty faculties : university.getFaculties()) {
            System.out.println(faculties);
        }
        transaction.commit();
        session.close();
        return university;
    }

    @Override
    public Faculty getFaculty(int facultyId) throws NoSuchFacultyException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Faculty faculty = session.get(Faculty.class, facultyId);
        if (faculty == null) {
            throw new NoSuchFacultyException(String.format("There is no faculty with this id:%d", facultyId));
        }
        System.out.println(faculty);
        transaction.commit();
        session.close();
        return faculty;
    }
}
