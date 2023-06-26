package br.com.trier.spring.resource;

import br.com.trier.spring.Application;
import br.com.trier.spring.config.jwt.LoginDTO;
import br.com.trier.spring.domain.User;
import br.com.trier.spring.domain.dto.UserDTO;
import br.com.trier.spring.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
    private MockMvc mockMvc;

    @PersistenceContext
    private EntityManager entityManager;

    private HttpHeaders headers;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    protected UserRepository repository;

    User user;

    private ResponseEntity<UserDTO> getUser(String url) {
        return rest.getForEntity(url, UserDTO.class);
    }
    
    @BeforeEach
    public void setup() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        passwordEncoder = new BCryptPasswordEncoder();
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
        UserDTO dto = new UserDTO(null, "nome", "email", "senha", "ADMIN");
        HttpEntity<UserDTO> httpEntity = new HttpEntity<>(dto);
        ResponseEntity<UserDTO> response = getUser("/user/3");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Teste de alteração de usuário")
    public void alterUserTest() {
        UserDTO dto = new UserDTO(null, "nome", "email", "senha", "ADMIN");
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
    private String getAuthToken() throws Exception {
        // Criação de um usuário de teste no banco de dados H2
    	LoginDTO loginDTO = new LoginDTO("email", passwordEncoder.encode("senha"));
        entityManager.persist(loginDTO);

        // Realiza uma solicitação de autenticação para obter o token
        String url = "/authenticate";
        String requestBody = "{\"username\":\"test_user\",\"password\":\"test_password\"}";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .headers(headers)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        String token = response.getHeader("Authorization").replace("Bearer ", "");

        return token;
    }

    @Test
    @DisplayName("Cadastrar usuário")
    @Sql(scripts = "classpath:sql/limpa_tabelas.sql")
    public void testInsertNewUser() {
        UserDTO dto = new UserDTO(null, "Cadastro", "Cadastro", "Cadastro", "ADMIN");
        HttpEntity<UserDTO> httpEntity = new HttpEntity<>(dto);

        ResponseEntity<UserDTO> response = this.rest.exchange("/user", HttpMethod.POST, httpEntity, UserDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
    }
}
