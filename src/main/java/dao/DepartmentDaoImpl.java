package dao;

import entity.*;
import exception.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateSessionFactoryUtil;

import java.util.List;

public class DepartmentDaoImpl implements DepartmentDao {

    @Override
    public void openGroup(int departmentId, StudyGroup studyGroup) throws NoSuchDepartmentException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Department department = session.get(Department.class, departmentId);
        if (department == null) {
            throw new NoSuchDepartmentException(String.format("There is no department with this id:%d", departmentId));
        }
        department.addGroup(studyGroup);
        session.persist(department);
        transaction.commit();
        System.out.println(String.format("Group %s was opened successfully!", studyGroup.getName()));
        session.close();
    }

    @Override
    public void renameGroup(int groupId, String groupName) throws NoSuchGroupException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        StudyGroup studyGroup = session.get(StudyGroup.class, groupId);
        if (studyGroup == null) {
            throw new NoSuchGroupException(String.format("Unable to rename this group with id:%d", groupId));
        }
        studyGroup.setName(groupName);
        session.saveOrUpdate(studyGroup);
        transaction.commit();
        System.out.println(String.format("Group %s was rename successfully!", studyGroup.getName()));
        session.close();
    }

    @Override
    public void closeGroup(int groupId) throws NoSuchGroupException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        StudyGroup studyGroup = session.get(StudyGroup.class, groupId);
        if (studyGroup == null) {
            throw new NoSuchGroupException(String.format("Unable to close this group with id:%d", groupId));
        }
        List<Student> students = studyGroup.getStudents();
        for (Student student : students) {
            student.setStudyGroup(null);
            session.persist(studyGroup);
        }
        session.persist(studyGroup);
        session.delete(studyGroup);
        transaction.commit();
        System.out.println(String.format("Group %s was closed!", studyGroup.getName()));
        session.close();
    }

    @Override
    public List<Student> getAllStudentsFromGroup(int groupId) throws NoSuchGroupException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        StudyGroup studyGroup = session.get(StudyGroup.class, groupId);
        if (studyGroup == null) {
            throw new NoSuchGroupException(String.format("There is no group with this id:%d", groupId));
        }
        List<Student> students = studyGroup.getStudents();
        transaction.commit();
        session.close();
        return students;
    }

    @Override
    public University getAllStudents(String universityName) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        University university = session.get(University.class, universityName);
        for (Student students : university.getStudents()) {
            System.out.println(students);
        }
        transaction.commit();
        session.close();
        return university;
    }

    @Override
    public void renameStudent(int studentId, String studentLastName, String studentFirstName) throws NoSuchStudentException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Student student = session.get(Student.class, studentId);
        if (student == null) {
            throw new NoSuchStudentException(String.format("Unable to rename this student with id:%d", studentId));
        }
        student.setLastName(studentLastName);
        student.setFirstName(studentFirstName);
        session.saveOrUpdate(student);
        transaction.commit();
        System.out.println(String.format("Student %s was rename successfully!", student.getLastName()));
        session.close();
    }

    @Override
    public Department getAllGroupsFromDepartment(int departmentId) throws NoSuchDepartmentException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Department department = session.get(Department.class, departmentId);
        if (department == null) {
            throw new NoSuchDepartmentException(String.format("There is no department with this id:%d", departmentId));
        }
        for (StudyGroup groups : department.getGroups()) {
            System.out.println(groups);
        }
        transaction.commit();
        session.close();
        return department;
    }

    @Override
    public StudyGroup getStudyGroup(int groupId) throws NoSuchGroupException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        StudyGroup studyGroup = session.get(StudyGroup.class, groupId);
        if (studyGroup == null) {
            throw new NoSuchGroupException(String.format("There is no group with this id:%d", groupId));
        }
        System.out.println(studyGroup);
        transaction.commit();
        session.close();
        return studyGroup;
    }

    @Override
    public void updateSchedule(int scheduleId, Schedule schedule) throws NoSuchScheduleException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Schedule updatedSchedule = session.get(Schedule.class, scheduleId);
        if (updatedSchedule == null) {
            throw new NoSuchScheduleException(String.format("Unable to update this schedule with id:%d", scheduleId));
        }
        updatedSchedule.setDate(schedule.getDate());
        updatedSchedule.setAudience(schedule.getAudience());
        updatedSchedule.setStartOfLecture(schedule.getStartOfLecture());
        updatedSchedule.setEndOfLecture(schedule.getEndOfLecture());
        session.saveOrUpdate(updatedSchedule);
        transaction.commit();
        System.out.println(String.format("Schedule %d was update successfully!", updatedSchedule.getId()));
        session.close();
    }
}
