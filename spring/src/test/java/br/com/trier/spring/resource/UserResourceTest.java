package br.com.trier.spring.resource;

import br.com.trier.spring.Application;
import br.com.trier.spring.domain.User;
import br.com.trier.spring.domain.dto.UserDTO;
import br.com.trier.spring.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/usuario.sql")
@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/limpa_tabelas.sql")
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class UserResourceTest {

    @Autowired
    protected TestRestTemplate rest;

    @Autowired
    protected UserRepository repository;

    User user;

    private ResponseEntity<UserDTO> getUser(String url) {
        return rest.getForEntity(url, UserDTO.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<List<UserDTO>> getUsers(String url) {
        return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<UserDTO>>() {
        });
    }


    @Test
    @DisplayName("Buscar por id inexistente")
    public void testGetNotFound() {
        ResponseEntity<UserDTO> response = getUser("/user/3");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Teste de alteração inválida")
    public void testInvalidAlteration(){
        UserDTO dto = new UserDTO(null, "nome", "email", "senha");
        HttpEntity<UserDTO> httpEntity = new HttpEntity<>(dto);
        ResponseEntity<UserDTO> response = getUser("/user/3");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Teste de alteração de usuário")
    public void alterUserTest() {
        UserDTO dto = new UserDTO(null, "nome", "email", "senha");
        HttpEntity<UserDTO> httpEntity = new HttpEntity<>(dto);
        ResponseEntity<UserDTO> responseEntity = this.rest.exchange("/user/1", HttpMethod.PUT, httpEntity, UserDTO.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        UserDTO user = responseEntity.getBody();
        assertEquals("nome", user.getName());
    }


    @Test
    @DisplayName("Teste listar todos usuários")
    public void listAllUsers() {
        ResponseEntity<UserDTO[]> response = rest.exchange("/user", HttpMethod.GET, null, UserDTO[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Remover Usuário")
    public void removeUserTest() {
        ResponseEntity<UserDTO> response1 = getUser("/user/1");

        assertEquals(response1.getStatusCode(), HttpStatus.OK);

        UserDTO user = response1.getBody();

        ResponseEntity<Void> response = rest.exchange("/user/" + user.getId(), HttpMethod.DELETE, null,
                Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Buscar por id")
    public void findByIdTest() {
        ResponseEntity<UserDTO> response = getUser("/user/1");
        UserDTO user = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, user.getId());
    }

    @Test
    @DisplayName("Cadastrar usuário")
    @Sql(scripts = "classpath:sql/limpa_tabelas.sql")
    public void testInsertNewUser() {
        UserDTO dto = new UserDTO(null, "Cadastro", "Cadastro", "Cadastro");
        HttpEntity<UserDTO> httpEntity = new HttpEntity<>(dto);

        ResponseEntity<UserDTO> response = this.rest.exchange("/user", HttpMethod.POST, httpEntity, UserDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
    }
}
