package interviews.microfocus.expression_evaluator.services;

import interviews.microfocus.expression_evaluator.exceptions.InvalidExpressionRequestException;
import interviews.microfocus.expression_evaluator.models.ExpressionRequest;
import interviews.microfocus.expression_evaluator.models.ExpressionResponse;

public interface ExpressionEvaluatorService {
    ExpressionResponse evaluate(ExpressionRequest expressionRequest) throws InvalidExpressionRequestException;
}
