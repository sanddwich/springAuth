package spring.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import spring.auth.exception.dao.ExceptionInfo;

@Data
@Builder
@RestControllerAdvice
public class RestApiGlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ExceptionInfo> exceptionHandler(Exception exception) {
        return new ResponseEntity<>(
                ExceptionInfo.builder()
                .message(exception.getMessage())
                .stackTraceElements(exception.getStackTrace())
                .build(),
                HttpStatus.BAD_REQUEST
        );
    }
}
