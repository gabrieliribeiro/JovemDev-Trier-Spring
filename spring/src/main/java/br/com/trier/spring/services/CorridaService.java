package br.com.trier.spring.services;

import java.time.LocalDate;
import java.util.List;

import br.com.trier.spring.domain.Campeonato;
import br.com.trier.spring.domain.Corrida;
import br.com.trier.spring.domain.Pista;


public interface CorridaService {
	Corrida salvar (Corrida pista);
	Corrida update (Corrida pista);
	void delete (Integer id);
	List<Corrida> listAll();
	Corrida findById(Integer id);
	List<Corrida> findByDataBetween(LocalDate dataInicial, LocalDate dataFinal);
	List<Corrida> findByCampeonato(Campeonato campeonato);
	List<Corrida> findByPista(Pista pista);
}
