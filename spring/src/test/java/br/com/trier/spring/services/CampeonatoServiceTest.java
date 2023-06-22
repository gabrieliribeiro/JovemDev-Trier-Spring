package br.com.trier.spring.services;

import java.util.List;

import br.com.trier.spring.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring.services.exceptions.ViolacaoIntegridade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring.BaseTests;
import br.com.trier.spring.domain.Campeonato;
import jakarta.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
public class CampeonatoServiceTest extends BaseTests{
	
	@Autowired
	CampeonatoService campeonatoService;
	
	@Test
	@DisplayName("Teste lista todos campeonatos")
	@Sql(scripts = "classpath:sql/campeaonato.sql")
	void listAllChampionshipTest() {
		List<Campeonato> lista = campeonatoService.listAll();
		assertEquals(4, lista.size());
		assertEquals(1, lista.get(0).getId());
	}
	
	@Test
	@DisplayName("Teste encontra campeonato por ID")
	@Sql(scripts = "classpath:sql/campeaonato.sql")
	void findChampionshipByIdTest(){
		var campeonato = campeonatoService.findById(1);
		assertNotEquals(campeonato, null);
		assertEquals(1, campeonato.getId());
		assertEquals(2020, campeonato.getAno());
		assertEquals("Corrida do Rio", campeonato.getDescricao());
	}

	@Test
	@DisplayName("Teste de inserir um campeonato")
	void insertChampioshipTest(){
		var campeonato = new  Campeonato(null, "Copa Pistão", 2023);
		campeonatoService.salvar(campeonato);
		List<Campeonato> lista = campeonatoService.listAll();
		assertEquals(1, lista.size());
	}

	@Test
	@DisplayName("Teste alterar um campeonato")
	@Sql(scripts = "classpath:sql/campeaonato.sql")
	void updateTest() {
		var campeonato = new Campeonato(1, "F1", 2023);
		campeonatoService.update(campeonato);
		List<Campeonato> campeonatos = campeonatoService.listAll();
		assertEquals("F1", campeonatos.get(0).getDescricao());
	}

	@Test
	@DisplayName("Teste deletar campeonato")
	@Sql(scripts = "classpath:sql/campeaonato.sql")
	void deleteTest() {
		campeonatoService.delete(1);
		List<Campeonato> campeonatos = campeonatoService.listAll();
		assertEquals(3, campeonatos.size());
	}

}
