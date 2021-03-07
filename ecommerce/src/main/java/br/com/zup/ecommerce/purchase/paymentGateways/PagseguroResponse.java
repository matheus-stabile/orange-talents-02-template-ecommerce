package br.com.zup.ecommerce.purchase.paymentGateways;

import br.com.zup.ecommerce.purchase.Purchase;
import br.com.zup.ecommerce.purchase.transactions.Transaction;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;

public class PagseguroResponse implements PaymentGatewayResponse{

    @NotBlank
    private String transactionId;

    @NonNull
    private PagseguroResponseStatus status;

    public PagseguroResponse(@NotBlank String transactionId, @NonNull PagseguroResponseStatus status) {
        this.transactionId = transactionId;
        this.status = status;
    }

    @Override
    public Transaction toTransaction(Purchase purchase) {
        return new Transaction(status.verify(), transactionId, purchase);
    }
}
