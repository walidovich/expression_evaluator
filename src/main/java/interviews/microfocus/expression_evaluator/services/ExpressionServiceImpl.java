package interviews.microfocus.expression_evaluator.services;

import interviews.microfocus.expression_evaluator.exceptions.InvalidExpressionRequestException;
import interviews.microfocus.expression_evaluator.models.ExpressionErrorResponse;
import interviews.microfocus.expression_evaluator.models.ExpressionRequest;
import interviews.microfocus.expression_evaluator.models.ExpressionResponse;
import interviews.microfocus.expression_evaluator.services.helpers.Operator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpressionServiceImpl implements ExpressionService {

    private static Logger LOG = LoggerFactory.getLogger(ExpressionServiceImpl.class);

    /**
     * @param expressionRequest, a string containing a mathematical expression
     * @return expressionResponse if expression evaluates fine
     * @throws InvalidExpressionRequestException if expression encounter a breaking rule
     */
    @Override
    public ExpressionResponse evaluate(ExpressionRequest expressionRequest) throws InvalidExpressionRequestException {
        LOG.info("Start of evaluate method with expressionRequest {}", expressionRequest);
        try {
            // ExpressionService tries to evaluate the expression and returns result if everything is OK
            double result = evaluateExpression(expressionRequest.getExpression().trim());
            // We return ExpressionResponse object with result attribute set to the result value
            ExpressionResponse expressionResponse = ExpressionResponse
                    .builder()
                    .result(result)
                    .build();
            LOG.info("End of evaluate method, expressionResponse {}", expressionResponse);
            return expressionResponse;
        } catch (InvalidExpressionRequestException e) {
            LOG.error("Method evaluate threw InvalidExpressionRequestException exception {} for expressionRequest {}"
                    , e, expressionRequest);
            // If evaluateExpression methods throws InvalidExpressionRequest, we rethrow it again to the controller
            throw e;
        } catch (Exception e) {
            LOG.error("Method evaluate threw general exception {} for expressionRequest {}", e, expressionRequest);
            // If something went wrong, we catch the exception, set a general error message and throw the exception
            ExpressionErrorResponse expressionErrorResponse = ExpressionErrorResponse
                    .builder()
                    .error("error evaluating expression : " + expressionRequest.getExpression())
                    .build();
            throw new InvalidExpressionRequestException(expressionErrorResponse.getError());
        }
    }

    private double evaluateExpression(String expression) throws InvalidExpressionRequestException {

        LOG.info("Start of recursive method evaluateExpression with expression {}", expression);

        // General way of thinking is that an expression has the recursive form above
        // firstExpression and secondExpression also have the same recursive form until we hit plain numbers
        // The logic then is to determine firstExpression, secondExpression and the operator

        // expression = (firstExpression) operator (secondExpression)


        // We will store operators and operatorsPositions to use them later when we try to divide the current expression
        List<Operator> operators = new ArrayList<>();
        Map<Operator, Integer> operatorsPositions = new HashMap<>();

        // The parenthesisCount determines whether we are inside an expression or outside
        // example 3+(2*3): at the operator + parenthesisCount is 0, then operator PLUS is added
        // and expression will split az fistExpression=3 and secondExpression=(2*3)
        // However, if expression is (3+2)*3, then the operator registered is * because at + parenthesisCount=1
        // thus, the expression splits at * and becomes, fistExpression=(3+2) and secondExpression=3
        int parenthesisCount = 0;

        for (int i = 0; i < expression.length(); i++) {
            // We only register operators if we have even count of left and right parenthesis
            if (parenthesisCount == 0) {
                if (expression.charAt(i) == '+') {
                    operatorsPositions.put(Operator.PLUS, i);
                    operators.add(Operator.PLUS);
                }
                if (expression.charAt(i) == '-') {
                    operatorsPositions.put(Operator.MINUS, i);
                    operators.add(Operator.MINUS);
                }
                if (expression.charAt(i) == '*') {
                    operatorsPositions.put(Operator.MULTIPLY, i);
                    operators.add(Operator.MULTIPLY);
                }
                if (expression.charAt(i) == '/') {
                    operatorsPositions.put(Operator.DIVIDE, i);
                    operators.add(Operator.DIVIDE);
                }
                if (expression.substring(i).toLowerCase().startsWith("abs")) {
                    operatorsPositions.put(Operator.ABS, i);
                    operators.add(Operator.ABS);
                }
                if (expression.substring(i).toLowerCase().startsWith("sizeof")) {
                    operatorsPositions.put(Operator.SIZEOF, i);
                    operators.add(Operator.SIZEOF);
                }
            }
            // We count parenthesis to see if we are inside an expression
            if (expression.charAt(i) == '(') {
                parenthesisCount++;
            }
            if (expression.charAt(i) == ')') {
                parenthesisCount--;
            }
        }

        LOG.info("Operator to be evaluated is {}", operators.get(0));

        // We check for PLUS and MINUS before MULTIPLY AND DIVIDE because of operators priorities.
        // example: 2+3*4 should become 2+(3*4), firstExpression is 2 and secondExpression is 3*4
        // however, 2*3+4 should become (2*3)+4, firstExpression is 2*3 and secondExpression is 4
        // Both expressions are split first by operators with lowest priority.

        if (operators.contains(Operator.PLUS)) {
            // expression = firstExpression + secondExpression will be split
            String firstExpression = expression.substring(0, operatorsPositions.get(Operator.PLUS));
            String secondExpression = expression.substring(operatorsPositions.get(Operator.PLUS) + 1);
            LOG.info("expression {} split into firstExpression {} plus secondExpression {}",
                    expression, firstExpression, secondExpression);
            double firstValue = firstExpression.length() != 0 ? evaluateExpression(firstExpression) : 0;
            double secondValue = evaluateExpression(secondExpression);
            LOG.info("expression {} evaluated to firstExpression {} plus secondExpression {}",
                    expression, firstValue, secondValue);
            return firstValue + secondValue;
        } else if (operators.contains(Operator.MINUS)) {
            // expression = firstExpression - secondExpression will be split
            String firstExpression = expression.substring(0, operatorsPositions.get(Operator.MINUS));
            String secondExpression = expression.substring(operatorsPositions.get(Operator.MINUS) + 1);
            LOG.info("expression {} split into firstExpression {} minus secondExpression {}",
                    expression, firstExpression, secondExpression);
            double firstValue = firstExpression.length() != 0 ? evaluateExpression(firstExpression) : 0;
            double secondValue = evaluateExpression(secondExpression);
            LOG.info("expression {} evaluated to firstExpression {} minus secondExpression {}",
                    expression, firstValue, secondValue);
            return firstValue - secondValue;
        } else if (operators.contains(Operator.MULTIPLY)) {
            // expression = firstExpression * secondExpression will be split
            String firstExpression = expression.substring(0, operatorsPositions.get(Operator.MULTIPLY));
            String secondExpression = expression.substring(operatorsPositions.get(Operator.MULTIPLY) + 1);
            LOG.info("expression {} split into firstExpression {} multiply by secondExpression {}",
                    expression, firstExpression, secondExpression);
            double firstValue = firstExpression.length() != 0 ? evaluateExpression(firstExpression) : 0;
            double secondValue = evaluateExpression(secondExpression);
            LOG.info("expression {} evaluated to firstExpression {} multiply by secondExpression {}",
                    expression, firstValue, secondValue);
            return firstValue * secondValue;
        } else if (operators.contains(Operator.DIVIDE)) {
            // expression = firstExpression / secondExpression will be split
            String firstExpression = expression.substring(0, operatorsPositions.get(Operator.DIVIDE));
            String secondExpression = expression.substring(operatorsPositions.get(Operator.DIVIDE) + 1);
            LOG.info("expression {} split into firstExpression {} divide by secondExpression {}",
                    expression, firstExpression, secondExpression);
            double firstValue = firstExpression.length() != 0 ? evaluateExpression(firstExpression) : 0;
            double secondValue = evaluateExpression(secondExpression);
            LOG.info("expression {} evaluated to firstExpression {} divided by secondExpression {}",
                    expression, firstValue, secondValue);
            if (secondValue == 0) {
                throw InvalidExpressionRequestException.builder().errorMessage("Division by zero!").build();
            }
            return firstValue / secondValue;
        } else if (operators.contains(Operator.ABS)) {
            // abs(expression) should recursively call evaluateExpression(expression)
            String innerExpression = expression.substring(operatorsPositions.get(Operator.ABS) + 4, expression.length() - 1);
            LOG.info("expression {} reduced to absolute of innerExpression {}", expression, innerExpression);
            double value = Math.abs(evaluateExpression(innerExpression));
            return value;
        } else if (operators.contains(Operator.SIZEOF)) {
            // sizeof(expression): expression WILL NOT be evaluated since the operator consider the input as a string
            String innerString = expression.substring(operatorsPositions.get(Operator.SIZEOF) + 7, expression.length() - 1);
            LOG.info("expression {} will return the size of \"{}\"", expression, innerString);
            double value = innerString.length();
            return value;
        } else if (expression.charAt(0) == '(' && expression.charAt(expression.length() - 1) == ')') {
            // If no operator is registered, it may be the case the expression starts with outer parenthesis
            // So we trim them and evaluate the inner expression
            String parenthesisTrimmedExpression = expression.substring(1, expression.length() - 1);
            LOG.info("expression {} will remove the outer parenthesis and evaluate {}",
                    expression, parenthesisTrimmedExpression);
            return evaluateExpression(parenthesisTrimmedExpression);
        } else {
            // When expression only contains a numerical value, the value is directly parsed and returned
            LOG.info("expression is reduced to its most inner value and will return {}", expression);
            return Double.parseDouble(expression);
        }
    }
}