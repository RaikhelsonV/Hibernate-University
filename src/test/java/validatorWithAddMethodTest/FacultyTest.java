package validatorWithAddMethodTest;

import entity.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class FacultyTest {
    private static Validator validator;

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void Name_Should_NotBeNull() {
        Faculty faculty = new Faculty(null);
        Set<ConstraintViolation<Faculty>> constraintViolations =
                validator.validate(faculty);

        assertEquals(1, constraintViolations.size());
        assertEquals("Name of faculty may not be null.", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void Name_Should_ContainMin2Characters() {
        Faculty faculty = new Faculty("M");
        Set<ConstraintViolation<Faculty>> constraintViolations =
                validator.validate(faculty);

        assertEquals(1, constraintViolations.size());
        assertEquals(
                "Minimum 2 characters, maximum 25 characters.",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void Faculty_Is_Valid() {
        Faculty faculty = new Faculty("Faculty1");
        Set<ConstraintViolation<Faculty>> constraintViolations =
                validator.validate(faculty);

        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void When_DepartmentNull_Should_ThrowException() {
        Faculty faculty = new Faculty();
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            faculty.addDepartment(null);
        });

        String expectedMessage = "Can't add null Department.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test(expected = IllegalStateException.class)
    public void When_DepartmentAlreadyAssigned_Should_ThrowException() {
        Faculty facultyMock = mock(Faculty.class);

        Department department = new Department();
        doThrow(IllegalStateException.class)
                .when(facultyMock)
                .addDepartment(department);

        facultyMock.addDepartment(department);
    }

    @Test
    public void When_TeacherNull_Should_ThrowException() {
        Faculty faculty = new Faculty();
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            faculty.addTeacher(null);
        });

        String expectedMessage = "Can't add null Teacher.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test(expected = IllegalStateException.class)
    public void When_TeacherAlreadyAssigned_Should_ThrowException() {
        Faculty facultyMock = mock(Faculty.class);

        Teacher teacher = new Teacher();
        doThrow(IllegalStateException.class)
                .when(facultyMock)
                .addTeacher(teacher);

        facultyMock.addTeacher(teacher);
    }
}
