package interviews.microfocus.expression_evaluator.services;

import interviews.microfocus.expression_evaluator.exceptions.InvalidExpressionRequest;
import interviews.microfocus.expression_evaluator.models.ExpressionRequest;
import interviews.microfocus.expression_evaluator.models.ExpressionResponse;

public interface ExpressionService {
    ExpressionResponse evaluate(ExpressionRequest expressionRequest) throws InvalidExpressionRequest;
}
