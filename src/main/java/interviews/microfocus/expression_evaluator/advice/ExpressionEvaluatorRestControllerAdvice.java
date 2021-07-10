package interviews.microfocus.expression_evaluator.advice;

import interviews.microfocus.expression_evaluator.exceptions.InvalidExpressionRequestException;
import interviews.microfocus.expression_evaluator.models.ExpressionErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExpressionEvaluatorRestControllerAdvice {
    @ExceptionHandler(InvalidExpressionRequestException.class)
    public ResponseEntity<ExpressionErrorResponse> invalidExpression(InvalidExpressionRequestException exc) {
        // We build  ResponseEntity with proper http error and error body
        return ResponseEntity
                .badRequest()
                .body(ExpressionErrorResponse.builder()
                        .error(exc.getErrorMessage())
                        .build()
                );
    }
}
