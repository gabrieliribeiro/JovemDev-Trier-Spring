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

import br.com.trier.spring.domain.Pais;
import br.com.trier.spring.domain.Pista;
import br.com.trier.spring.services.PaisService;
import br.com.trier.spring.services.PistaService;

@RestController
@RequestMapping(value = "pistas")
public class PistaResource {
	
	@Autowired
	private PistaService service;
	
	@Autowired
	private PaisService paisService;
	
	@PostMapping
	public ResponseEntity<Pista> insert(@RequestBody Pista pista){
		return ResponseEntity.ok(service.salvar(pista));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Pista> buscaPorCodigo(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Pista>> findByName(@PathVariable String name){
		return ResponseEntity.ok(service.findByName(name));
	}
	
	@GetMapping
	public ResponseEntity<List<Pista>> listarTodos(){
		return ResponseEntity.ok(service.listAll());
	}
	
	@GetMapping("/tamanho/{tamanhoInicial}/{tamanhoFinal}")
	public ResponseEntity<List<Pista>> buscarPorTamanho(@PathVariable Integer tamanhoInicial, Integer tamanhoFinal){
		return ResponseEntity.ok(service.findByTamanhoBetween(tamanhoInicial, tamanhoFinal));
	}
	
	@GetMapping("/pais/{idPais}")
	public ResponseEntity<List<Pista>> buscaPorPais(@PathVariable Integer idPais){
		return ResponseEntity.ok(service.findByPaisOrderByTamanhoDesc(paisService.findById(idPais)));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Pista> update(@PathVariable Integer id, @RequestBody Pista pista) {
		pista.setId(id);
		return ResponseEntity.ok(pista = service.update(pista));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
}
