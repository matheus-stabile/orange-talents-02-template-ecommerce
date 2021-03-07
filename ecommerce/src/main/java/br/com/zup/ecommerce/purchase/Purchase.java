package br.com.zup.ecommerce.purchase;

import br.com.zup.ecommerce.products.Product;
import br.com.zup.ecommerce.purchase.paymentGateways.PaymentGateway;
import br.com.zup.ecommerce.purchase.paymentGateways.PaymentGatewayResponse;
import br.com.zup.ecommerce.purchase.transactions.Transaction;
import br.com.zup.ecommerce.users.User;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static io.jsonwebtoken.lang.Assert.isTrue;
import static io.jsonwebtoken.lang.Assert.state;

@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Valid
    @ManyToOne
    private Product product;

    @Positive
    private Integer quantity;

    @NotNull
    @Enumerated
    private PaymentGateway paymentGateway;

    @NotNull
    @Valid
    @ManyToOne
    private User customer;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.MERGE)
    private Set<Transaction> transactions = new HashSet<>();

    @Deprecated
    public Purchase() {
    }

    public Purchase(@NotNull @Valid Product product, @Positive Integer quantity, @NotNull @Valid User customer, @NotNull PaymentGateway paymentGateway) {
        this.product = product;
        this.quantity = quantity;
        this.customer = customer;
        this.paymentGateway = paymentGateway;
    }

    public String redirectionUrl(UriComponentsBuilder uriComponentsBuilder) {
        return this.paymentGateway.createResponseUrl(this, uriComponentsBuilder);
    }

    public Long getId() {
        return id;
    }

    public User getCustomer() {
        return customer;
    }

    public User getProductOwner() {
        return product.getOwner();
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public PaymentGateway getPaymentGateway() {
        return paymentGateway;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(@Valid PaymentGatewayResponse paymentGatewayResponse) {
        Transaction transaction = paymentGatewayResponse.toTransaction(this);

        state(!this.transactions.contains(transaction), "Esta transação já está cadastrada");
        state(completedTransactions().isEmpty(), "Esta compra já está concluída");

        this.transactions.add(transaction);
    }

    private Set<Transaction> completedTransactions() {
        Set<Transaction> completedTransactions = this.transactions.stream().filter(Transaction::concluded).collect(Collectors.toSet());

        isTrue(completedTransactions.size() <= 1, "mais de uma transação concluída com o mesmo id" + this.id);

        return completedTransactions;
    }

    public boolean processed() {
        return !completedTransactions().isEmpty();
    }
}
