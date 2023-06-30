package br.com.trier.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring.domain.Corrida;
import br.com.trier.spring.domain.Piloto;
import br.com.trier.spring.domain.PilotoCorrida;

@Repository
public interface PilotoCorridaRepository extends JpaRepository<PilotoCorrida, Integer> {
	
	List<PilotoCorrida> findByPiloto(Piloto piloto); 
	List<PilotoCorrida> findByCorrida(Corrida corrida);
	List<PilotoCorrida> findByCorridaOrderByColocacaoAsc(Corrida corrida);
}
