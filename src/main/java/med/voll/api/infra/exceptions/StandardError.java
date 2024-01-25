package med.voll.api.infra.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class StandardError {
    private LocalDateTime timestamp;
    private Integer status;
    private String message;
    private List<DadosErroValidacao> error;
    private String path;
}
