package br.com.trier.spring.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring.domain.Campeonato;
import br.com.trier.spring.domain.Corrida;
import br.com.trier.spring.domain.Pista;

@Repository
public interface CorridaRepository extends JpaRepository<Corrida, Integer>{
	List<Corrida> findByDataBetween(ZonedDateTime dataInicial, ZonedDateTime dataFinal);
	List<Corrida> findByCampeonato(Campeonato campeonato);
	List<Corrida> findByPista(Pista pista);
}
