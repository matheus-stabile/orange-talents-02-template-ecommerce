package br.com.zup.ecommerce.purchase;

import br.com.zup.ecommerce.purchase.events.PurchaseEvents;
import br.com.zup.ecommerce.purchase.paymentGateways.PagseguroResponse;
import br.com.zup.ecommerce.purchase.paymentGateways.PaymentGatewayResponse;
import br.com.zup.ecommerce.purchase.paymentGateways.PaypalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class PurchaseResponseController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PurchaseEvents purchaseEvents;

    @PostMapping("/retorno-pagseguro/{id}")
    @Transactional
    public ResponseEntity<?> pagseguroProcess(@PathVariable("id") Long id, @RequestBody @Valid PagseguroResponse pagseguroResponse) {
        return process(id, pagseguroResponse);
    }

    @PostMapping("/retorno-paypal/{id}")
    @Transactional
    public ResponseEntity<?> paypalProcess(@PathVariable("id") Long id, @RequestBody @Valid PaypalResponse paypalResponse) {
        return process(id, paypalResponse);
    }

    private ResponseEntity<?> process(Long id, PaymentGatewayResponse paymentGatewayResponse) {
        Purchase purchase = entityManager.find(Purchase.class, id);
        purchase.addTransaction(paymentGatewayResponse);
        entityManager.merge(purchase);
        purchaseEvents.process(purchase);

        return ResponseEntity.ok(new PurchaseResponse(purchase));
    }
}
