package br.com.zup.ecommerce.shared.validations;

import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ExistsIfInformedValidator implements ConstraintValidator<ExistsIfInformed, Object> {

    Class<?> domainClass;
    String domainAttribute;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(ExistsIfInformed constraintAnnotation) {
        domainClass = constraintAnnotation.domainClass();
        domainAttribute = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null)
            return true;

        Query query = entityManager.createQuery("SELECT 1 FROM " + domainClass.getName() + " WHERE " + domainAttribute + " = :value")
                .setParameter("value", value);

        List<?> list = query.getResultList();
        Assert.isTrue(list.size() <= 1, "existe mais de um " + domainAttribute + " cadastrado em " + domainClass.getName());

        return !list.isEmpty();
    }
}
