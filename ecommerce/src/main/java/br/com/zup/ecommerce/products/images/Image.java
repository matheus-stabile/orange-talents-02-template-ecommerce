package br.com.zup.ecommerce.products.images;

import br.com.zup.ecommerce.products.Product;
import io.jsonwebtoken.lang.Assert;
import org.hibernate.validator.constraints.URL;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

import static io.jsonwebtoken.lang.Assert.*;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Valid
    @ManyToOne
    private Product product;

    @NotBlank
    @URL
    private String link;

    @Deprecated
    public Image() {
    }

    public Image(@NotNull @Valid Product product, @NotBlank @URL String link) {

        notNull(product, "o produto relacionado a imagem não pode ser nulo");
        isTrue(StringUtils.hasLength(link), "o link não pode ser nulo ou vazio");

        this.product = product;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image that = (Image) o;
        return Objects.equals(product, that.product) && Objects.equals(link, that.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, link);
    }
}
