package interviews.microfocus.expression_evaluator.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidExpressionRequest extends Exception {

    private String errorMessage;

    public InvalidExpressionRequest(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
