package interviews.microfocus.expression_evaluator.services;

import interviews.microfocus.expression_evaluator.exceptions.InvalidExpressionRequest;
import interviews.microfocus.expression_evaluator.models.ExpressionErrorResponse;
import interviews.microfocus.expression_evaluator.models.ExpressionRequest;
import interviews.microfocus.expression_evaluator.models.ExpressionResponse;
import org.springframework.stereotype.Service;

@Service
public class ExpressionServiceImpl implements ExpressionService {
    @Override
    public ExpressionResponse evaluate(ExpressionRequest expressionRequest) throws InvalidExpressionRequest {
        ExpressionResponse expressionResponse;
        ExpressionErrorResponse expressionErrorResponse;
        try {
            expressionResponse = new ExpressionResponse();
            expressionResponse.setResult(Double.valueOf(expressionRequest.getExpression()));
        } catch (Exception e) {
            expressionErrorResponse = new ExpressionErrorResponse();
            expressionErrorResponse.setError("error evaluating expression : " + expressionRequest.getExpression());
            throw new InvalidExpressionRequest(expressionErrorResponse.getError());
        }
        return expressionResponse;
    }
}
