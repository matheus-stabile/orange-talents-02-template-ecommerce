package br.com.zup.ecommerce.products.images;

import br.com.zup.ecommerce.products.ProductRequest;
import br.com.zup.ecommerce.products.categories.CategoryRequest;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.persistence.EntityManager;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class ImageControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("deve retornar 200 e adicionar a imagem ao produto")
    void test1() throws Exception {

        //NAO USAR ESSA FUNCAO EM NENHUM OUTRO TESTE
        criaUsuario();
        String token = pegaToken();
        criaProdutoValido();

        FileInputStream inputFile = new FileInputStream("/home/matheus/Downloads/zup.jpg");
        MockMultipartFile file = new MockMultipartFile("image", "zup.jpg", "image/jpg", inputFile);

        mockMvc.perform(multipart("/produtos/1/imagens")
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header("Authorization", token))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("deve retornar 400 e nao adicionar a imagem ao produto")
    void test2() throws Exception {

        String token = pegaToken();

        FileInputStream inputFile = new FileInputStream("/home/matheus/Downloads/arquivoinexistente.jpg");
        MockMultipartFile file = new MockMultipartFile("image", "zup.jpg", "image/jpg", inputFile);

        mockMvc.perform(multipart("/produtos/1/imagens")
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header("Authorization", token)).andExpect(status().isInternalServerError())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof FileNotFoundException));
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