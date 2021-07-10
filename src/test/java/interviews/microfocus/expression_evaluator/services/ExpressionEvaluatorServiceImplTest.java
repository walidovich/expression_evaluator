package interviews.microfocus.expression_evaluator.services;

import interviews.microfocus.expression_evaluator.exceptions.InvalidExpressionRequestException;
import interviews.microfocus.expression_evaluator.models.ExpressionRequest;
import interviews.microfocus.expression_evaluator.models.ExpressionResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExpressionEvaluatorServiceImplTest {
    @Autowired
    private ExpressionEvaluatorService expressionEvaluatorService;

    @Test
    public void twoPlusThreeEqualsFiveTest() {
        String expression = "2+3";
        double expectedResult = 5;
        evaluateExpressionAndAssertResult(expression, expectedResult);
    }

    @Test
    public void twoPlusThreePlusFourEqualsNineTest() {
        String expression = "2+3+4";
        double expectedResult = 9;
        evaluateExpressionAndAssertResult(expression, expectedResult);
    }

    @Test
    public void oneMinusFourEqualsMinusThreeTest() {
        String expression = "1-4";
        double expectedResult = -3;
        evaluateExpressionAndAssertResult(expression, expectedResult);
    }

    @Test
    public void threeMultiplyFourEqualsTwelveTest() {
        String expression = "3*4";
        double expectedResult = 12;
        evaluateExpressionAndAssertResult(expression, expectedResult);
    }

    @Test
    public void threeMultiplyFourWithExtraSpaceEqualsTwelveTest() {
        String expression = " 3 * 4 ";
        double expectedResult = 12;
        evaluateExpressionAndAssertResult(expression, expectedResult);
    }

    @Test
    public void twoDividedByZeroThrowsInvalidRequestExceptionTest() {
        String expression = "2/0";
        ExpressionRequest expressionRequest = new ExpressionRequest();
        expressionRequest.setExpression(expression);
        try {
            expressionEvaluatorService.evaluate(expressionRequest);
        } catch (InvalidExpressionRequestException invalidExpressionRequestException) {
            Assertions.assertTrue(invalidExpressionRequestException.getErrorMessage().toLowerCase().contains("division by zero"));
        }
    }

    @Test
    public void FiveDivideByTwoEqualsTwoPointFiveTest() {
        String expression = "5/2";
        double expectedResult = 2.5;
        evaluateExpressionAndAssertResult(expression, expectedResult);
    }

    @Test
    public void twoPlusThreeMultiplyByFiveEqualsSeventeenTest() {
        String expression = "2+3*5";
        double expectedResult = 17;
        evaluateExpressionAndAssertResult(expression, expectedResult);
    }

    @Test
    public void twoMultiplyByThreePlusFiveEqualsElevenTest() {
        String expression = "2*3+5";
        double expectedResult = 11;
        evaluateExpressionAndAssertResult(expression, expectedResult);
    }

    @Test
    public void threeDividedByFourPlusOneEqualsOnePointSeventyFiveTest() {
        String expression = "3/4+1";
        double expectedResult = 1.75;
        evaluateExpressionAndAssertResult(expression, expectedResult);
    }

    @Test
    public void nestedThreeMultiplyByFourMinusSevenEqualsMinusNineTest() {
        String expression = "3*(4-7)";
        double expectedResult = -9;
        evaluateExpressionAndAssertResult(expression, expectedResult);
    }

    @Test
    public void expressionOperatorsPriorityTest() {
        String expression = "1*2+3/(4-2)";
        double expectedResult = 3.5;
        evaluateExpressionAndAssertResult(expression, expectedResult);
    }

    @Test
    public void nestedParenthesisTest() {
        String expression = "(2+3)*(4-5)";
        double expectedResult = -5;
        evaluateExpressionAndAssertResult(expression, expectedResult);
    }

    @Test
    public void doubleNestedParenthesisTest() {
        String expression = "(6-4)/(2-(2*3)+2)";
        double expectedResult = -1;
        evaluateExpressionAndAssertResult(expression, expectedResult);
    }

    @Test
    public void absOfMinusOneEqualsOneTest() {
        String expression = "abs(-1)";
        double expectedResult = 1;
        evaluateExpressionAndAssertResult(expression, expectedResult);
    }

    @Test
    public void MinusAbsoluteOfMinusOneWithExtraParenthesisEqualsMinusOneTest() {
        String expression = "(-abs(-1))";
        double expectedResult = -1;
        evaluateExpressionAndAssertResult(expression, expectedResult);
    }

    @Test
    public void absOfMinusOneMinusThreeEqualsFourTest() {
        String expression = "abs(-1-3)";
        double expectedResult = 4;
        evaluateExpressionAndAssertResult(expression, expectedResult);
    }

    @Test
    public void nestedAbsoluteTest() {
        String expression = "abs(-1*(3+4))";
        double expectedResult = 7;
        evaluateExpressionAndAssertResult(expression, expectedResult);
    }

    @Test
    public void doubleNestedAbsoluteWithUselessParenthesisWrapperTest() {
        String expression = "(abs((-1+3)*(3-(10-4))))";
        double expectedResult = 6;
        evaluateExpressionAndAssertResult(expression, expectedResult);
    }

    @Test
    public void sizeofWalidEqualsFiveTest() {
        String expression = "sizeof(walid)";
        double expectedResult = 5;
        evaluateExpressionAndAssertResult(expression, expectedResult);
    }

    @Test
    public void sizeofMinusOneEqualsTwoTest() {
        String expression = "sizeof(-1)";
        double expectedResult = 2;
        evaluateExpressionAndAssertResult(expression, expectedResult);
    }

    @Test
    public void sizeofAbsoluteMinusThreeEqualsSevenTest() {
        String expression = "sizeof(abs(-3))";
        double expectedResult = "abs(-3)".length();
        evaluateExpressionAndAssertResult(expression, expectedResult);
    }

    @Test
    public void MinusSizeofWalidEqualsMinusFiveTest() {
        String expression = "-sizeof(walid)";
        double expectedResult = -"walid".length();
        evaluateExpressionAndAssertResult(expression, expectedResult);
    }

    @Test
    public void emptyLeftExpressionMultiplyByFourEqualsZeroTest() {
        String expression = "*4";
        double expectedResult = 0;
        evaluateExpressionAndAssertResult(expression, expectedResult);
    }

    @Test
    public void emptyLeftExpressionPlusTwoEqualsTwoTest() {
        String expression = "+2";
        double expectedResult = 2;
        evaluateExpressionAndAssertResult(expression, expectedResult);
    }

    @Test
    public void MinusTwoEqualsMinusTwoTest() {
        String expression = "-2";
        double expectedResult = -2;
        evaluateExpressionAndAssertResult(expression, expectedResult);
    }

    @Test
    public void unequalAmountOfParenthesisAtBeginningThrowsInvalidExpressionRequestExceptionTest() {
        String expression = "(2+1";
        evaluateExpressionAndAssertInvalidRequestExceptionError(expression);
    }

    @Test
    public void unequalAmountOfParenthesisAtEndThrowsInvalidExpressionRequestExceptionTest() {
        String expression = "2-1)";
        evaluateExpressionAndAssertInvalidRequestExceptionError(expression);
    }

    @Test
    public void emptyExpressionThrowsInvalidExpressionRequestExceptionTest() {
        String expression = "";
        evaluateExpressionAndAssertInvalidRequestExceptionError(expression);
    }

    @Test
    public void emptyExpressionWithTwoParenthesisThrowsInvalidExpressionRequestExceptionTest() {
        String expression = "()";
        evaluateExpressionAndAssertInvalidRequestExceptionError(expression);
    }

    private void evaluateExpressionAndAssertInvalidRequestExceptionError(String expression) {
        ExpressionRequest expressionRequest = new ExpressionRequest();
        expressionRequest.setExpression(expression);
        try {
            expressionEvaluatorService.evaluate(expressionRequest);
        } catch (InvalidExpressionRequestException invalidExpressionRequestException) {
            Assertions.assertTrue(true, "expression " + expression + " didn't threw an invalid request exception");
        }
    }

    private void evaluateExpressionAndAssertResult(String expression, double expectedResult) {
        ExpressionRequest expressionRequest = new ExpressionRequest();
        expressionRequest.setExpression(expression);
        try {
            ExpressionResponse expressionResponse = expressionEvaluatorService.evaluate(expressionRequest);
            Assertions.assertEquals(expectedResult, expressionResponse.getResult(),
                    "expression " + expression + " returned different result than " + expectedResult);
        } catch (InvalidExpressionRequestException invalidExpressionRequestException) {
            Assertions.assertTrue(false, "expression " + expression + " threw an invalid request exception");
        }
    }
}
