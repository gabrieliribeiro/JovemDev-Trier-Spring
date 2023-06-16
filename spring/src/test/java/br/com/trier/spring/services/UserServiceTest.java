package br.com.trier.spring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring.BaseTests;
import br.com.trier.spring.domain.User;
import jakarta.transaction.Transactional;

@Transactional
public class UserServiceTest extends BaseTests{
	
	@Autowired
	UserService userService;
	
	@Test
	@DisplayName("Teste busca por ID")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
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
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findNotValidIdTest() {
		var user = userService.findById(3);
		assertNotEquals(user, null);
	}
	
	@Test
	@DisplayName("Teste listar todos")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void listAllTest() {
		List<User> lista = userService.listAll();
		assertEquals(2, lista.size());
		assertEquals(1, lista.get(0).getId());
	}
	
	@Test
	@DisplayName("Teste incluir usuário")
	void insertUserTest() {
		var user = new User(null, "nome", "email", "senha");
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
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void updateUserTest() {
		var user = new User(1, "altera", "altera", "altera");
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
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void deleteUserTest() {
		userService.delete(1);
		List<User> lista  = userService.listAll();
		assertEquals(1, lista.size());
		assertEquals(2, lista.get(0).getId());
	}
	
	@Test
	@DisplayName("Busca nome do usuário que inicia com ")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void startWithTest() {
		List<User> lista = userService.findByName("Usuario");
		assertEquals(0, lista.size());
		lista = userService.findByName("Usuario teste 2");
		assertEquals(1, lista.size());
	}
}
