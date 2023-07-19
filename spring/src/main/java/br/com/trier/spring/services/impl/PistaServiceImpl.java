package br.com.trier.spring.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring.domain.Pais;
import br.com.trier.spring.domain.Pista;
import br.com.trier.spring.repositories.PistaRepository;
import br.com.trier.spring.services.PistaService;
import br.com.trier.spring.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring.services.exceptions.ViolacaoIntegridade;

@Service
public class PistaServiceImpl implements PistaService {
	
	@Autowired
	private PistaRepository repository;
	
	private void validarPista(Pista pista) {
		if (pista == null) {
			throw new ViolacaoIntegridade("Pista não pode ser nula");
		}
		if (pista.getPais() == null) {
			throw new ViolacaoIntegridade("País não pode ser nula");
		}
		if (pista.getTamanho() == null || pista.getTamanho() <= 0) {
			throw new ViolacaoIntegridade("Tamanho não pode ser nulo ou 0");
		}
	}


	@Override
	public Pista salvar(Pista pista) {
		validarPista(pista);
		return repository.save(pista);
	}

	@Override
	public Pista update(Pista pista) {
		findById(pista.getId());
		validarPista(pista);
		return repository.save(pista);
	}

	@Override
	public void delete(Integer id) {
		Pista pista = findById(id);
		repository.delete(pista);
	}

	@Override
	public List<Pista> listAll() {
		List<Pista> lista = repository.findAll();
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhuma pista encontrada");
		}
		return lista;
	}

	@Override
	public Pista findById(Integer id) {
		return repository.findById(id).orElseThrow(()-> new ObjetoNaoEncontrado("Pista id %s não existe".formatted(id)));
	}

	@Override
	public List<Pista> findByTamanhoBetween(Integer tamanhoInicial, Integer tamanhoFinal) {
		List<Pista> lista = repository.findByTamanhoBetween(tamanhoInicial, tamanhoFinal);
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhuma pista encontrada com os tamanho informados");
		}
		return lista;
	}

	@Override
	public List<Pista> findByPaisOrderByTamanhoDesc(Pais pais) {
		List<Pista> lista = repository.findByPaisOrderByTamanhoDesc(pais);
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhuma pista encontrada no país %s".formatted(pais));
		}
		return lista;
	}


	@Override
	public List<Pista> findByName(String name) {
		return repository.findByName(name);
	}
}
