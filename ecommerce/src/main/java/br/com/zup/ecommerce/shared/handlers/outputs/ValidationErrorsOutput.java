package br.com.zup.ecommerce.shared.handlers.outputs;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorsOutput {

    private final List<String> globalErrorMessages = new ArrayList<>();
    private final List<FieldErrorOutput> fieldErrors = new ArrayList<>();

    public void addError(String message) {
        globalErrorMessages.add(message);
    }

    public void addFieldError(String field, String message) {
        FieldErrorOutput fieldError = new FieldErrorOutput(field, message);
        fieldErrors.add(fieldError);
    }

    public List<String> getGlobalErrorMessages() {
        return globalErrorMessages;
    }

    public List<FieldErrorOutput> getErrors() {
        return fieldErrors;
    }
}
