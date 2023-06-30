package br.com.trier.spring.services;

import java.util.List;

import br.com.trier.spring.domain.Corrida;
import br.com.trier.spring.domain.Piloto;
import br.com.trier.spring.domain.PilotoCorrida;

public interface PilotoCorridaService {
	
	PilotoCorrida insert(PilotoCorrida pilotoCorrida);
	PilotoCorrida update(PilotoCorrida pilotoCorrida);
	void delete(Integer id);
	List<PilotoCorrida> listAll();
	PilotoCorrida findById(Integer id);
	List<PilotoCorrida> findByPiloto(Piloto piloto);
	List<PilotoCorrida> findByCorrida(Corrida corrida);
	List<PilotoCorrida> findByCorridaOrderByColocacaoAsc(Corrida corrida);
}
