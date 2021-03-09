package br.com.zup.ecommerce.products.categories;

import br.com.zup.ecommerce.shared.validations.ExistsIfInformed;
import br.com.zup.ecommerce.shared.validations.UniqueValue;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;

public class CategoryRequest {

    @NotBlank
    @UniqueValue(domainClass = Category.class, fieldName = "name", message = "a categoria informada já está cadastrada")
    private String name;

    @ExistsIfInformed(domainClass = Category.class, fieldName = "id", message = "a categoria mãe informada não existe")
    private Long parentCategoryId;

    public CategoryRequest(@NotBlank String name, Long parentCategoryId) {
        this.name = name;
        this.parentCategoryId = parentCategoryId;
    }

    @Deprecated
    public CategoryRequest() {
    }

    public Category toModel(EntityManager entityManager) {

        if (parentCategoryId != null) {
            Category parentCategory = entityManager.find(Category.class, parentCategoryId);
            return new Category(name, parentCategory);
        }

        return new Category(name);
    }

    public String getName() {
        return name;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }
}
