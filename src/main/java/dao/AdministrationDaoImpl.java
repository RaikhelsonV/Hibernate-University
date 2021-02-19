package dao;

import entity.*;
import exception.NoSuchAdministrationException;
import exception.NoSuchStudentException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateSessionFactoryUtil;

public class AdministrationDaoImpl implements AdministrationDao {

    @Override
    public void appointAdministration(Administration administration) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(administration);
        System.out.println(String.format("Administration %s was appointed successfully!", administration.getLastName()));
        session.close();
    }

    @Override
    public void fireAdministration(String universityName) throws NoSuchAdministrationException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        University university = session.get(University.class, universityName);
        Administration administration = university.getAdministration();
        if (administration == null) {
            throw new NoSuchAdministrationException("Unable to fire administration");
        }
        university.setAdministration(null);
        session.delete(administration);
        transaction.commit();
        System.out.println(String.format("Administration %s was fired!", administration.getLastName()));
        session.close();
    }

    @Override
    public void updateAdministration(int administrationId, Administration administration) throws NoSuchAdministrationException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Administration updatedAdministration = session.get(Administration.class, administrationId);
        if (updatedAdministration == null) {
            throw new NoSuchAdministrationException(String.format("Unable to update this administration with id:%d", administrationId));
        }
        updatedAdministration.setLastName(administration.getLastName());
        updatedAdministration.setFirstName(administration.getFirstName());
        updatedAdministration.setAcademicDegree(administration.getAcademicDegree());
        updatedAdministration.setSpeciality(administration.getSpeciality());
        session.saveOrUpdate(updatedAdministration);
        transaction.commit();
        System.out.println(String.format("Administration %s was updated successfully!", administration.getLastName()));
        session.close();
    }

    @Override
    public void matriculatedStudent(Student student, String universityName) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        University university = session.get(University.class, universityName);
        university.addStudent(student);
        session.persist(university);
        transaction.commit();
        System.out.println(String.format("Student %s was matriculated successfully to university %s!", student.getLastName(), university.getName()));
        session.close();
    }

    @Override
    public void expelStudent(int studentId) throws NoSuchStudentException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Student student = session.get(Student.class, studentId);
        if (student == null) {
            throw new NoSuchStudentException(String.format("Unable to expel this student with studentId:%d", studentId));
        }
        Schedule schedule = student.getSchedule();
        schedule.setStudent(null);

        student.setSchedule(null);
        session.saveOrUpdate(student);
        session.delete(student);
        transaction.commit();
        System.out.println(String.format("Student %s was expelled!", student.getLastName()));
        session.close();
    }
}
