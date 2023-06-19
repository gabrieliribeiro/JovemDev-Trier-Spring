package br.com.trier.spring.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring.domain.Pais;
import br.com.trier.spring.repositories.PaisRepository;
import br.com.trier.spring.services.PaisService;
import br.com.trier.spring.services.exceptions.ObjetoNaoEncontrado;

@Service
public class PaisServiceImpl implements PaisService{
	
	@Autowired
	PaisRepository repository;

	@Override
	public Pais salvar(Pais pais) {
		return repository.save(pais);
	}

	@Override
	public List<Pais> listAll() {
		return repository.findAll();
	}

	@Override
	public Pais findById(Integer id) {
		Optional<Pais> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjetoNaoEncontrado("País %s não encontrado".formatted(id)));
	}

	@Override
	public Pais update(Pais pais) {
		return repository.save(pais);
	}

	@Override
	public void delete(Integer id) {
		Pais pais = findById(id);
		if (pais != null) {
			repository.delete(pais);
		}
		
	}

	@Override
	public List<Pais> findByName(String name) {
		return repository.findByName(name);
	}
	

}
