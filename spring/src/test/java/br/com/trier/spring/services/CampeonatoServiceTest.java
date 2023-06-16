package br.com.trier.spring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring.BaseTests;
import br.com.trier.spring.domain.Campeonato;
import jakarta.transaction.Transactional;

@Transactional
public class CampeonatoServiceTest extends BaseTests{
	
	@Autowired
	CampeonatoService campeonatoService;
	
	@Test
	@DisplayName("Teste lista todos campeonatos")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void listAllChampionshipTest() {
		List<Campeonato> lista = campeonatoService.listAll();
		assertEquals(4, lista.size());
		assertEquals(1, lista.get(0).getId());
	}
	
	@Test
	@DisplayName("Teste encontra campeonato por ID")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findChampionshipByIdTest(){
		var campeonato = campeonatoService.findById(1);
		assertNotEquals(campeonato, null);
		assertEquals(1, campeonato.getId());
		assertEquals(2020, campeonato.getAno());
		assertEquals("Corrida do Rio", campeonato.getDescricao());
	}
	
	
}
