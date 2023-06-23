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
import br.com.trier.spring.domain.User;
import jakarta.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

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
	@DisplayName("Teste de busca campeonato que começa com")
	@Sql(scripts = "classpath:sql/campeaonato.sql")
	void findChampionshipStartWithTest() {
		List<Campeonato> lista = campeonatoService.findByDescricaoContainsIgnoreCase("corrida");
		assertEquals(4, lista.size());
	}
	
	@Test
	@DisplayName("Teste de não encontra campeonato que começa com")
	@Sql(scripts = "classpath:sql/campeaonato.sql")
	void cantFindTest() {
		List<Campeonato> lista;
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> campeonatoService.findByDescricaoContainsIgnoreCase("f"));
		assertEquals("Campeonato não encontrado com a descrição f", exception.getMessage());
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
	@DisplayName("Teste de busca por ID inválido")
	@Sql(scripts = "classpath:sql/campeaonato.sql")
	void searchForInvalidIdTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> campeonatoService.findById(8));
		assertEquals("Campeonato 8 não encontrado", exception.getMessage());
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
	@DisplayName("Teste de inserir campeonato inválido")
	@Sql(scripts = "classpath:sql/campeaonato.sql")
	void insertInvalidChampionshipTest() {
		var campeonato = new Campeonato(null, "Descrição", null);
		var exception = assertThrows(ViolacaoIntegridade.class, () -> campeonatoService.salvar(campeonato));
		assertEquals("Ano inválido!", exception.getMessage());
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
	@DisplayName("Teste de alterar um campeonato inexistente")
	@Sql(scripts = "classpath:sql/campeaonato.sql")
	void updateNonexist() {
		var campeonato = new Campeonato(5, "Formula Indy", 2022);
		var exception = assertThrows(ObjetoNaoEncontrado.class,	() -> campeonatoService.findById(5));
		assertEquals("Campeonato 5 não encontrado", exception.getMessage());
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
