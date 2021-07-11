package interviews.microfocus.expression_evaluator.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import interviews.microfocus.expression_evaluator.exceptions.InvalidExpressionRequestException;
import interviews.microfocus.expression_evaluator.models.ExpressionErrorResponse;
import interviews.microfocus.expression_evaluator.models.ExpressionRequest;
import interviews.microfocus.expression_evaluator.models.ExpressionResponse;
import interviews.microfocus.expression_evaluator.services.ExpressionEvaluatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ExpressionEvaluatorRestControllerTest {

    @MockBean
    private ExpressionEvaluatorService expressionEvaluatorService;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void goodExpressionReturnsGoodResultTest() throws Exception {
        when(expressionEvaluatorService.evaluate(any())).thenReturn(ExpressionResponse.builder().result(12d).build());

        ExpressionRequest expressionRequest = new ExpressionRequest();
        expressionRequest.setExpression("2*6");
        String expressionRequestJsonBody = objectMapper.writeValueAsString(expressionRequest);

        MvcResult mvcResult = mockMvc.perform(post("/evaluate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expressionRequestJsonBody))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        ExpressionResponse expressionResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                ExpressionResponse.class);

        assertEquals("ExpressionEvaluatorRestController didn't return Http 200 response entity",
                200, mvcResult.getResponse().getStatus());
        assertEquals("Response entity doesn't contains correct expression response result", java.util.Optional.of(12d).get(),
                expressionResponse.getResult());
    }

    @Test
    public void badExpressionThrowsInvalidExpressionRequestExceptionTest() throws Exception {
        when(expressionEvaluatorService.evaluate(any()))
                .thenThrow(InvalidExpressionRequestException.builder().errorMessage("Error message").build());

        ExpressionRequest expressionRequest = new ExpressionRequest();
        expressionRequest.setExpression("bad expression");
        String expressionRequestJsonBody = objectMapper.writeValueAsString(expressionRequest);

        MvcResult mvcResult = mockMvc.perform(post("/evaluate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expressionRequestJsonBody))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        ExpressionErrorResponse expressionErrorResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                ExpressionErrorResponse.class);

        assertEquals("ExpressionEvaluatorRestController didn't return Http 400 response entity",
                400, mvcResult.getResponse().getStatus());
        assertEquals("Response entity doesn't contains 'Error message'",
                "Error message", expressionErrorResponse.getError());
    }

    @Test
    public void badJsonRequestThrowsErrorExpressionAttributeMissingOrEmptyTest() throws Exception {

        ExpressionRequest expressionRequest = new ExpressionRequest();
        expressionRequest.setExpression("some expression");
        String expressionRequestJsonBody = objectMapper.writeValueAsString(expressionRequest);
        String badExpressionRequestJsonBody = expressionRequestJsonBody.replace("expression", "operation");

        MvcResult mvcResult = mockMvc.perform(post("/evaluate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(badExpressionRequestJsonBody))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        ExpressionErrorResponse expressionErrorResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                ExpressionErrorResponse.class);

        assertEquals("ExpressionEvaluatorRestController didn't return Http 400 response entity",
                400, mvcResult.getResponse().getStatus());
        assertEquals("Response entity doesn't contains correct expression response result",
                "expression attribute is either missing or empty!", expressionErrorResponse.getError());
    }
}
