package br.com.trier.spring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring.BaseTests;
import br.com.trier.spring.domain.Pista;
import br.com.trier.spring.domain.User;
import br.com.trier.spring.services.exceptions.ObjetoNaoEncontrado;
import jakarta.transaction.Transactional;

@Transactional
public class PistaServiceTest extends BaseTests{
	
	@Autowired
	PistaService pistaService;
	
	@Autowired
	PaisService paisService;
	
	@Test
	@DisplayName("Teste de busca por ID")
	@Sql(scripts = "classpath:sql/pais.sql")
	@Sql(scripts = "classpath:sql/pista.sql")
	void findByIdTest() {
		var pista = pistaService.findById(1);
		assertNotEquals(pista, null);
		assertEquals(1, pista.getId());
	}
	
	@Test
	@DisplayName("Teste de busca por ID inválido")
	@Sql(scripts = "classpath:sql/pais.sql")
	@Sql(scripts = "classpath:sql/pista.sql")
	void findByNotValidIdTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class,	() -> pistaService.findById(10));
		assertEquals("Pista id 10 não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste listar todos")
	@Sql(scripts = "classpath:sql/pais.sql")
	@Sql(scripts = "classpath:sql/pista.sql")
	void findAllTest() {
		List<Pista> lista = pistaService.listAll();
		assertEquals(2, lista.size());
		assertEquals(1, lista.get(0).getId());
	}
	
	@Test
	@DisplayName("Teste de incluir uma pista")
	@Sql(scripts = "classpath:sql/pais.sql")
	void insertNewSpeedwayTest() {
		var pista = new Pista(null, "Interlagos", 2000, paisService.findById(2));
		pistaService.salvar(pista);
		pista = pistaService.findById(1);
		assertNotEquals(pista, null);
		assertEquals(1, pista.getId());
	}
	
	@Test
	@DisplayName("Teste alterar pista")
	@Sql(scripts = "classpath:sql/pais.sql")
	@Sql(scripts = "classpath:sql/pista.sql")
	void updateUserTest() {
		var pista = new Pista(1, "Interlagos", 2500, paisService.findById(2));
		pistaService.update(pista);
		var alteredUser = pistaService.findById(1);
		assertNotEquals(pista, null);
		assertEquals(1, pista.getId());
		assertEquals(2500, pista.getTamanho());
	}
	
	@Test
	@DisplayName("Alterar pista inexistente")
	@Sql(scripts = "classpath:sql/pais.sql")
	@Sql(scripts = "classpath:sql/pista.sql")
	void updateNonexistentUser() {
		var pista = new Pista(5,"Interlagos", 2500, null);
		var exception = assertThrows(ObjetoNaoEncontrado.class,	() -> pistaService.findById(5));
		assertEquals("Pista id 5 não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deleta pista")
	@Sql(scripts = "classpath:sql/pais.sql")
	@Sql(scripts = "classpath:sql/pista.sql")
	void deleteUserTest() {
		pistaService.delete(1);
		List<Pista> lista  = pistaService.listAll();
		assertEquals(1, lista.size());
		assertEquals(2, lista.get(0).getId());
	}
}
