package br.com.zup.ecommerce.purchase.paymentGateways;

import br.com.zup.ecommerce.purchase.Purchase;
import br.com.zup.ecommerce.purchase.transactions.Transaction;
import br.com.zup.ecommerce.purchase.transactions.TransactionStatus;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class PaypalResponse implements PaymentGatewayResponse{

    @Min(0)
    @Max(1)
    Integer status;

    @NotBlank
    private String transactionId;

    public PaypalResponse(@Min(0) @Max(1) Integer status, @NotBlank String transactionId) {
        this.status = status;
        this.transactionId = transactionId;
    }

    @Override
    public Transaction toTransaction(Purchase purchase) {
        TransactionStatus checkStatus = this.status == 0 ? TransactionStatus.ERROR : TransactionStatus.SUCCESS;

        return new Transaction(checkStatus, transactionId, purchase);
    }
}
