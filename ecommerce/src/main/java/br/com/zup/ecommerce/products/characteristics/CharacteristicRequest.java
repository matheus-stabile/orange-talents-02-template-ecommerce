package br.com.zup.ecommerce.products.characteristics;

import br.com.zup.ecommerce.products.Product;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CharacteristicRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    public CharacteristicRequest(@NotBlank String name, @NotBlank String description) {
        this.name = name;
        this.description = description;
    }

    public Characteristic toModel(@NotNull @Valid Product product) {
        return new Characteristic(name, description, product);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
