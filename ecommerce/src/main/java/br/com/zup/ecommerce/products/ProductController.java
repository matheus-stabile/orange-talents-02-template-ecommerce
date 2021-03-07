package br.com.zup.ecommerce.products;

import br.com.zup.ecommerce.products.views.ProductDetailsResponse;
import br.com.zup.ecommerce.shared.security.AuthenticatedUser;
import br.com.zup.ecommerce.shared.validations.NotSameCharacteristicNameValidator;
import br.com.zup.ecommerce.users.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProductController {

    @PersistenceContext
    private EntityManager entityManager;

    @InitBinder
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new NotSameCharacteristicNameValidator());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid ProductRequest productRequest, @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        User owner = authenticatedUser.get();
        entityManager.persist(productRequest.toModel(entityManager, owner));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailsResponse> getProductById(@PathVariable Long id) {
        Product product = entityManager.find(Product.class, id);
        return ResponseEntity.ok(new ProductDetailsResponse(product));
    }
}
