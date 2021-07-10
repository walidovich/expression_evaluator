package interviews.microfocus.expression_evaluator.controllers;

import interviews.microfocus.expression_evaluator.exceptions.InvalidExpressionRequestException;
import interviews.microfocus.expression_evaluator.models.ExpressionRequest;
import interviews.microfocus.expression_evaluator.models.ExpressionResponse;
import interviews.microfocus.expression_evaluator.services.ExpressionEvaluatorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ExpressionEvaluatorRestController {

    private static Logger LOG = LoggerFactory.getLogger(ExpressionEvaluatorRestController.class);
    private ExpressionEvaluatorServiceImpl expressionServiceImpl;

    @Autowired
    public ExpressionEvaluatorRestController(ExpressionEvaluatorServiceImpl expressionServiceImpl) {
        this.expressionServiceImpl = expressionServiceImpl;
    }

    @RequestMapping("/")
    public String home() {
        return "Keep calm, you're Home";
    }

    @PostMapping("/evaluate")
    public ResponseEntity<ExpressionResponse> evaluate(@RequestBody @Valid ExpressionRequest expressionRequest,
                                                       BindingResult bindingResult) throws InvalidExpressionRequestException {
        if (bindingResult.hasFieldErrors("expression")) {
            // Check if the request is a valid ExpressionRequest
            LOG.error("Error when validating expressionRequest {}", expressionRequest);
            throw InvalidExpressionRequestException.builder()
                    .errorMessage(bindingResult.getFieldError("expression").getDefaultMessage())
                    .build();
        } else {
            LOG.info("POST call for controller with expressionRequest {}", expressionRequest);
            ExpressionResponse expressionResponse = expressionServiceImpl.evaluate(expressionRequest);
            ResponseEntity<ExpressionResponse> expressionResponseBody = ResponseEntity.ok().body(expressionResponse);
            LOG.info("Controller will return  response is {} for expressionRequest {}"
                    , expressionResponseBody, expressionRequest);
            return expressionResponseBody;
        }
    }
}
