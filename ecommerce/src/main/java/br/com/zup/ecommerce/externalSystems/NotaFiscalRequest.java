package br.com.zup.ecommerce.externalSystems;

import javax.validation.constraints.NotNull;

public class NotaFiscalRequest {

    @NotNull
    private Long purchaseId;

    @NotNull
    private Long customerId;

    public NotaFiscalRequest(@NotNull Long purchaseId, @NotNull Long customerId) {
        this.purchaseId = purchaseId;
        this.customerId = customerId;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return "NotaFiscalRequest{" +
                "purchaseId=" + purchaseId +
                ", customerId=" + customerId +
                '}';
    }
}


