package br.com.zup.ecommerce.products.questions;

import br.com.zup.ecommerce.products.Product;
import br.com.zup.ecommerce.shared.services.EmailMessage;
import br.com.zup.ecommerce.users.User;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.format.DateTimeFormatter;

public class QuestionEmailMessage implements EmailMessage {

    private final String from = "no-reply@ecommerce.com";
    private String to;
    private String subject;
    private String body;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public QuestionEmailMessage(@NotNull @Valid Question question) {
        this.to = question.getProduct().getOwner().getUsername();
        this.subject = "Você tem uma nova pergunta sobre o produto " + question.getProduct().getName();
        this.body = new StringBuilder()
                .append("Em ")
                .append(question.getCreationDate().format(formatter))
                .append(", ")
                .append(question.getCustomer().getUsername())
                .append(" perguntou: ")
                .append(question.getTitle())
                .append("\n")
                .append("<a href=\"")
                .append("http://localhost:8080/produtos/")
                .append(question.getProduct().getId())
                .append("/pergunta/")
                .append(question.getId())
                .append("\"")
                .append(">Clique aqui</a> para responder!")
                .toString();
    }

    public String build() {
        return "De: " + from
                + "\nPara: " + to
                + "\nAssunto: " + subject
                + "\nMensagem: " + body;
    }
}

