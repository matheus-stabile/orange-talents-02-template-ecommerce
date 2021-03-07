package br.com.zup.ecommerce.purchase;

import br.com.zup.ecommerce.products.Product;
import br.com.zup.ecommerce.purchase.paymentGateways.PaymentGateway;
import br.com.zup.ecommerce.shared.security.AuthenticatedUser;
import br.com.zup.ecommerce.shared.services.FakeMailerService;
import br.com.zup.ecommerce.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/compras")
public class PurchaseRequestController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    FakeMailerService fakeMailerService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> createPurchase(@RequestBody @Valid PurchaseRequest purchaseRequest, UriComponentsBuilder uriComponentsBuilder,
                                            @AuthenticationPrincipal AuthenticatedUser authenticatedUser) throws BindException {

        Product product = entityManager.find(Product.class, purchaseRequest.getProductId());
        Integer quantity = purchaseRequest.getQuantity();

        if (product.manageStock(quantity)) {
            User customer = authenticatedUser.get();
            PaymentGateway paymentGateway = purchaseRequest.getPaymentGateway();
            Purchase purchase = new Purchase(product, quantity, customer, paymentGateway);

            entityManager.persist(purchase);

            fakeMailerService.send(new NewPurchaseEmailMessage(purchase));

            return ResponseEntity.ok().build();
        }

        BindException outOfStockError = new BindException(purchaseRequest, "PurchaseRequest");
        outOfStockError.reject(null, "o produto não tem a quantidade informada no pedido em estoque");
        throw outOfStockError;
    }
}
