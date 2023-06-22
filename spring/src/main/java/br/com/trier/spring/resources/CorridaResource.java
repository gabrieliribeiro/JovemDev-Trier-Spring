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

import br.com.trier.spring.domain.Campeonato;
import br.com.trier.spring.domain.Corrida;
import br.com.trier.spring.domain.Pista;
import br.com.trier.spring.domain.dto.CorridaDTO;
import br.com.trier.spring.services.CorridaService;


@RestController
@RequestMapping(value = "corrida")
public class CorridaResource {
	
	@Autowired
	private CorridaService service;
	
	
	@PostMapping
	public ResponseEntity<CorridaDTO> insert(@RequestBody CorridaDTO corrida){
		Corrida novaCorrida = service.salvar(new Corrida(corrida));
		return novaCorrida!=null ? ResponseEntity.ok(novaCorrida.toDto()) : ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CorridaDTO> buscaPorCodigo(@PathVariable Integer id) {
		Corrida corrida = service.findById(id);
		return ResponseEntity.ok(corrida.toDto());
	}
	
	@GetMapping
	public ResponseEntity<List<CorridaDTO>> listarTodos(){
		return ResponseEntity.ok(service.listAll().stream()
				.map((corrida) -> corrida.toDto())
				.toList());
	}
	
	@GetMapping("/pista/{idPista}")
	public ResponseEntity<List<CorridaDTO>> buscaPorPista(@PathVariable Pista idPista){
		return ResponseEntity.ok(service.findByPista(idPista).stream()
				.map((corrida) -> corrida.toDto())
				.toList());
	}
	
	@GetMapping("/campeonato/{campeonato}")
	public ResponseEntity<List<CorridaDTO>> buscarPorEquipe(@PathVariable Campeonato campeonato){
		return ResponseEntity.ok(service.findByCampeonato(campeonato).stream()
				.map((corrida) -> corrida.toDto())
				.toList());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CorridaDTO> update(@PathVariable Integer id, @RequestBody CorridaDTO corridaDTO) {
		Corrida corrida = new Corrida(corridaDTO);
		corrida.setId(id);
		corrida = service.update(corrida);
		return corrida != null ? ResponseEntity.ok(corrida.toDto()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
}
