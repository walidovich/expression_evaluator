package interviews.microfocus.expression_evaluator.controllers;

import interviews.microfocus.expression_evaluator.exceptions.InvalidExpressionRequest;
import interviews.microfocus.expression_evaluator.models.ExpressionRequest;
import interviews.microfocus.expression_evaluator.models.ExpressionResponse;
import interviews.microfocus.expression_evaluator.services.ExpressionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExpressionController {

    private ExpressionServiceImpl expressionServiceImpl;

    @Autowired
    public ExpressionController(ExpressionServiceImpl expressionServiceImpl) {
        this.expressionServiceImpl = expressionServiceImpl;
    }

    @RequestMapping("/")
    public String home() {
        return "Keep calm, you're Home";
    }

    @PostMapping("/evaluate")
    public ResponseEntity<ExpressionResponse> evaluate(@RequestBody ExpressionRequest expressionRequest) throws InvalidExpressionRequest {
        // Check if the request is a valid ExpressionRequest
        ExpressionResponse expressionResponse = expressionServiceImpl.evaluate(expressionRequest);
        return ResponseEntity.ok().body(expressionResponse);
    }
}
