package exception;

public class NoSuchStudentException extends RuntimeException {
    public NoSuchStudentException(String msg) {
        super(msg);
    }
}
