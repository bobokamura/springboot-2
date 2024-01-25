package med.voll.api.infra.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        StandardError err = new StandardError();
        HttpStatus status = HttpStatus.NOT_FOUND;

        err.setTimestamp(LocalDateTime.now());
        err.setStatus(status.value());
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> integrityException(MethodArgumentNotValidException e, HttpServletRequest request) {
        StandardError err = new StandardError();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        var erros = e.getFieldErrors();

        List<DadosErroValidacao> listError = new ArrayList<>();
        erros.forEach(error -> {
            DadosErroValidacao dadosErroValidacao = new DadosErroValidacao(error);
            listError.add(dadosErroValidacao);
        });

        err.setTimestamp(LocalDateTime.now());
        err.setStatus(status.value());
        err.setError(listError);
        err.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }
}
