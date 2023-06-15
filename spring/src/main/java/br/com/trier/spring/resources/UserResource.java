package br.com.trier.spring.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.spring.domain.User;

@RestController
@RequestMapping(value = "/user")
public class UserResource {
	
	List<User> lista = new ArrayList<User>();
	
	public UserResource() {
		lista.add(new User(1, "Usuário 1", "usuarioum@gmail.com", "1234"));
		lista.add(new User(2, "Usuário 2", "usuarioadois@gmail.com", "1234"));
		lista.add(new User(3, "Usuário 3", "usuariotres@gmail.com", "1234"));
	}
	
	@GetMapping
	public List<User> listAll() {
		return lista;
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<User> findById(@PathVariable(name = "codigo")Integer codigo) {
		User u = lista.stream().filter(user -> user.getId().equals(codigo))
				.findAny()
				.orElse(null);
		return u != null ? ResponseEntity.ok(u) : ResponseEntity.noContent().build();	
	}
	
	public User insert(@RequestBody User u) {
		u.setId(lista.size()+1);
		lista.add(u);
		return u;
	}
}
