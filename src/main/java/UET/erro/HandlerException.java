package UET.erro;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class HandlerException extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ErrorException.class)
    public void error(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_ACCEPTABLE.value());
    }
}
