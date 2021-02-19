package exception;

public class NoSuchDepartmentException extends RuntimeException {
    public NoSuchDepartmentException(String msg) {
        super(msg);
    }
}
