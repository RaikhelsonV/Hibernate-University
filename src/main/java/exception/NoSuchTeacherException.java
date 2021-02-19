package exception;

public class NoSuchTeacherException extends RuntimeException {
    public NoSuchTeacherException(String msg) {
        super(msg);
    }
}
