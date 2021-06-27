package interviews.microfocus.expression_evaluator.helpers;

import interviews.microfocus.expression_evaluator.exceptions.InvalidExpressionRequest;
import interviews.microfocus.expression_evaluator.models.ExpressionResponse;
import interviews.microfocus.expression_evaluator.models.ResultType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExpressionControllerAdvice {
    @ExceptionHandler(InvalidExpressionRequest.class)
    public ResponseEntity<ExpressionResponse> invalidExpression() {
        return ResponseEntity.badRequest().body(
                ExpressionResponse.builder()
                        .resultType(ResultType.STR)
                        .resultValue("error")
                        .build()
        );
    }
}
