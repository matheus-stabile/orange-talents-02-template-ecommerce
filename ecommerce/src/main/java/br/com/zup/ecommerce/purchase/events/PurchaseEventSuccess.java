package br.com.zup.ecommerce.purchase.events;

import br.com.zup.ecommerce.purchase.Purchase;

public interface PurchaseEventSuccess {
    void processPurchase(Purchase purchase);
}
