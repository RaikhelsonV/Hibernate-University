package util;

import entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {

    public HibernateSessionFactoryUtil() {
    }

    public static SessionFactory getSessionFactory() {

        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(University.class)
                .addAnnotatedClass(Administration.class)
                .addAnnotatedClass(Faculty.class)
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Department.class)
                .addAnnotatedClass(StudyGroup.class)
                .addAnnotatedClass(Teacher.class)
                .addAnnotatedClass(Schedule.class)
                .addAnnotatedClass(Lecture.class)
                .buildSessionFactory();

        return sessionFactory;
    }


}
