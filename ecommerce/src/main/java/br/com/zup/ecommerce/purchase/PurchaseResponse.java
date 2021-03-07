package br.com.zup.ecommerce.purchase;

import br.com.zup.ecommerce.purchase.paymentGateways.PaymentGateway;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class PurchaseResponse {

    private Long id;
    private String product;
    private Integer quantity;
    private PaymentGateway paymentGateway;
    private String customer;
    private String transactionStatus;


    public PurchaseResponse(@NotNull @Valid Purchase purchase) {

        this.id = purchase.getId();
        this.product = purchase.getProduct().getName();
        this.quantity = purchase.getQuantity();
        this.paymentGateway = purchase.getPaymentGateway();
        this.customer = purchase.getCustomer().getUsername();
        purchase.getTransactions().forEach(transaction -> transactionStatus = transaction.getStatus().toString());
    }

    public Long getId() {
        return id;
    }

    public String getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public PaymentGateway getPaymentGateway() {
        return paymentGateway;
    }

    public String getCustomer() {
        return customer;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }
}
