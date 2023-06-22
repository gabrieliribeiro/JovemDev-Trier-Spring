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
import br.com.trier.spring.domain.Pais;
import br.com.trier.spring.domain.Piloto;
import br.com.trier.spring.domain.dto.PilotoDTO;
import br.com.trier.spring.services.PilotoService;

@RestController
@RequestMapping(value = "pilotos")
public class PilotoResource {
	
	@Autowired
	private PilotoService service;

	
	@PostMapping
	public ResponseEntity<PilotoDTO> insert(@RequestBody PilotoDTO piloto){
		Piloto novoPiloto = service.salvar(new Piloto(piloto));
		return novoPiloto!=null ? ResponseEntity.ok(novoPiloto.toDto()) : ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PilotoDTO> buscaPorCodigo(@PathVariable Integer id) {
		Piloto piloto = service.findById(id);
		return ResponseEntity.ok(piloto.toDto());
	}
	
	@GetMapping
	public ResponseEntity<List<PilotoDTO>> listarTodos(){
		return ResponseEntity.ok(service.listAll().stream()
				.map((piloto) -> piloto.toDto())
				.toList());
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<PilotoDTO>> achaPorNome(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameStartingWithIgnoreCase(name).stream()
				.map((piloto) -> piloto.toDto())
				.toList());
	}
	
	@GetMapping("/pais/{idPais}")
	public ResponseEntity<List<PilotoDTO>> buscaPorPais(@PathVariable Pais idPais){
		return ResponseEntity.ok(service.findByPais(idPais).stream()
				.map((piloto) -> piloto.toDto())
				.toList());
	}
	
	@GetMapping("/equipe/{equipe}")
	public ResponseEntity<List<PilotoDTO>> buscarPorEquipe(@PathVariable Equipe equipe){
		return ResponseEntity.ok(service.findByEquipe(equipe).stream()
				.map((piloto) -> piloto.toDto())
				.toList());	
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PilotoDTO> update(@PathVariable Integer id, @RequestBody PilotoDTO pilotoDTO) {
		Piloto piloto = new Piloto(pilotoDTO);
		piloto.setId(id);
		piloto = service.update(piloto);
		return piloto != null ? ResponseEntity.ok(piloto.toDto()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
}
