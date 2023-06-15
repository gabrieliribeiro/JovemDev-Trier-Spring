package br.com.trier.spring.services;

import java.util.List;

import br.com.trier.spring.domain.Equipe;

public interface EquipeService {
	Equipe salvar (Equipe equipe);
	List<Equipe> listAll();
	Equipe findById(Integer id);
	Equipe update(Equipe equipe);
	void delete(Integer id);
}
