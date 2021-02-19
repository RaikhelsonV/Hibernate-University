package validatorWithAddMethodTest;

import entity.Schedule;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ScheduleTest {
    private static Validator validator;

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void Audience_Should_Be0OrLessThan500() {
        LocalDate date = LocalDate.parse("2020-12-12");
        LocalTime start = LocalTime.parse("10:00");
        LocalTime end = LocalTime.parse("11:00");

        Schedule schedule = new Schedule(date, 600, start, end);
        Set<ConstraintViolation<Schedule>> constraintViolations =
                validator.validate(schedule);

        assertEquals(1, constraintViolations.size());
        assertEquals("Minimum 0, maximum 500.",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void Schedule_Is_Valid() {
        LocalDate date = LocalDate.parse("2020-12-12");
        LocalTime start = LocalTime.parse("10:00");
        LocalTime end = LocalTime.parse("11:00");

        Schedule schedule = new Schedule(date, 500, start, end);
        Set<ConstraintViolation<Schedule>> constraintViolations =
                validator.validate(schedule);

        assertEquals(0, constraintViolations.size());
    }

}
