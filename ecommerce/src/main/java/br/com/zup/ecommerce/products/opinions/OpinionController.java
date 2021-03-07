package br.com.zup.ecommerce.products.opinions;

import br.com.zup.ecommerce.products.Product;
import br.com.zup.ecommerce.shared.security.AuthenticatedUser;
import br.com.zup.ecommerce.users.User;
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
public class OpinionController {

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/produtos/{id}/opiniao")
    @Transactional
    public ResponseEntity<?> addOpinion(@PathVariable Long id, @RequestBody @Valid OpinionRequest opinionRequest, @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        User customer = authenticatedUser.get();
        Product product = entityManager.find(Product.class, id);

        product.addOpinions(opinionRequest.toModel(product, customer));

        entityManager.merge(product);

        return ResponseEntity.ok().build();
    }
}
