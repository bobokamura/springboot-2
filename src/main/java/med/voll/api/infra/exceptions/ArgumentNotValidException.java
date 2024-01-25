package med.voll.api.infra.exceptions;

public class ArgumentNotValidException extends RuntimeException {
    public ArgumentNotValidException(String message) {
        super(message);
    }
}
