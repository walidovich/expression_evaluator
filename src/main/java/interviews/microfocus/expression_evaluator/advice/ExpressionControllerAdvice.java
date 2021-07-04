package interviews.microfocus.expression_evaluator.advice;

import interviews.microfocus.expression_evaluator.exceptions.InvalidExpressionRequest;
import interviews.microfocus.expression_evaluator.models.ExpressionErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExpressionControllerAdvice {
    @ExceptionHandler(InvalidExpressionRequest.class)
    public ResponseEntity<ExpressionErrorResponse> invalidExpression(InvalidExpressionRequest exc) {
        return ResponseEntity.badRequest().body(
                ExpressionErrorResponse.builder()
                        .error(exc.getErrorMessage())
                        .build()
        );
    }
}
