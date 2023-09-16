package spring.auth.exception.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionInfo {
    private String message;
    private StackTraceElement[] stackTraceElements;
}
