package br.com.zup.ecommerce.products.characteristics;

import br.com.zup.ecommerce.products.Product;
import io.jsonwebtoken.lang.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

import static io.jsonwebtoken.lang.Assert.*;

@Entity
public class Characteristic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    @Valid
    @ManyToOne
    private Product product;

    @Deprecated
    public Characteristic() {
    }

    public Characteristic(@NotBlank String name, @NotBlank String description, @NotNull @Valid Product product) {

        notNull(name, "o nome da característica não pode ser nulo");
        isTrue(StringUtils.hasLength(name), "o nome da característica não pode estar em branco");
        notNull(description, "a descrição da característica não pode ser nula");
        isTrue(StringUtils.hasLength(description), "a descrição da característica não pode estar em branco");
        notNull(product, "o produto referente a característica não pode ser nulo");

        this.name = name;
        this.description = description;
        this.product = product;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Characteristic that = (Characteristic) o;
        return Objects.equals(name, that.name) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, product);
    }
}
