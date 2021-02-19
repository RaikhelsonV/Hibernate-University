package validatorWithAddMethodTest;

import entity.Lecture;
import entity.Teacher;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class TeacherTest {
    private static Validator validator;
    private Teacher teacher;
    private Lecture lecture;
    private String lastName;
    private String firstName;
    private int experience;
    private String academicDegree;
    private String email;
    private String lectureName;

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Before
    public void init() {
        lectureName = "lecture";
        lecture = new Lecture(lectureName);
        lastName = "lastN";
        firstName = "firstN";
        experience = 7;
        academicDegree = "degree";
        email = "email";
        teacher = new Teacher(lastName, firstName, academicDegree, experience, email, lecture);
    }

    @Test
    public void LastName_Should_NotBeNull() {
        Teacher teacher = new Teacher(
                null,
                "first",
                "degree",
                5,
                "email",
                lecture);

        Set<ConstraintViolation<Teacher>> validate = validator.validate(teacher);

        assertEquals(1, validate.size());
        assertEquals("Last name may not be null.", validate.iterator().next().getMessage());
    }

    @Test
    public void FirstName_Should_NotBeNull() {
        Teacher teacher = new Teacher(
                "last",
                null,
                "degree",
                5,
                "email",
                lecture);
        Set<ConstraintViolation<Teacher>> validate = validator.validate(teacher);
        assertEquals(1, validate.size());
        assertEquals("First name may not be null.", validate.iterator().next().getMessage());
    }

    @Test
    public void Degree_Should_NotBeNull() {
        Teacher teacher = new Teacher(
                "last",
                "first",
                null,
                5,
                "email",
                lecture);

        Set<ConstraintViolation<Teacher>> validate = validator.validate(teacher);

        assertEquals(1, validate.size());
        assertEquals("Academic degree may not be null.", validate.iterator().next().getMessage());
    }

    @Test
    public void FirstName_LastName_And_Degree_Should_ContainMin2Characters() {
        Teacher teacher = new Teacher(
                "l",
                "f",
                "a",
                5,
                "email",
                lecture);

        Set<ConstraintViolation<Teacher>> validate = validator.validate(teacher);

        assertEquals(3, validate.size());
        assertEquals(
                "Minimum 2 characters, maximum 25 characters.",
                validate.iterator().next().getMessage()
        );
    }

    @Test
    public void Teacher_Is_Valid() {
        Set<ConstraintViolation<Teacher>> validate = validator.validate(teacher);
        assertEquals(0, validate.size());
    }
}
