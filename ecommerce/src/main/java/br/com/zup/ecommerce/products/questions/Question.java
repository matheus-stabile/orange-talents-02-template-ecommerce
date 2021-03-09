package br.com.zup.ecommerce.products.questions;

import br.com.zup.ecommerce.products.Product;
import br.com.zup.ecommerce.users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.Objects;

import static io.jsonwebtoken.lang.Assert.isTrue;
import static io.jsonwebtoken.lang.Assert.notNull;

@Entity
public class Question implements Comparable<Question> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PastOrPresent
    private LocalDateTime creationDate = LocalDateTime.now();

    @NotBlank
    private String title;

    @JsonIgnore
    @NotNull
    @Valid
    @ManyToOne
    private Product product;

    @JsonIgnore
    @NotNull
    @Valid
    @ManyToOne
    private User customer;

    @Deprecated
    public Question() {
    }

    public Question(@NotBlank String title, @NotNull @Valid Product product, @NotNull @Valid User customer) {

        isTrue(StringUtils.hasLength(title), "o título da pergunta é obrigatório");
        notNull(product, "o produto referente a pergunta é obrigatório");
        notNull(customer, "o cliente referente a pergunta é obrigatório");

        this.title = title;
        this.product = product;
        this.customer = customer;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public User getCustomer() {
        return customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id) && Objects.equals(title, question.title) && Objects.equals(product, question.product) && Objects.equals(customer, question.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, product, customer);
    }

    @Override
    public int compareTo(Question o) {
        return this.title.compareTo(o.title);
    }
}
