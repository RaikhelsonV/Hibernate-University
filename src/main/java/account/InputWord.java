package account;

import entity.*;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;

import static util.Validation.validate;

public class InputWord {
    FinalFields finalFields = new FinalFields();
    ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
    Validator validator = vf.getValidator();

    public String readString() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }

    public int readInt() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return Integer.parseInt(reader.readLine());
    }

    public LocalDate readDate() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return LocalDate.parse(reader.readLine());
    }

    public LocalTime readTime() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return LocalTime.parse(reader.readLine());
    }

    public University university() throws IOException {
//        System.out.println("Enter university name: ");
        University university = new University(finalFields.UNIVERSITY_NAME);
        return university;
    }

    public Administration administration() throws IOException {
        System.out.println("Enter lastName, firstName, academicDegree, and speciality: ");
        Administration administration = new Administration(readString(), readString(), readString(), readString(), university());
        validate(administration, validator);
        return administration;
    }

    public Faculty faculty() throws IOException {
        System.out.println("Enter faculty name: ");
        Faculty faculty = new Faculty(readString());
        validate(faculty, validator);
        return faculty;
    }

    public Department department() throws IOException {
        System.out.println("Enter department name: ");
        Department department = new Department(readString());
        validate(department, validator);
        return department;
    }

    public StudyGroup studyGroup() throws IOException {
        System.out.println("Enter group name and amount: ");
        StudyGroup studyGroup = new StudyGroup(readString(), readInt());
        validate(studyGroup, validator);
        return studyGroup;
    }

    public Teacher teacher() throws IOException {
        System.out.println("Enter lastName, firstName, academicDegree, experience and email: ");
        Teacher teacher = new Teacher(readString(), readString(), readString(),
                readInt(), readString(), lecture());
        validate(teacher, validator);
        return teacher;
    }

    public Lecture lecture() throws IOException {
        System.out.println("Enter lecture name: ");
        Lecture lecture = new Lecture(readString());
        validate(lecture, validator);
        return lecture;
    }

    public Schedule schedule() throws IOException {
        System.out.println("Enter date(YYYY-MM-DD), audience, start Of lecture(hh:mm), end Of lecture(hh:mm): ");
        Schedule schedule = new Schedule(readDate(), readInt(), readTime(), readTime());
        validate(schedule, validator);
        return schedule;
    }

    public Student student() throws IOException {
        System.out.println("Enter last name and first name:");
        Student student = new Student(readString(), readString());
        validate(student, validator);
        return student;
    }

}
