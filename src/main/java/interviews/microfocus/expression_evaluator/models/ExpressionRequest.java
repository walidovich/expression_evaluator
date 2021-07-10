package interviews.microfocus.expression_evaluator.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class ExpressionRequest {
    @JsonProperty(value = "expression")
    @NotEmpty(message = "expression attribute is either missing or empty!")
    private String expression;
}
