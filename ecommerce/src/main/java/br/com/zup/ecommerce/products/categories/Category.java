package br.com.zup.ecommerce.products.categories;

import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static io.jsonwebtoken.lang.Assert.*;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @ManyToOne
    private Category parentCategory;

    public Category(@NotBlank String name, @Valid Category parentCategory) {

        notNull(name, "o nome da categoria não pode ser nulo");
        isTrue(StringUtils.hasLength(name), "o nome da categoria não pode estar vazio");
        notNull(parentCategory, "a categoria mãe não pode ser nula");

        this.name = name;
        this.parentCategory = parentCategory;
    }

    public Category(@NotBlank String name) {
        notNull(name, "o nome da categoria não pode ser nulo");
        isTrue(StringUtils.hasLength(name), "o nome da categoria não pode estar vazio");

        this.name = name;
    }

    @Deprecated
    public Category() {
    }

    public String getName() {
        return name;
    }

    public Category getParentCategory() {
        return parentCategory;
    }
}
