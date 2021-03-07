package br.com.zup.ecommerce.externalSystems;

import javax.validation.constraints.NotNull;

public class RankingRequest {

    @NotNull
    private Long purchaseId;

    @NotNull
    private Long productOwnerId;

    public RankingRequest(@NotNull Long purchaseId, @NotNull Long productOwnerId) {
        this.purchaseId = purchaseId;
        this.productOwnerId = productOwnerId;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public Long getProductOwnerId() {
        return productOwnerId;
    }

    @Override
    public String toString() {
        return "RankingRequest{" +
                "purchaseId=" + purchaseId +
                ", productOwnerId=" + productOwnerId +
                '}';
    }
}
