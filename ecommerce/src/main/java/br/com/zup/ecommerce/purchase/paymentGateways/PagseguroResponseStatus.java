package br.com.zup.ecommerce.purchase.paymentGateways;

import br.com.zup.ecommerce.purchase.transactions.TransactionStatus;

public enum PagseguroResponseStatus {
    SUCCESS,
    ERROR;

    public TransactionStatus verify() {
        if (this.equals(SUCCESS)) {
            return TransactionStatus.SUCCESS;
        }

        return TransactionStatus.ERROR;
    }


}
