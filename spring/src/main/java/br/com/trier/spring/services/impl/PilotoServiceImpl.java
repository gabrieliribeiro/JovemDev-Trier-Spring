package br.com.trier.spring.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring.domain.Equipe;
import br.com.trier.spring.domain.Pais;
import br.com.trier.spring.domain.Piloto;
import br.com.trier.spring.repositories.PilotoRepository;
import br.com.trier.spring.services.PilotoService;
import br.com.trier.spring.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring.services.exceptions.ViolacaoIntegridade;

@Service
public class PilotoServiceImpl implements PilotoService{
	
	@Autowired
	private PilotoRepository repository;
	
	private void validaPiloto(Piloto piloto) {
		if (piloto == null) {
			throw new ViolacaoIntegridade("Piloto não pode ser nulo");
		}
		if(piloto.getNome() == null) {
			throw new ViolacaoIntegridade("Nome do piloto não pode ser nulo");
		}
		if(piloto.getEquipe() == null) {
			throw new ViolacaoIntegridade("Equipe do piloto não pode ser nula");
		}
		if(piloto.getPais() == null) {
			throw new ViolacaoIntegridade("Pais do piloto não pode ser nulo");
		}
	}
	
	@Override
	public Piloto salvar(Piloto piloto) {
		return repository.save(piloto);
	}

	@Override
	public Piloto update(Piloto piloto) {
		findById(piloto.getId());
		validaPiloto(piloto);
		return repository.save(piloto);
	}

	@Override
	public void delete(Integer id) {
		Piloto piloto = findById(id);
		repository.delete(piloto);
	}
	
	@Override
	public List<Piloto> listAll() {
		List<Piloto> lista = repository.findAll();
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhum piloto encontrado");
		}
		return lista;
	}

	@Override
	public Piloto findById(Integer id) {
		return repository.findById(id).orElseThrow(()-> new ObjetoNaoEncontrado("Piloto com id %s não existe".formatted(id)));
	}

	@Override
	public List<Piloto> findByNameStartingWithIgnoreCase(String nome) {
		return repository.findByNomeStartingWithIgnoreCase(nome);
	}

	@Override
	public List<Piloto> findByEquipe(Equipe equipe) {
		List<Piloto> lista = repository.findByEquipe(equipe);
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhum piloto encontrado na equipe %s".formatted(equipe));
		}
		return lista;
	}

	@Override
	public List<Piloto> findByPais(Pais pais) {
		List<Piloto> lista = repository.findByPais(pais);
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhum piloto encontrado no país %s".formatted(pais));
		}
		return lista;
	}

}
