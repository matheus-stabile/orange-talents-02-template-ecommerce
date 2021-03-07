package br.com.zup.ecommerce.purchase;

import br.com.zup.ecommerce.products.Product;
import br.com.zup.ecommerce.shared.services.EmailMessage;
import br.com.zup.ecommerce.users.User;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NewPurchaseEmailMessage implements EmailMessage {

    private final String from = "no-reply@ecommerce.com";
    private String to;
    private String subject;
    private String body;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public NewPurchaseEmailMessage(@NotNull @Valid Purchase purchase) {
        this.to = purchase.getProduct().getOwner().getUsername();
        this.subject = "Você tem um novo pedido do produto " + purchase.getProduct().getName();
        this.body = new StringBuilder()
                .append("Em ")
                .append(LocalDateTime.now().format(formatter))
                .append(", ")
                .append(purchase.getCustomer().getUsername())
                .append(" realizou um pedido de ")
                .append(purchase.getQuantity())
                .append(" unidade(s) do produto: ")
                .append(purchase.getProduct().getName())
                .toString();
    }

    @Override
    public String build() {
        return "De: " + from
                + "\nPara: " + to
                + "\nAssunto: " + subject
                + "\nMensagem: " + body;
    }
}
