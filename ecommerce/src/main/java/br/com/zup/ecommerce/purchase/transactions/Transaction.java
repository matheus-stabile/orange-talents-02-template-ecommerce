package br.com.zup.ecommerce.purchase.transactions;

import br.com.zup.ecommerce.purchase.Purchase;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    TransactionStatus status;

    @NotBlank
    String PaymentGatewayTransactionId;

    private LocalDateTime creationDate = LocalDateTime.now();

    @NotNull
    @Valid
    @ManyToOne
    private Purchase purchase;

    @Deprecated
    public Transaction() {
    }

    public Transaction(TransactionStatus status, @NotBlank String paymentGatewayTransactionId, @NotNull @Valid Purchase purchase) {
        this.status = status;
        PaymentGatewayTransactionId = paymentGatewayTransactionId;
        this.purchase = purchase;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public boolean concluded() {
        return this.status.equals(TransactionStatus.SUCCESS);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(PaymentGatewayTransactionId, that.PaymentGatewayTransactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(PaymentGatewayTransactionId);
    }
}
