package br.com.zup.ecommerce.purchase.events;

import br.com.zup.ecommerce.purchase.ErrorPurchaseEmailMessage;
import br.com.zup.ecommerce.purchase.Purchase;
import br.com.zup.ecommerce.purchase.PurchaseResponse;
import br.com.zup.ecommerce.shared.services.FakeMailerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PurchaseEvents {

    @Autowired
    private FakeMailerService fakeMailerService;

    @Autowired
    private Set<PurchaseEventSuccess> purchaseEventsSuccess;

    public void process(Purchase purchase) {
        if (purchase.processed()) {
            purchaseEventsSuccess.forEach(event -> event.processPurchase(purchase));
        } else {
            fakeMailerService.send(new ErrorPurchaseEmailMessage(new PurchaseResponse(purchase)));
        }
    }
}
