package br.com.zup.ecommerce.products.images;

import br.com.zup.ecommerce.products.Product;
import br.com.zup.ecommerce.shared.security.AuthenticatedUser;
import br.com.zup.ecommerce.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Set;

import static io.jsonwebtoken.lang.Assert.isTrue;

@RestController
public class ImageController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private FakeUploader fakeUploader;

    @PostMapping("/produtos/{id}/imagens")
    @Transactional
    public ResponseEntity<?> addProductImages(@PathVariable Long id, @Valid ImageRequest imageRequest, @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        User owner = authenticatedUser.get();
        Product product = entityManager.find(Product.class, id);

        if (!product.belongsTo(owner)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Set<String> links = fakeUploader.send(imageRequest.getImages());
        product.addImages(links);
        isTrue(product.getImages().size() >= 1, "o produto deve ter imagens associadas");

        entityManager.merge(product);

        return ResponseEntity.ok().build();
    }
}
