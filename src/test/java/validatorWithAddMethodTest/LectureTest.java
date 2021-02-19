package validatorWithAddMethodTest;

import entity.Lecture;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class LectureTest {
    private static Validator validator;

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void Name_Should_NotBeNull() {
        Lecture lecture = new Lecture(null);
        Set<ConstraintViolation<Lecture>> constraintViolations =
                validator.validate(lecture);

        assertEquals(1, constraintViolations.size());
        assertEquals("Name of lecture may not be null.", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void Name_Should_ContainMin2Characters() {
        Lecture lecture = new Lecture("L");
        Set<ConstraintViolation<Lecture>> constraintViolations =
                validator.validate(lecture);

        assertEquals(1, constraintViolations.size());
        assertEquals(
                "Minimum 2 characters, maximum 25 characters.",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void Lecture_Is_Valid() {
        Lecture lecture = new Lecture("Lecture");
        Set<ConstraintViolation<Lecture>> constraintViolations =
                validator.validate(lecture);

        assertEquals(0, constraintViolations.size());
    }

}
