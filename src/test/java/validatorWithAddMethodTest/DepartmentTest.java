package validatorWithAddMethodTest;

import entity.Department;
import entity.StudyGroup;
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
public class DepartmentTest {
    private static Validator validator;

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void Name_Should_NotBeNull() {
        Department department = new Department(null);
        Set<ConstraintViolation<Department>> constraintViolations =
                validator.validate(department);

        assertEquals(1, constraintViolations.size());
        assertEquals("Name of department may not be null.", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void Name_Should_ContainMin2Characters() {
        Department department = new Department("D");
        Set<ConstraintViolation<Department>> constraintViolations =
                validator.validate(department);

        assertEquals(1, constraintViolations.size());
        assertEquals(
                "Minimum 2 characters, maximum 25 characters.",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void Department_Is_Valid() {
        Department department = new Department("Dep");
        Set<ConstraintViolation<Department>> constraintViolations =
                validator.validate(department);

        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void When_GroupNull_Should_ThrowException() {
        Department department = new Department();
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            department.addGroup(null);
        });

        String expectedMessage = "Can't add null StudyGroup.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test(expected = IllegalStateException.class)
    public void When_GroupAlreadyAssigned_Should_ThrowException() {
        Department departmentMock = mock(Department.class);

        StudyGroup studyGroup = new StudyGroup();
        doThrow(IllegalStateException.class)
                .when(departmentMock)
                .addGroup(studyGroup);

        departmentMock.addGroup(studyGroup);
    }
}
