package br.com.trier.spring.services;

import java.util.List;

import br.com.trier.spring.domain.Pais;
import br.com.trier.spring.domain.Pista;
import br.com.trier.spring.domain.User;

public interface PistaService {
	Pista salvar (Pista pista);
	Pista update (Pista pista);
	void delete (Integer id);
	List<Pista> listAll();
	Pista findById(Integer id);
	List<Pista> findByName(String name);
	List<Pista> findByTamanhoBetween(Integer tamanhoInicial, Integer tamanhoFinal);
	List<Pista> findByPaisOrderByTamanhoDesc(Pais pais);
}
