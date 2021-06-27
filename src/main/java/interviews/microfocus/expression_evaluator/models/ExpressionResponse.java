package interviews.microfocus.expression_evaluator.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpressionResponse {
    @JsonIgnore
    private ResultType resultType;
    @JsonProperty("result")
    private String resultValue;
}
