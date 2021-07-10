package interviews.microfocus.expression_evaluator.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class InvalidExpressionRequestException extends Exception {
    private String errorMessage;
}
