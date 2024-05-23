package exceptions;

public class InvalidCommuneIdException extends RuntimeException {
    public InvalidCommuneIdException(String message) {
        super(message);
    }
}