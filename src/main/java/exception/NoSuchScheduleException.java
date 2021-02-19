package exception;

public class NoSuchScheduleException extends RuntimeException {
    public NoSuchScheduleException(String msg) {
        super(msg);
    }
}
