package br.com.trier.spring.services;

import java.util.List;

import br.com.trier.spring.domain.Equipe;
import br.com.trier.spring.domain.Pais;
import br.com.trier.spring.domain.Piloto;


public interface PilotoService {
	Piloto salvar (Piloto piloto);
	Piloto update (Piloto piloto);
	void delete (Integer id);
	List<Piloto> listAll();
	Piloto findById(Integer id);
	List<Piloto> findByNameStartingWithIgnoreCase(String name);
	List<Piloto> findByEquipe(Equipe equipe);
	List<Piloto> findByPais(Pais pais);
}
