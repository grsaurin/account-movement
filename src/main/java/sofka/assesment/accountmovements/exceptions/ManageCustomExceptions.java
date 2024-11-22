package sofka.assesment.accountmovements.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ManageCustomExceptions {

    public ResponseEntity<String> handleMovementsErrors(UnableProcessMovementException e) {
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }
}
