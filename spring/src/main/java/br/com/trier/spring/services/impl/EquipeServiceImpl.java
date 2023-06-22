package br.com.trier.spring.services.impl;

import java.util.List;
import java.util.Optional;

import br.com.trier.spring.services.exceptions.ViolacaoIntegridade;
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

	private boolean validaEquipe(Equipe equipe) {
		if(equipe == null) {
			throw new ViolacaoIntegridade("Dado inválido. Equipe está nula");
		} else if(equipe.getNome() == null || equipe.getNome().isBlank()) {
			throw new ViolacaoIntegridade("Preencha os dados da equipe");
		}
		return true;
	}
	
	@Override
	public Equipe salvar(Equipe equipe) {
		if (!validaEquipe(equipe)) {
			return null;
		}

		Optional<Equipe> equipeOptional = repository.findByNome(equipe.getNome());
		if (equipeOptional.isPresent()) {
			Equipe equipExistence = equipeOptional.get();
			if (equipe.getId() != equipExistence.getId()) {
				throw new ViolacaoIntegridade("Este é o nome de uma equipe já existente");
			}
		}

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
	public Equipe findByName(String nome) {
		Optional<Equipe> equipe = repository.findByNome(nome);
		return equipe.orElseThrow(() -> new ObjetoNaoEncontrado("Equipe %s não encontrada".formatted(nome)));
	}

	@Override
	public List<Equipe> findByNameContainsIgnoreCase(String nome) {
		List<Equipe> equipes = repository.findByNomeContainsIgnoreCase(nome);
		if(equipes.size()==0) {
			throw new ObjetoNaoEncontrado("Não há equipes com " + nome);
		}
		return equipes;
	}

	@Override
	public void delete(Integer id) {
		Equipe equipe = findById(id);
		if (equipe != null) {
			repository.delete(equipe);
		}
		
	}
	
}
