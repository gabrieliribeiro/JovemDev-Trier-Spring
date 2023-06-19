package br.com.trier.spring.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring.domain.Campeonato;
import br.com.trier.spring.repositories.CampeonatoRepository;
import br.com.trier.spring.services.CampeonatoService;
import br.com.trier.spring.services.exceptions.ObjetoNaoEncontrado;

@Service
public class CampeonatoServiceImpl implements CampeonatoService {
	@Autowired
	CampeonatoRepository repository;

	@Override
	public Campeonato salvar(Campeonato campeonato) {
		return repository.save(campeonato);
	}

	@Override
	public List<Campeonato> listAll() {
		return repository.findAll();
	}

	@Override
	public Campeonato findById(Integer id) {
		Optional<Campeonato> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjetoNaoEncontrado("Campeonato %s não encontrado".formatted(id)));
	}

	@Override
	public Campeonato update(Campeonato campeonato) {
		return repository.save(campeonato);
	}

	@Override
	public void delete(Integer id) {
		Campeonato campeonato = findById(id);
		if (campeonato != null) {
			repository.delete(campeonato);
		}

	}

	@Override
	public List<Campeonato> findByAno(Integer ano) {
		return repository.findByAno(ano);
	}

	@Override
	public List<Campeonato> findByAnoBetween(Integer anoInicial, Integer anoFinal) {
		return repository.findByAnoBetween(anoInicial, anoFinal);
	}

	@Override
	public List<Campeonato> findByDescricaoContainsIgnoreCase(String descricao) {
		return repository.findByDescricaoContainsIgnoreCase(descricao);
	}

	@Override
	public List<Campeonato> findByDescricaoContainsIgnoreCaseAndAnoEquals(String descricao, Integer ano) {
		return repository.findByDescricaoContainsIgnoreCaseAndAnoEquals(descricao, ano);
	}
	
}
