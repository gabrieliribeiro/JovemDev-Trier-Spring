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

import br.com.trier.spring.domain.Corrida;
import br.com.trier.spring.services.CampeonatoService;
import br.com.trier.spring.services.CorridaService;
import br.com.trier.spring.services.PistaService;

@RestController
@RequestMapping(value = "corrida")
public class CorridaResource {
	
	@Autowired
	private CorridaService service;
	
	@Autowired
	private PistaService pistaService;
	
	@Autowired
	private CampeonatoService campeonatoService;
	
	@PostMapping
	public ResponseEntity<Corrida> insert(@RequestBody Corrida corrida){
		return ResponseEntity.ok(service.salvar(corrida));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Corrida> buscaPorCodigo(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<Corrida>> listarTodos(){
		return ResponseEntity.ok(service.listAll());
	}
	
	@GetMapping("/pais/{idPista}")
	public ResponseEntity<List<Corrida>> buscaPorPista(@PathVariable Integer idPista){
		return ResponseEntity.ok(service.findByPista(pistaService.findById(idPista)));
	}
	
	@GetMapping("/pais/{idCampeonato}")
	public ResponseEntity<List<Corrida>> buscarPorEquipe(@PathVariable Integer idCampeonato){
		return ResponseEntity.ok(service.findByCampeonato(campeonatoService.findById(idCampeonato)));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Corrida> update(@PathVariable Integer id, @RequestBody Corrida piloto) {
		piloto.setId(id);
		return ResponseEntity.ok(piloto = service.update(piloto));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
}
