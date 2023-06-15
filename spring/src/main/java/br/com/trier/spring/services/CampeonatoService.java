package br.com.trier.spring.services;

import java.util.List;

import br.com.trier.spring.domain.Campeonato;


public interface CampeonatoService {
	Campeonato salvar (Campeonato campeonato);
	List<Campeonato> listAll();
	Campeonato findById(Integer id);
	Campeonato update(Campeonato campeonato);
	void delete(Integer id);
}
