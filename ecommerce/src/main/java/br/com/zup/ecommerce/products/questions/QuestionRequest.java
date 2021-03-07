package br.com.zup.ecommerce.products.questions;

import br.com.zup.ecommerce.products.Product;
import br.com.zup.ecommerce.users.User;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class QuestionRequest {

    @NotBlank
    private String title;

    public QuestionRequest(@NotBlank String title) {
        this.title = title;
    }

    @Deprecated
    public QuestionRequest() {
    }

    public Question toModel(@NotNull @Valid Product product, @NotNull @Valid User customer) {
        return new Question(title, product, customer);
    }

    public String getTitle() {
        return title;
    }
}
