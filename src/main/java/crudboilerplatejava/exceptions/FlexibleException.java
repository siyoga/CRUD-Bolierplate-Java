package crudboilerplatejava.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
@Setter
public class FlexibleException extends RuntimeException {
    private final String message;
    private final HttpStatus statusCode;
}
