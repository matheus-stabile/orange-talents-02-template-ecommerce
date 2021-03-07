package br.com.zup.ecommerce.purchase;

import br.com.zup.ecommerce.shared.services.EmailMessage;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SuccessPurchaseEmailMessage implements EmailMessage {

    private final String from = "no-reply@ecommerce.com";
    private String to;
    private String subject;
    private String body;

    public SuccessPurchaseEmailMessage(@NotNull @Valid PurchaseResponse purchaseResponse) {
        this.to = purchaseResponse.getCustomer();
        this.subject = "Seu pagamento foi aprovado!";
        this.body = new StringBuilder()
                .append("produto: ")
                .append(purchaseResponse.getProduct())
                .append(" quantidade: ")
                .append(purchaseResponse.getQuantity())
                .append(" forma de pagamento: ")
                .append(purchaseResponse.getPaymentGateway())
                .append(" status do pagamento: ")
                .append(purchaseResponse.getTransactionStatus())
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
