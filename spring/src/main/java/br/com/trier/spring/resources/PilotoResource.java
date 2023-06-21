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

import br.com.trier.spring.domain.Piloto;
import br.com.trier.spring.services.EquipeService;
import br.com.trier.spring.services.PaisService;
import br.com.trier.spring.services.PilotoService;

@RestController
@RequestMapping(value = "pilotos")
public class PilotoResource {
	
	@Autowired
	private PilotoService service;
	
	@Autowired
	private PaisService paisService;
	
	@Autowired
	private EquipeService equipeService;
	
	@PostMapping
	public ResponseEntity<Piloto> insert(@RequestBody Piloto piloto){
		return ResponseEntity.ok(service.salvar(piloto));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Piloto> buscaPorCodigo(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<Piloto>> listarTodos(){
		return ResponseEntity.ok(service.listAll());
	}
	
	@GetMapping("/pais/{idPais}")
	public ResponseEntity<List<Piloto>> buscaPorPais(@PathVariable Integer idPais){
		return ResponseEntity.ok(service.findByPais(paisService.findById(idPais)));
	}
	
	@GetMapping("/pais/{idEquipe}")
	public ResponseEntity<List<Piloto>> buscarPorEquipe(@PathVariable Integer idEquipe){
		return ResponseEntity.ok(service.findByEquipe(equipeService.findById(idEquipe)));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Piloto> update(@PathVariable Integer id, @RequestBody Piloto piloto) {
		piloto.setId(id);
		return ResponseEntity.ok(piloto = service.update(piloto));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
}
