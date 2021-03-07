package br.com.zup.ecommerce.shared.validations;

import br.com.zup.ecommerce.products.ProductRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

public class NotSameCharacteristicNameValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return ProductRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors())
            return;

        ProductRequest request = (ProductRequest) target;
        Set<String> checkName = request.findDuplicatedCharacteristics();

        if (!checkName.isEmpty()) {
            errors.rejectValue("characteristics", "400", "característica duplicada: " + checkName);
        }

    }
}
