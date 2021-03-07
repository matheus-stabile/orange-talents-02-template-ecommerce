package br.com.zup.ecommerce.products.opinions;

import br.com.zup.ecommerce.products.Product;
import br.com.zup.ecommerce.users.User;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OpinionRequest {

    @Min(value = 1, message = "deve ser entre 1 e 5")
    @Max(value = 5, message = "deve ser entre 1 e 5")
    private Integer rate;

    @NotBlank
    private String title;

    @NotBlank
    @Length(max = 500)
    private String description;

    public OpinionRequest(@Min(1) @Max(5) Integer rate, @NotBlank String title, @NotBlank @Length(max = 500) String description) {
        this.rate = rate;
        this.title = title;
        this.description = description;
    }

    public Opinion toModel(@NotNull @Valid Product product, @NotNull @Valid User customer) {
        return new Opinion(rate, title, description, product, customer);
    }
}
