package br.com.trier.spring.resource;

import br.com.trier.spring.Application;
import br.com.trier.spring.domain.Pais;
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
public class PaisResourceTest {
	
	@Autowired
	protected TestRestTemplate rest;
	
	private ResponseEntity<Pais> getPais(String url) {
		return rest.getForEntity(url, Pais.class);
	}
	
	@SuppressWarnings("unused")
	private ResponseEntity<List<Pais>> getPaises(String url) {
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Pais>>() {
		});
	}

	@Test
	@DisplayName("Teste inserir pais")
	@Sql(scripts = "classpath:sql/limpa_tabelas.sql")
	void insertTest() {
		Pais pais = new Pais(null, "teste");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Pais> requestEntity = new HttpEntity<>(pais, headers);
		ResponseEntity<Pais> responseEntity = rest.exchange("/pais", HttpMethod.POST, requestEntity, Pais.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("teste", responseEntity.getBody().getName());
	}
	
	@Test
	@DisplayName("Teste alterar pais")
	void updateTest() {
		Pais pais = new Pais(1, "teste");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Pais> requestEntity = new HttpEntity<>(pais, headers);
		ResponseEntity<Pais> responseEntity = rest.exchange("/pais/1", HttpMethod.PUT, requestEntity, Pais.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("teste", responseEntity.getBody().getName());
	}

	


}
