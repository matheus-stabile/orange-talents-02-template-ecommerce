package br.com.zup.ecommerce.products.views;

import br.com.zup.ecommerce.products.characteristics.Characteristic;

public class CharacteristicDetails {

    private String name;
    private String description;

    public CharacteristicDetails(Characteristic characteristic) {

        this.name = characteristic.getName();
        this.description = characteristic.getDescription();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
