package validatorWithAddMethodTest;

import entity.Student;
import entity.StudyGroup;
import entity.University;
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
public class StudyGroupTest {
    private static Validator validator;

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void Name_Should_NotBeNull() {
        StudyGroup studyGroup = new StudyGroup(null, 0);
        Set<ConstraintViolation<StudyGroup>> validate = validator.validate(studyGroup);

        assertEquals(1, validate.size());
        assertEquals("Name of group may not be null.", validate.iterator().next().getMessage());
    }

    @Test
    public void Amount_Should_Be0OrLessThan25() {
        StudyGroup studyGroup = new StudyGroup("group", 26);
        Set<ConstraintViolation<StudyGroup>> validate = validator.validate(studyGroup);

        assertEquals(1, validate.size());
        assertEquals("Minimum 0, maximum 25.", validate.iterator().next().getMessage());
    }

    @Test
    public void Name_Should_ContainMin2Characters() {
        StudyGroup studyGroup = new StudyGroup("s", 0);
        Set<ConstraintViolation<StudyGroup>> validate = validator.validate(studyGroup);

        assertEquals(1, validate.size());
        assertEquals(
                "Minimum 2 characters, maximum 25 characters.",
                validate.iterator().next().getMessage()
        );
    }

    @Test
    public void Group_Is_Valid() {
        StudyGroup studyGroup = new StudyGroup("group", 0);
        Set<ConstraintViolation<StudyGroup>> validate = validator.validate(studyGroup);

        assertEquals(0, validate.size());
    }

    @Test
    public void When_StudentNull_Should_ThrowException() {
        University university = new University();
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            university.addStudent(null);
        });

        String expectedMessage = "Can't add null Student.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test(expected = IllegalStateException.class)
    public void When_StudentAlreadyAssigned_Should_ThrowException() {
        StudyGroup studyGroupMock = mock(StudyGroup.class);
        Student student = new Student();
        doThrow(IllegalStateException.class)
                .when(studyGroupMock)
                .addStudent(student);

        studyGroupMock.addStudent(student);
    }
}
