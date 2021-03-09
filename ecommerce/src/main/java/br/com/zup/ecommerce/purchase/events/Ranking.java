package br.com.zup.ecommerce.purchase.events;

import br.com.zup.ecommerce.externalSystems.RankingRequest;
import br.com.zup.ecommerce.purchase.Purchase;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static io.jsonwebtoken.lang.Assert.isTrue;

@Service
public class Ranking implements PurchaseEventSuccess {
    @Override
    public void processPurchase(Purchase purchase) {
        isTrue(purchase.processed(), "a compra não pode ser concluída");

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> request = Map.of("purchaseId", purchase.getId(), "productOwnerId", purchase.getProductOwner().getId());

        restTemplate.postForEntity("http://localhost:8080/ranking", request, String.class);
    }
}
