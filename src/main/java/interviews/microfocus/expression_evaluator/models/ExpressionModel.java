package interviews.microfocus.expression_evaluator.models;

import interviews.microfocus.expression_evaluator.helpers.Operation;
import interviews.microfocus.expression_evaluator.helpers.Parameter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ExpressionModel {
    private Operation operation;
    private Parameter parameter1;
    private Parameter parameter2;
}
