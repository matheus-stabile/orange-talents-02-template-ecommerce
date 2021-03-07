package br.com.zup.ecommerce.purchase.events;

import br.com.zup.ecommerce.externalSystems.RankingRequest;
import br.com.zup.ecommerce.purchase.Purchase;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static io.jsonwebtoken.lang.Assert.isTrue;

@Service
public class Ranking implements PurchaseEventSuccess {
    @Override
    public void processPurchase(Purchase purchase) {
        isTrue(purchase.processed(), "a compra não pode ser concluída");

        RankingRequest rankingRequest = new RankingRequest(purchase.getId(), purchase.getProductOwner().getId());
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJadXAgRWR1IC0gRGVzYWZpbyBFY29tbWVyY2UiLCJzdWIiOiJtYXRoZXVzLnN0YWJpbGVAenVwLmNvbS5iciIsImlhdCI6MTYxNTE0NzkwNSwiZXhwIjoxNjE1MjM0MzA1fQ.PC7FNpXjZ14N8K30QZJHQ0dCoKZf4yg6xWVjycZhPE0");

        HttpEntity<RankingRequest> requestHttpEntity = new HttpEntity<>(rankingRequest, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.postForEntity("http://localhost:8080/ranking", requestHttpEntity, String.class);
    }
}
