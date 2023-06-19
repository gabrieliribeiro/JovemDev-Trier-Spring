package br.com.trier.spring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring.BaseTests;
import br.com.trier.spring.domain.Pais;
import jakarta.transaction.Transactional;

@Transactional
public class PaisServiceTest extends BaseTests{
	
	@Autowired
	PaisService paisService;
	
	@Test
	@DisplayName("Teste busca todo os paises")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findAllCountriesTest() {
		List<Pais> lista = paisService.listAll();
		assertEquals(4, lista.size());
	}
	
	@Test
	@DisplayName("Teste busca pais por ID")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByIdTest() {
		var pais = paisService.findById(1);
		assertNotEquals(pais, null);
		assertEquals(1, pais.getId());
		assertEquals("Brasil", pais.getName());		
	}
	
	@Test
	@DisplayName("Teste busca por ID inválido")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByNonExistenteId() {
		
	}
}
