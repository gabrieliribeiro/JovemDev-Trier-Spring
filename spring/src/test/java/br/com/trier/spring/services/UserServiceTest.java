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
import br.com.trier.spring.domain.User;
import br.com.trier.spring.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring.services.exceptions.ViolacaoIntegridade;
import jakarta.transaction.Transactional;

@Transactional
public class UserServiceTest extends BaseTests{
	
	@Autowired
	UserService userService;
	
	@Test
	@DisplayName("Teste busca por ID")
	@Sql(scripts = "classpath:sql/usuario.sql")
	void findByIdTest() {
		var user = userService.findById(1);
		assertNotEquals(user, null);
		assertEquals(1, user.getId());
		assertEquals("Usuario teste 1", user.getName());
		assertEquals("teste1@teste.com", user.getEmail());
		assertEquals("1234", user.getPassword());		
	}
	
	@Test
	@DisplayName("Teste busca por ID inválido")
	@Sql(scripts = "classpath:sql/usuario.sql")
	void findNotValidIdTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class,	() -> userService.findById(10));
		assertEquals("Usuário 10 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste listar todos")
	@Sql(scripts = "classpath:sql/usuario.sql")
	void listAllTest() {
		List<User> lista = userService.listAll();
		assertEquals(2, lista.size());
		assertEquals(1, lista.get(0).getId());
	}
	
	@Test
	@DisplayName("Teste incluir usuário")
	void insertUserTest() {
		var user = new User(null, "nome", "email", "senha", "ADMIN,USER");
		userService.salvar(user);
		user = userService.findById(1);
		assertNotEquals(user, null);
		assertEquals(1, user.getId());
		assertEquals("nome", user.getName());
		assertEquals("email", user.getEmail());
		assertEquals("senha", user.getPassword());
		
	}
	
	@Test
	@DisplayName("Teste alterar usuário")
	@Sql(scripts = "classpath:sql/usuario.sql")
	void updateUserTest() {
		var user = new User(1, "altera", "altera", "altera", "ADMIN,USER");
		userService.update(user);
		var alteredUser = userService.findById(1);
		assertNotEquals(user, null);
		assertEquals(1, user.getId());
		assertEquals("altera", user.getName());
		assertEquals("altera", user.getEmail());
		assertEquals("altera", user.getPassword());
	}
	
	@Test
	@DisplayName("Teste deleta usuário")
	@Sql(scripts = "classpath:sql/usuario.sql")
	void deleteUserTest() {
		userService.delete(1);
		List<User> lista  = userService.listAll();
		assertEquals(1, lista.size());
		assertEquals(2, lista.get(0).getId());
	}
	
	@Test
	@DisplayName("Busca nome do usuário que inicia com ")
	@Sql(scripts = "classpath:sql/usuario.sql")
	void startWithTest() {
		List<User> lista = userService.findByNameStartingWithIgnoreCase("U");
		assertEquals(2, lista.size());
		lista = userService.findByNameStartingWithIgnoreCase("Usuario");
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("Cadastro com email repetido")
	@Sql(scripts = "classpath:sql/usuario.sql")
	void insertNewUserWithDuplicateEmailTest() {
		var user = new User(null, "Gabrieli", "teste1@teste.com", "1234", "ADMIN,USER");
		var exception = assertThrows(ViolacaoIntegridade.class, ()-> userService.salvar(user));
		assertEquals("E-mail já cadastrado: teste1@teste.com", exception.getMessage());
	}
	
	@Test
	@DisplayName("Alterar usuário inexistente")
	@Sql(scripts = "classpath:sql/usuario.sql")
	void updateNonexistentUser() {
		var user = new User(5, "Gabrieli", "teste5@teste.com", "1234", "ADMIN,USER");
		var exception = assertThrows(ObjetoNaoEncontrado.class,	() -> userService.findById(5));
		assertEquals("Usuário 5 não encontrado", exception.getMessage());
	}
	
}
