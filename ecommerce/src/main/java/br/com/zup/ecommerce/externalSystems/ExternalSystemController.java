package br.com.zup.ecommerce.externalSystems;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ExternalSystemController {

    @PostMapping("/notas-fiscais")
    public void createNF(@RequestBody @Valid NotaFiscalRequest notaFiscalRequest) throws InterruptedException {
        System.out.println("Nota fiscal sendo gerada...");
        Thread.sleep(150);
        System.out.println(notaFiscalRequest.toString());
    }

    @PostMapping("/ranking")
    public void createNF(@RequestBody @Valid RankingRequest rankingRequest) throws InterruptedException {
        System.out.println("Ranking está sendo criado...");
        Thread.sleep(150);
        System.out.println(rankingRequest.toString());
    }
}
