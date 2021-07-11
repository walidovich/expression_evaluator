package interviews.microfocus.expression_evaluator.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Builder
@Value
public class ExpressionResponse {
    @JsonProperty("result")
    private Double result;
}

