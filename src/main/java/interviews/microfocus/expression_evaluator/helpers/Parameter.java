package interviews.microfocus.expression_evaluator.helpers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Parameter<T> {
    private T type;
    private String value;
}
