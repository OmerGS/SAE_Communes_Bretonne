package exceptions;

public class InvalidCommuneNameException extends RuntimeException {
    public InvalidCommuneNameException(String message) {
        super(message);
    }
}