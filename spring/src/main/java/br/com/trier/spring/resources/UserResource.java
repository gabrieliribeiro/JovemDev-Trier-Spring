package br.com.trier.spring.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.spring.domain.User;
import br.com.trier.spring.domain.dto.UserDTO;
import br.com.trier.spring.services.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserResource {
	
	@Autowired
	private UserService services;

	@PostMapping
	public ResponseEntity<UserDTO> insert(@RequestBody UserDTO user){
		User newUser = services.salvar(new User(user));
		return newUser!=null ? ResponseEntity.ok(newUser.toDto()) : ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> buscaPorCodigo(@PathVariable Integer id) {
		User user = services.findById(id);
		return ResponseEntity.ok(user.toDto());
	}
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> listarTodos(){
		return ResponseEntity.ok(services.listAll().stream()
				.map((user) -> user.toDto())
				.toList());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
		User user = new User(userDTO);
		user.setId(id);
		user = services.update(user);

		return user != null ? ResponseEntity.ok(user.toDto()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		services.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<UserDTO>> achaPorNome(@PathVariable String name){
		return ResponseEntity.ok(services.findByNameStartingWithIgnoreCase(name).stream()
				.map((user) -> user.toDto())
				.toList());
	}
}
