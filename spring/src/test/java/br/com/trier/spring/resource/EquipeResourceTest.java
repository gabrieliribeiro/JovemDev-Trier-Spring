package br.com.trier.spring.resource;

import br.com.trier.spring.Application;
import br.com.trier.spring.domain.Equipe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/usuario.sql")
@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/limpa_tabelas.sql")
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EquipeResourceTest {

	@Autowired
	protected TestRestTemplate rest;

	
	@SuppressWarnings("unused")
	private ResponseEntity<List<Equipe>> getEquipes(String url) {
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Equipe>>() {
		});
	}

	@Test
	@DisplayName("Teste inserir equipe")
	@Sql(scripts = "classpath:sql/limpa_tabelas.sql")
	void insertTest() {
		Equipe equipe = new Equipe(null, "teste");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Equipe> requestEntity = new HttpEntity<>(equipe, headers);
		ResponseEntity<Equipe> responseEntity = rest.exchange("/equipe", HttpMethod.POST, requestEntity, Equipe.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("teste", responseEntity.getBody().getNome());
	}
	
	@Test
	@DisplayName("Teste alterar equipe")
	void updateTest() {
		Equipe equipe = new Equipe(1, "teste");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Equipe> requestEntity = new HttpEntity<>(equipe, headers);
		ResponseEntity<Equipe> responseEntity = rest.exchange("/equipe/1", HttpMethod.PUT, requestEntity, Equipe.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("teste", responseEntity.getBody().getNome());
	}
}
