package br.com.zup.ecommerce.products;

import br.com.zup.ecommerce.products.categories.Category;
import br.com.zup.ecommerce.products.characteristics.CharacteristicRequest;
import br.com.zup.ecommerce.shared.validations.Exists;
import br.com.zup.ecommerce.users.User;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductRequest {

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @Positive
    private Integer quantity;

    @NotBlank
    @Length(max = 1000)
    private String description;

    @NotNull
    @Exists(domainClass = Category.class, fieldName = "id", message = "a categoria informada não existe")
    private Long categoryId;

    @Size(min = 3, message = "o produto deve ter no mínimo 3 características")
    @Valid
    private List<CharacteristicRequest> characteristics = new ArrayList<>();

    public ProductRequest(@NotBlank String name, @NotNull @Positive BigDecimal price,
                          @NotNull @Positive Integer quantity, @NotBlank @Length(max = 1000) String description,
                          @NotNull Long categoryId, List<CharacteristicRequest> characteristics) {

        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.categoryId = categoryId;
        this.characteristics.addAll(characteristics);
    }

    public Product toModel(EntityManager entityManager, User owner) {
        Category category = entityManager.find(Category.class, categoryId);
        return new Product(name, price, quantity, description, category, characteristics, owner);
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public List<CharacteristicRequest> getCharacteristics() {
        return characteristics;
    }

    public Set<String> findDuplicatedCharacteristics() {
        HashSet<String> equalsNames = new HashSet<>();
        HashSet<String> results = new HashSet<>();

        for (CharacteristicRequest characteristicRequest : characteristics) {

            String name = characteristicRequest.getName();

            if (!equalsNames.add(name)) {
                results.add(name);
            }
        }

        return results;
    }
}
