package interviews.microfocus.expression_evaluator.models;

import interviews.microfocus.expression_evaluator.helpers.Operation;
import interviews.microfocus.expression_evaluator.helpers.Parameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpressionRequest {
    private Operation operation;
    private Parameter parameter1;
    private Parameter parameter2;
}
