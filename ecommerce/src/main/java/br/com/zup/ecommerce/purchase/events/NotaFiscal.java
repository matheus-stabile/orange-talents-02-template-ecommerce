package br.com.zup.ecommerce.purchase.events;

import br.com.zup.ecommerce.externalSystems.NotaFiscalRequest;
import br.com.zup.ecommerce.purchase.Purchase;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static io.jsonwebtoken.lang.Assert.isTrue;

@Service
public class NotaFiscal implements PurchaseEventSuccess {

    @Override
    public void processPurchase(Purchase purchase) {
        isTrue(purchase.processed(), "a compra não pode ser concluída");

        NotaFiscalRequest notaFiscalRequest = new NotaFiscalRequest(purchase.getProduct().getId(), purchase.getCustomer().getId());
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJadXAgRWR1IC0gRGVzYWZpbyBFY29tbWVyY2UiLCJzdWIiOiJtYXRoZXVzLnN0YWJpbGVAenVwLmNvbS5iciIsImlhdCI6MTYxNTE0NzkwNSwiZXhwIjoxNjE1MjM0MzA1fQ.PC7FNpXjZ14N8K30QZJHQ0dCoKZf4yg6xWVjycZhPE0");

        HttpEntity<NotaFiscalRequest> requestHttpEntity = new HttpEntity<>(notaFiscalRequest, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.postForEntity("http://localhost:8080/notas-fiscais", requestHttpEntity, String.class);
    }
}
