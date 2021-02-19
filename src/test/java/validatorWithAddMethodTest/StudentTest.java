package validatorWithAddMethodTest;

import entity.Student;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class StudentTest {
    private static Validator validator;

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void LastName_Should_NotBeNull() {
        Student student = new Student(null, "firstName");
        Set<ConstraintViolation<Student>> validate = validator.validate(student);

        assertEquals(1, validate.size());
        assertEquals("Last name may not be null.", validate.iterator().next().getMessage());
    }

    @Test
    public void FirstName_Should_NotBeNull() {
        Student student = new Student("lastName", null);
        Set<ConstraintViolation<Student>> validate = validator.validate(student);

        assertEquals(1, validate.size());
        assertEquals("First name may not be null.", validate.iterator().next().getMessage());
    }

    @Test
    public void FirstName_And_LastName_Should_ContainMin2Characters() {
        Student student = new Student("l", "f");
        Set<ConstraintViolation<Student>> validate = validator.validate(student);

        assertEquals(2, validate.size());
        assertEquals(
                "Minimum 2 characters, maximum 25 characters.",
                validate.iterator().next().getMessage()
        );
    }

    @Test
    public void Student_Is_Valid() {
        Student student = new Student("lastN", "firstN");
        Set<ConstraintViolation<Student>> validate = validator.validate(student);

        assertEquals(0, validate.size());
    }
}
