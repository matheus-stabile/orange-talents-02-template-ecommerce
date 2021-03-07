package br.com.zup.ecommerce.purchase.paymentGateways;

import br.com.zup.ecommerce.purchase.Purchase;
import br.com.zup.ecommerce.purchase.transactions.Transaction;

public interface PaymentGatewayResponse {

    Transaction toTransaction(Purchase purchase);
}
