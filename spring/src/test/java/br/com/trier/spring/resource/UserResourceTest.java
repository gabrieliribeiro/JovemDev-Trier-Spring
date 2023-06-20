package br.com.trier.spring.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.http.HttpHeaders;
import java.util.List;

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

import br.com.trier.spring.Application;
import br.com.trier.spring.domain.User;
import br.com.trier.spring.domain.dto.UserDTO;
import br.com.trier.spring.repositories.UserRepositoy;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/usuario.sql")
@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class UserResourceTest {

	@Autowired
	protected TestRestTemplate rest;

	@Autowired
	protected UserRepositoy repository;

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
	@DisplayName("Buscar por id")
	@Sql({ "classpath:/resources/sqls/usuario.sql" })
	public void testGetOk() {
		ResponseEntity<UserDTO> response = getUser("/user/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		UserDTO user = response.getBody();
		assertEquals("Usuario teste 1", user.getName());
	}

	@Test
	@DisplayName("Buscar por id inexistente")
	public void testGetNotFound() {
		ResponseEntity<UserDTO> response = getUser("/user/3");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	@DisplayName("Cadastrar usuário")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	public void testInsertNewUser() {
		User dto = new User(null, "Cadastro", "Cadastro", "Cadastro");
		HttpEntity<User> httpEntity = new HttpEntity<>(dto);

		assertNotNull(rest);

		ResponseEntity<UserDTO> response = rest.exchange("/user", HttpMethod.POST, httpEntity, UserDTO.class);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(1, response.getBody().getId());
	}

	@Test
	@DisplayName("Remover Usuário")
	public void removeUserTest() {
		ResponseEntity<UserDTO> response1 = getUser("/user/1");
		assertEquals(response1.getStatusCode(), HttpStatus.OK);
		UserDTO user = response1.getBody();
		ResponseEntity<Void> response = rest.exchange("/user/" + user.getId(), HttpMethod.DELETE, null,
				Void.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste listar todos usuários")
	public void listAllUsers() {
		ResponseEntity<UserDTO[]> response = rest.exchange("/user", HttpMethod.GET, null, UserDTO[].class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test 
	@DisplayName("Teste de alteração de usuário")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void alterUserTest() {
		
		/*
		 * UserDTO dto = new UserDTO(null, "nome", "email", "senha"); HttpHeaders
		 * headers = new HttpHeaders();
		 * headers.setContentType(MediaType.APPLICATION_JSON); HttpEntity<UserDTO>
		 * requestEntity = new HttpEntity<>(dto, headers); ResponseEntity<UserDTO>
		 * responseEntity = rest.exchange( "/usuarios", HttpMethod.POST, requestEntity,
		 * UserDTO.class ); assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		 * UserDTO user = responseEntity.getBody(); assertEquals("nome",
		 * user.getName());
		 */
		
		
//		User dto = new User(null, "Cadastro", "Cadastro", "Cadastro");
//		HttpEntity<User> httpEntity = new HttpEntity<>(dto);
//
//		assertNotNull(rest);
//
//		ResponseEntity<UserDTO> response = this.rest.exchange("/user/1", HttpMethod.PUT, httpEntity, UserDTO.class);
//
//		assertNotNull(response);
//		assertEquals(HttpStatus.OK, response.getStatusCode());
//		UserDTO user = response.getBody();
//		assertEquals(1, user.getId());
//		assertEquals("Cadastro", user.getName());
//		assertEquals("Cadastro", user.getEmail());
//		assertEquals("Cadastro", user.getName());
		
	}
}
