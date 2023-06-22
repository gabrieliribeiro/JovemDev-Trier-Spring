package br.com.trier.spring.resource;

import br.com.trier.spring.Application;
import br.com.trier.spring.domain.Campeonato;
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
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/campeaonato.sql")
@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/limpa_tabelas.sql")
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CampeonatoResourceTest {

	@Autowired
	protected TestRestTemplate rest;
	
	private ResponseEntity<Campeonato> getCampeonato(String url) {
		return rest.getForEntity(url, Campeonato.class);
	}
	
	@SuppressWarnings("unused")
	private ResponseEntity<List<Campeonato>> getCampeonatos(String url) {
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Campeonato>>() {
		});
	}
	
	@Test
	@DisplayName("Teste alterar campeonato")
	void updateTest() {
		Campeonato campeonato = new Campeonato(1, "teste", 2020);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Campeonato> requestEntity = new HttpEntity<>(campeonato, headers);
		ResponseEntity<Campeonato> responseEntity = rest.exchange("/campeonato/1", HttpMethod.PUT, requestEntity, Campeonato.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("teste", responseEntity.getBody().getDescricao());
	}
	
	@Test
	@DisplayName("Teste deletar campeonato")
	void deleteTest() {
		ResponseEntity<Void> response = rest.exchange("/campeonato/1", HttpMethod.DELETE, null, Void.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste buscar todos os campeonatos")
	void listAllTest() {
		ResponseEntity<List<Campeonato>> response = getCampeonatos("/campeonato");
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Teste buscar campeonatos pelo ano")
	void findByAnoTest() {
		ResponseEntity<List<Campeonato>> response = getCampeonatos("/campeonato/ano/2023");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	

	

	

}
