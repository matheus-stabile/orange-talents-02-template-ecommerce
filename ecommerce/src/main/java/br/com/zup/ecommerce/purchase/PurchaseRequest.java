package br.com.zup.ecommerce.purchase;

import br.com.zup.ecommerce.purchase.paymentGateways.PaymentGateway;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class PurchaseRequest {

    @NotNull
    private Long productId;

    @Positive
    private Integer quantity;

    @NotNull
    private PaymentGateway paymentGateway;

    public PurchaseRequest(@NotNull Long productId, @Positive Integer quantity, PaymentGateway paymentGateway) {
        this.productId = productId;
        this.quantity = quantity;
        this.paymentGateway = paymentGateway;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public PaymentGateway getPaymentGateway() {
        return paymentGateway;
    }
}
