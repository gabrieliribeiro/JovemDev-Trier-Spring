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

import br.com.trier.spring.domain.Equipe;
import br.com.trier.spring.services.EquipeService;

@RestController
@RequestMapping(value = "/equipe")
public class EquipeResource {
	
	@Autowired
	private EquipeService service;
	
	@PostMapping
	public ResponseEntity<Equipe> insert(@RequestBody Equipe equipe){
		Equipe newEquipe = service.salvar(equipe);
		return newEquipe!=null ? ResponseEntity.ok(newEquipe) : ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Equipe> buscarPorCodigo(@PathVariable Integer id){
		Equipe equipe = service.findById(id);
		return equipe!=null ? ResponseEntity.ok(equipe) : ResponseEntity.badRequest().build();
	}
	
	@GetMapping
	public ResponseEntity<List<Equipe>>listarTodos(){
		List<Equipe> lista = service.listAll();
		return lista.size()>0 ? ResponseEntity.ok(lista) : ResponseEntity.badRequest().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Equipe> update(@PathVariable Integer id, @RequestBody  Equipe equipe){
		equipe.setId(id);
		equipe = service.update(equipe);
		return equipe!=null ? ResponseEntity.ok(equipe) : ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/nome/{nome}")
	public ResponseEntity<Equipe> findByName(@PathVariable String nome){
		return ResponseEntity.ok(service.findByName(nome));
	}

	@GetMapping("/like/{nome}")
	public ResponseEntity<List<Equipe>> findByNameContainsIgnoreCase(@PathVariable String nome){
		return ResponseEntity.ok(service.findByNameContainsIgnoreCase(nome));
	}
}
