package br.com.zup.ecommerce.products.categories;

import br.com.zup.ecommerce.products.ProductRequest;
import br.com.zup.ecommerce.products.characteristics.CharacteristicRequest;
import br.com.zup.ecommerce.shared.security.AuthenticationRequest;
import br.com.zup.ecommerce.users.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class CategoryControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("deveria retornar 200 sem categoria mãe e nome válido e criar a categoria")
    void test1() throws Exception {

        criaUsuario();

        String token = pegaToken();

        CategoryRequest categoryRequest = new CategoryRequest("categoriaMae", null);

        mockMvc.perform(post("/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isOk());

        Category categoriaMae = entityManager.find(Category.class, 1L);
        Assertions.assertEquals("categoriaMae", categoriaMae.getName());

    }

    @Test
    @DisplayName("deveria retornar 200 com categoria mãe e nome válido e criar a categoria")
    void test2() throws Exception {

        String token = pegaToken();

        CategoryRequest categoryRequest = new CategoryRequest("categoriaFilha", 1L);

        mockMvc.perform(post("/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isOk());

        Category categoriaMae = entityManager.find(Category.class, 1L);
        Category categoriaFilha = entityManager.find(Category.class, 2L);

        Assertions.assertEquals("categoriaMae", categoriaMae.getName());
        Assertions.assertEquals("categoriaFilha", categoriaFilha.getName());
    }

    @Test
    @DisplayName("deveria retornar 400 com categoria de nome inválido e nao criar a categoria")
    void test3() throws Exception {

        String token = pegaToken();

        CategoryRequest categoryRequest = new CategoryRequest("", null);

        mockMvc.perform(post("/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isBadRequest());

        List<?> list = entityManager.createQuery("select 1 from Category").getResultList();
        Assertions.assertEquals(2, list.size());
    }

    @Test
    @DisplayName("deveria retornar 400 com categoria mãe inexistente e nao criar a categoria")
    void test4() throws Exception {

        String token = pegaToken();

        CategoryRequest categoryRequest = new CategoryRequest("CategoriaFalha", 10L);

        mockMvc.perform(post("/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isBadRequest());

        List<?> list = entityManager.createQuery("select 1 from Category").getResultList();
        Assertions.assertEquals(2, list.size());
    }

    void criaUsuario() throws Exception {
        UserRequest userRequest = new UserRequest("matheus.stabile@zup.com.br", "123456");
        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)));
    }

    String pegaToken() throws Exception {

        AuthenticationRequest authenticationRequest = new AuthenticationRequest("matheus.stabile@zup.com.br", "123456");

        MvcResult loginResult = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authenticationRequest))).andReturn();

        return loginResult.getResponse().getContentAsString();
    }

    void criaProdutoValido() throws Exception {

        String token = pegaToken();

        CharacteristicRequest c1 = new CharacteristicRequest("c1", "c1");
        CharacteristicRequest c2 = new CharacteristicRequest("c2", "c2");
        CharacteristicRequest c3 = new CharacteristicRequest("c3", "c3");

        List<CharacteristicRequest> caracteristicas = new ArrayList<>();

        caracteristicas.add(c1);
        caracteristicas.add(c2);
        caracteristicas.add(c3);

        CategoryRequest categoryRequest = new CategoryRequest("categoria", null);

        mockMvc.perform(post("/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(objectMapper.writeValueAsString(categoryRequest)));

        ProductRequest productRequest = new ProductRequest("produto", BigDecimal.TEN, 10, "descrição", 1L, caracteristicas);

        mockMvc.perform(post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(objectMapper.writeValueAsString(productRequest)));
    }

}