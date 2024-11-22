package sofka.assesment.accountmovements.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class UnableProcessMovementException extends Exception {

    HttpStatus status;

    public UnableProcessMovementException(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }
}
