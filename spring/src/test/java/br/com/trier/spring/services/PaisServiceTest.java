package br.com.trier.spring.services;

import java.util.List;

import br.com.trier.spring.services.exceptions.ObjetoNaoEncontrado;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring.BaseTests;
import br.com.trier.spring.domain.Pais;
import jakarta.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
public class PaisServiceTest extends BaseTests{
	
	@Autowired
	PaisService paisService;
	
	@Test
	@DisplayName("Teste busca todo os paises")
	@Sql({"classpath:/sql/pais.sql"})
	void findAllCountriesTest() {
		List<Pais> lista = paisService.listAll();
		assertEquals(4, lista.size());
	}
	
	@Test
	@DisplayName("Teste busca pais por ID")
	@Sql({"classpath:/sql/pais.sql"})
	void findByIdTest() {
		var pais = paisService.findById(1);
		assertNotEquals(pais, null);
		assertEquals(1, pais.getId());
		assertEquals("Brasil", pais.getName());		
	}
	
	@Test
	@DisplayName("Teste busca por ID inválido")
	@Sql({"classpath:/sql/pais.sql"})
	void findNotValidIDTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, ()-> paisService.findById(8));
		assertEquals("País 8 não encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste de incluir um país")
	void insertCountryTest(){
		var pais = new Pais(null, "Suíça");
		paisService.salvar(pais);
		pais = paisService.findById(1);
		assertEquals(1, pais.getId());
		assertEquals("Suíça", pais.getName());
	}

	@Test
	@DisplayName("Teste de alterar um país inexistente")
	@Sql({"classpath:/sql/pais.sql"})
	void updateNonexistenteCountry(){
		var pais = new Pais(10, "Chile");
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> paisService.findById(10));
		assertEquals("País 10 não encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste de deletar um país")
	@Sql({"classpath:/sql/pais.sql"})
	void deleteCountryTest(){
		paisService.delete(1);
		List<Pais> lista = paisService.listAll();
		assertEquals(3, lista.size());
		assertEquals(2, lista.get(0).getId());
	}
}
