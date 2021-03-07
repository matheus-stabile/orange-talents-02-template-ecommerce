package br.com.zup.ecommerce.products.questions;

import br.com.zup.ecommerce.products.Product;
import br.com.zup.ecommerce.shared.security.AuthenticatedUser;
import br.com.zup.ecommerce.shared.services.FakeMailerService;
import br.com.zup.ecommerce.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class QuestionController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private FakeMailerService fakeMailerService;

    @PostMapping("/produtos/{id}/pergunta")
    @Transactional
    public ResponseEntity<?> addQuestion(@PathVariable Long id, @RequestBody @Valid QuestionRequest questionRequest, @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {

        Product product = entityManager.find(Product.class, id);
        User customer = authenticatedUser.get();
        Question question = questionRequest.toModel(product, customer);

        entityManager.persist(question);

        fakeMailerService.send(new QuestionEmailMessage(question));

        return ResponseEntity.ok(question);
    }

}
