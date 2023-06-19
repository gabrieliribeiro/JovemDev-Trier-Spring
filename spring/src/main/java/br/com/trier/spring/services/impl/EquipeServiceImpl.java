package br.com.trier.spring.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring.domain.Equipe;
import br.com.trier.spring.repositories.EquipeRepository;
import br.com.trier.spring.services.EquipeService;
import br.com.trier.spring.services.exceptions.ObjetoNaoEncontrado;

@Service
public class EquipeServiceImpl implements EquipeService {
	
	@Autowired
	EquipeRepository repository;
	
	@Override
	public Equipe salvar(Equipe equipe) {
		return repository.save(equipe);
	}

	@Override
	public List<Equipe> listAll() {
		return repository.findAll();
	}

	@Override
	public Equipe findById(Integer id) {
		Optional<Equipe> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjetoNaoEncontrado("Equipe %s não encontrada".formatted(id)));
		
	}

	@Override
	public Equipe update(Equipe equipe) {
		return repository.save(equipe);
	}

	@Override
	public void delete(Integer id) {
		Equipe equipe = findById(id);
		if (equipe != null) {
			repository.delete(equipe);
		}
		
	}
	
}
