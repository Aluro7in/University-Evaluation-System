package university.backend.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ApiError error(HttpStatus status,String message,HttpServletRequest req){
        return new ApiError(Instant.now(),status.value(),status.getReasonPhrase(),message,req.getRequestURI());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError notFound(ResourceNotFoundException ex,HttpServletRequest req){
        return error(HttpStatus.NOT_FOUND,ex.getMessage(),req);
    }

    @ExceptionHandler({DuplicateResourceException.class,BusinessRuleException.class,InvalidGradeException.class,IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError badRequest(RuntimeException ex,HttpServletRequest req){
        return error(HttpStatus.BAD_REQUEST,ex.getMessage(),req);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError validation(MethodArgumentNotValidException ex,HttpServletRequest req){
        String message=ex.getBindingResult().getFieldErrors().stream()
                .map(e->e.getField()+": "+e.getDefaultMessage()).collect(Collectors.joining("; "));
        return error(HttpStatus.BAD_REQUEST,message,req);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError general(Exception ex,HttpServletRequest req){
        return error(HttpStatus.INTERNAL_SERVER_ERROR,"Unexpected server error",req);
    }
}