package br.com.zup.ecommerce.products.opinions;

import br.com.zup.ecommerce.products.Product;
import br.com.zup.ecommerce.users.User;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

import static io.jsonwebtoken.lang.Assert.isTrue;
import static io.jsonwebtoken.lang.Assert.notNull;

@Entity
public class Opinion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1)
    @Max(5)
    private Integer rate;

    @NotBlank
    private String title;

    @NotBlank
    @Length(max = 500)
    private String description;

    @NotNull
    @Valid
    @ManyToOne
    private Product product;

    @NotNull
    @Valid
    @ManyToOne
    private User customer;

    @Deprecated
    public Opinion() {
    }

    public Opinion(@Min(1) @Max(5) Integer rate,
                   @NotBlank String title,
                   @NotBlank @Length(max = 500) String description,
                   @NotNull @Valid Product product,
                   @NotNull @Valid User customer) {

        notNull(rate, "a classificação é obrigatória");
        isTrue(rate >= 1 && rate <= 5, "a classificação deve ser entre 1 e 5");
        isTrue(StringUtils.hasLength(title), "o título da opinião é obrigatório");
        isTrue(StringUtils.hasLength(description), "a descrição da opinião é obrigratória");
        isTrue(description.length() <= 500, "a descrição da opinião não pode ultrapassar 500 caracteres");
        notNull(product, "o produto referente a opinião é obrigatório");
        notNull(customer, "o cliente referente a opinião é obrigatório");

        this.rate = rate;
        this.title = title;
        this.description = description;
        this.product = product;
        this.customer = customer;
    }

    public Integer getRate() {
        return rate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Opinion opinion = (Opinion) o;
        return Objects.equals(title, opinion.title) && Objects.equals(description, opinion.description) && Objects.equals(product, opinion.product) && Objects.equals(customer, opinion.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, product, customer);
    }
}


