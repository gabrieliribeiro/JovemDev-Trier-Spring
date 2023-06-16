package br.com.trier.spring.services;

import java.util.List;

import br.com.trier.spring.domain.Pais;

public interface PaisService {
	Pais salvar (Pais pais);
	List<Pais> listAll();
	Pais findById(Integer id);
	Pais update (Pais pais);
	void delete (Integer id);
	List<Pais> findByName(String name);
}
