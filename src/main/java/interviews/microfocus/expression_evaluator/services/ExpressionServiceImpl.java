package interviews.microfocus.expression_evaluator.services;

import interviews.microfocus.expression_evaluator.models.ExpressionRequest;
import interviews.microfocus.expression_evaluator.models.ExpressionResponse;
import interviews.microfocus.expression_evaluator.models.ResultType;
import org.springframework.stereotype.Service;

@Service
public class ExpressionServiceImpl implements ExpressionService {
    @Override
    public ExpressionResponse evaluate(ExpressionRequest expressionRequest) {
        ExpressionResponse expressionResponse = new ExpressionResponse();
        expressionResponse.setResultType(ResultType.INT);
        expressionResponse.setResultValue("goodResult");
        return expressionResponse;
    }
}
