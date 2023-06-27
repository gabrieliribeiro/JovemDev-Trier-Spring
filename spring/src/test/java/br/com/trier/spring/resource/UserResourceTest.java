package br.com.trier.spring.resource;

import br.com.trier.spring.Application;
import br.com.trier.spring.config.jwt.LoginDTO;
import br.com.trier.spring.domain.User;
import br.com.trier.spring.domain.dto.UserDTO;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;


import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class UserResourceTest {

    @Autowired
    protected TestRestTemplate rest;

    
    private HttpHeaders getHeaders(String email, String password){
		LoginDTO loginDTO = new LoginDTO(email, password);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				"/auth/token", 
				HttpMethod.POST,  
				requestEntity,    
				String.class   
				);
		String token = responseEntity.getBody();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		return headers;
	}
    

    private ResponseEntity<UserDTO> getUser(String url) {
        return rest.exchange(
				url,  
				HttpMethod.GET, 
				new HttpEntity<>(getHeaders("teste1@teste.com", "1234")), 
				UserDTO.class
				);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<List<UserDTO>> getUsers(String url) {
        return rest.exchange(
				url, HttpMethod.GET, 
				new HttpEntity<>(getHeaders("teste1@teste.com", "1234")), 
				new ParameterizedTypeReference<List<UserDTO>>() {}
			);
    }
    
    @Test
    @DisplayName("Buscar por nome")
    @Sql(scripts = "classpath:sql/limpa_tabelas.sql")
    @Sql(scripts = "classpath:sql/usuario.sql")
    public void findByNameTest() {
		ResponseEntity<List<UserDTO>> response = getUsers("/user/name/u");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, response.getBody().size());
	}

    @Test
    @DisplayName("Buscar por id inexistente")
    @Sql(scripts = "classpath:sql/limpa_tabelas.sql")
    @Sql(scripts = "classpath:sql/usuario.sql")
    public void testGetNotFound() {
        ResponseEntity<UserDTO> response = getUser("/user/50");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Teste de alteração inválida")
    @Sql(scripts = "classpath:sql/limpa_tabelas.sql")
    @Sql(scripts = "classpath:sql/usuario.sql")
    public void testInvalidAlteration(){
        UserDTO dto = new UserDTO(null, "nome", "email", "senha", "ADMIN");
        HttpHeaders headers = getHeaders("teste1@teste.com", "1234");
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
        ResponseEntity<UserDTO> response = getUser("/user/9");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Teste de alteração de usuário")
    @Sql(scripts = "classpath:sql/limpa_tabelas.sql")
    @Sql(scripts = "classpath:sql/usuario.sql")
    public void alterUserTest() {
        UserDTO dto = new UserDTO(null, "nome", "teste1@teste.com", "1234", "ADMIN");
        HttpHeaders headers = getHeaders("teste1@teste.com", "1234");
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
        ResponseEntity<UserDTO> responseEntity = this.rest.exchange("/user/3", HttpMethod.PUT, requestEntity, UserDTO.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        UserDTO user = responseEntity.getBody();
        assertEquals("nome", user.getName());
    }


    @Test
    @DisplayName("Teste listar todos usuários")
    @Sql(scripts = "classpath:sql/limpa_tabelas.sql")
    @Sql(scripts = "classpath:sql/usuario.sql")
    public void listAllUsers() {
        ResponseEntity<List<UserDTO>> response = rest.exchange("/user", 
        		HttpMethod.GET, 
        		new HttpEntity<>(getHeaders("teste1@teste.com", "1234")),
				new ParameterizedTypeReference<List<UserDTO>>() {});
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Remover Usuário")
    @Sql(scripts = "classpath:sql/limpa_tabelas.sql")
    @Sql(scripts = "classpath:sql/usuario.sql")
    public void removeUserTest() {
        ResponseEntity<UserDTO> response1 = getUser("/user/4");
        HttpHeaders headers = getHeaders("teste1@teste.com", "1234");
        HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        UserDTO user = response1.getBody();
        ResponseEntity<Void> response = rest.exchange("/user/" + user.getId(), HttpMethod.DELETE, requestEntity,
                Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Buscar por id")
    @Sql(scripts = "classpath:sql/limpa_tabelas.sql")
    @Sql(scripts = "classpath:sql/usuario.sql")
    public void findByIdTest() {
		ResponseEntity<UserDTO> response = getUser("/user/3");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		UserDTO user = response.getBody();
		assertEquals(3, user.getId());
    }


    @Test
    @DisplayName("teste obter token")
    @Sql(scripts = "classpath:sql/limpa_tabelas.sql")
    public String geraToken() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("test1@test.com.br");
        loginDTO.setPassword("123");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);

        ResponseEntity<String> responseEntity = rest.exchange("/auth/token",
                HttpMethod.POST,
                requestEntity,
                String.class);

        String token = responseEntity.getBody();
        return token;

    }
    
    @Test
    @DisplayName("Cadastrar usuário")
    @Sql(scripts = "classpath:sql/limpa_tabelas.sql")
    @Sql(scripts = "classpath:sql/usuario.sql")
    public void testInsertNewUser() {
        UserDTO dto = new UserDTO(null, "Cadastro", "Cadastro", "Cadastro", "ADMIN");
        HttpHeaders headers = getHeaders("teste1@teste.com", "1234");
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
        ResponseEntity<UserDTO> response = this.rest.exchange("/user", HttpMethod.POST, requestEntity, UserDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Cadastro", response.getBody().getName());
    }
}
