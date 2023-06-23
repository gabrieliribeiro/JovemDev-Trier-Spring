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
import br.com.trier.spring.services.CampeonatoService;

@RestController
@RequestMapping("/campeonato")
public class CampeonatoResource {
	@Autowired
	private CampeonatoService service;
	
	@PostMapping
	public ResponseEntity<Campeonato> insert(@RequestBody Campeonato campeonato){
		Campeonato newCampeonato= service.salvar(campeonato);
		return newCampeonato!=null ? ResponseEntity.ok(newCampeonato) : ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/codigo/{id}")
	public ResponseEntity<Campeonato> buscarPorCodigo(@PathVariable Integer id){
		Campeonato campeonato = service.findById(id);
		return campeonato != null ? ResponseEntity.ok(campeonato) : ResponseEntity.badRequest().build();
	}
	
	@GetMapping
	public ResponseEntity<List<Campeonato>>listarTodos(){
		List<Campeonato> lista = service.listAll();
		return lista.size()>0 ? ResponseEntity.ok(lista) : ResponseEntity.badRequest().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Campeonato> update(@PathVariable Integer id, @RequestBody  Campeonato campeonato){
		campeonato.setId(id);
		campeonato = service.update(campeonato);
		return campeonato!=null ? ResponseEntity.ok(campeonato) : ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("ano/{ano}")
	public ResponseEntity<List<Campeonato>>findByAno(@PathVariable Integer ano) {
		service.findByAno(ano);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/ano-entre/{anoInicial}/{anoFinal}")
	public ResponseEntity<List<Campeonato>> findByAnoBetween(@PathVariable Integer anoInicial, Integer anoFinal) {
		service.findByAnoBetween(anoInicial, anoFinal);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/descricao/{descricao}")
	public ResponseEntity<List<Campeonato>> findByDescricaoContainsIgnoreCase(@PathVariable String descricao) {
		service.findByDescricaoContainsIgnoreCase(descricao);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/acha-desc-ano/{descricao}/{ano}")
	public ResponseEntity<List<Campeonato>> findByDescricaoContainsIgnoreCaseAndYearEquals(@PathVariable String descricao, Integer ano) {
		service.findByDescricaoContainsIgnoreCaseAndAnoEquals(descricao, ano);
		return ResponseEntity.ok().build();
	}
}
