package validatorWithAddMethodTest;

import entity.Administration;
import entity.University;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class AdministrationTest {
    private static Validator validator;

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void LastName_Should_NotBeNull() {
        University university = new University();
        Administration administration = new Administration(
                null,
                "firstName",
                "academicDegree",
                "speciality",
                university);
        Set<ConstraintViolation<Administration>> validate = validator.validate(administration);

        assertEquals(1, validate.size());
        assertEquals("Last name may not be null.", validate.iterator().next().getMessage());
    }

    @Test
    public void FirstName_Should_NotBeNull() {
        University university = new University();
        Administration administration = new Administration(
                "lastName",
                null,
                "academicDegree",
                "speciality",
                university);
        Set<ConstraintViolation<Administration>> validate = validator.validate(administration);

        assertEquals(1, validate.size());
        assertEquals("First name may not be null.", validate.iterator().next().getMessage());
    }

    @Test
    public void Speciality_Should_NotBeNull() {
        University university = new University();
        Administration administration = new Administration(
                "lastName",
                "firstName",
                "academicDegree",
                null,
                university);
        Set<ConstraintViolation<Administration>> validate = validator.validate(administration);

        assertEquals(1, validate.size());
        assertEquals("Speciality may not be null.", validate.iterator().next().getMessage());
    }

    @Test
    public void FirstName_LastName_And_Specialty_Should_ContainMin2Characters() {
        University university = new University();
        Administration administration = new Administration(
                "l",
                "f",
                "academicDegree",
                "s",
                university);
        Set<ConstraintViolation<Administration>> validate = validator.validate(administration);

        assertEquals(3, validate.size());
        assertEquals(
                "Minimum 2 characters, maximum 25 characters.",
                validate.iterator().next().getMessage()
        );
    }

    @Test
    public void Administration_IsValid() {
        University university = new University();
        Administration administration = new Administration(
                "lastName",
                "firstName",
                "academicDegree",
                "speciality",
                university);
        Set<ConstraintViolation<Administration>> constraintViolations =
                validator.validate(administration);

        assertEquals(0, constraintViolations.size());
    }
}
