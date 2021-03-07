package br.com.zup.ecommerce.purchase.events;

import br.com.zup.ecommerce.purchase.Purchase;
import br.com.zup.ecommerce.purchase.PurchaseResponse;
import br.com.zup.ecommerce.purchase.SuccessPurchaseEmailMessage;
import br.com.zup.ecommerce.shared.services.FakeMailerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Email implements PurchaseEventSuccess {

    @Autowired
    private FakeMailerService fakeMailerService;

    @Override
    public void processPurchase(Purchase purchase) {

        PurchaseResponse purchaseResponse = new PurchaseResponse(purchase);

        fakeMailerService.send(new SuccessPurchaseEmailMessage(purchaseResponse));
    }
}
