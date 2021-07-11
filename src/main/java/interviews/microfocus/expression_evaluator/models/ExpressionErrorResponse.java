package interviews.microfocus.expression_evaluator.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Builder
@Value
@AllArgsConstructor
public class ExpressionErrorResponse {
    @JsonProperty("error")
    private String error;
}
