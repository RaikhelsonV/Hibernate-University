package validatorWithAddMethodTest;

import entity.Faculty;
import entity.Student;
import entity.University;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;


@RunWith(MockitoJUnitRunner.class)
public class UniversityTest {
    private static Validator validator;

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void When_FacultyNull_Should_ThrowException() {
        University university = new University();
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            university.addFaculty(null);
        });

        String expectedMessage = "Can't add null Faculty.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test(expected = IllegalStateException.class)
    public void When_FacultyAlreadyAssigned_Should_ThrowException() {
        University universityMock = mock(University.class);
        Faculty faculty = new Faculty();
        doThrow(IllegalStateException.class)
                .when(universityMock)
                .addFaculty(faculty);

        universityMock.addFaculty(faculty);
    }


    @Test
    public void  When_StudentNull_Should_ThrowException() {
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
        University universityMock = mock(University.class);
        Student student = new Student();
        doThrow(IllegalStateException.class)
                .when(universityMock)
                .addStudent(student);

        universityMock.addStudent(student);
    }
}
