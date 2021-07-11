# expression_evaluator

## REST API

### URL         : http://localhost:8080/evaluate
### Method      : POST

### Examples of Request/Response
* request1    : {
                    "expression": "abs(-2)-sizeof(123)"
                }
* response1   : {
                    "result": -1.0
                }
* request2    : {
                    "expression": "5/(3-(2+1))"
                }
* response2   : {
                    "error": "Division by zero!"
                }
* request3    : {
                    "expression": "(2+1)/5)"
                }
* response3   : {
                    "error": "error evaluating expression : (2+1)/5)"
                }

