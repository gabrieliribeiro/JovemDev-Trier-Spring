package br.com.trier.spring.resources;

import java.util.List;

import br.com.trier.spring.services.CampeonatoService;
import br.com.trier.spring.services.PistaService;
import br.com.trier.spring.utils.DateUtils;
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

	@Autowired
	private  PistaService pistaService;

	@Autowired
	private  CampeonatoService campeonatoService;
	
	
	@PostMapping
	public ResponseEntity<CorridaDTO> insert(@RequestBody CorridaDTO corridaDTO){
		return ResponseEntity.ok(service.salvar(new Corrida(corridaDTO,
						campeonatoService.findById(corridaDTO.getCampeonatoId()),
						pistaService.findById(corridaDTO.getPistaId())))
						.toDTO()
		);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CorridaDTO> buscaPorCodigo(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id).toDTO());
	}
	
	@GetMapping
	public ResponseEntity<List<CorridaDTO>> listarTodos(){
		return ResponseEntity.ok(service.listAll().stream()
				.map((corrida) -> corrida.toDTO())
				.toList()
		);
	}

	@GetMapping("/data/{date}")
	public ResponseEntity<List<CorridaDTO>> findByDate(@PathVariable String date){
		return ResponseEntity.ok(service.findByData(
				DateUtils.strToZonedDateTime(date)).stream()
				.map((corrida) -> corrida.toDTO())
				.toList()
		);
	}

	@GetMapping("/data/{dataInicial}/{dataFinal}")
	public ResponseEntity<List<CorridaDTO>> findByDateBetween(@PathVariable String dataInicial, @PathVariable String dataFinal){
		return ResponseEntity.ok(service.findByDataBetween(
				DateUtils.strToZonedDateTime(dataInicial),
				DateUtils.strToZonedDateTime(dataFinal)).stream()
				.map(Corrida::toDTO)
				.toList()
		);
	}


	@GetMapping("/pista/{idPista}")
	public ResponseEntity<List<CorridaDTO>> buscaPorPista(@PathVariable Pista idPista){
		return ResponseEntity.ok(service.findByPista(idPista).stream()
				.map((corrida) -> corrida.toDTO())
				.toList()
		);
	}
	
	@GetMapping("/campeonato/{campeonato}")
	public ResponseEntity<List<CorridaDTO>> buscarPorEquipe(@PathVariable Campeonato campeonato){
		return ResponseEntity.ok(service.findByCampeonato(campeonato).stream()
				.map((corrida) -> corrida.toDTO())
				.toList()
		);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CorridaDTO> update(@PathVariable Integer id, @RequestBody CorridaDTO corridaDTO) {
		Corrida corrida = new Corrida(corridaDTO,
				campeonatoService.findById(corridaDTO.getCampeonatoId()),
				pistaService.findById(corridaDTO.getPistaId())
		);
		corrida.setId(id);
		return ResponseEntity.ok(service.update(corrida).toDTO());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
}
