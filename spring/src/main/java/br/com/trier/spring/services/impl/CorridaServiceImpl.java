package br.com.trier.spring.services.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring.domain.Campeonato;
import br.com.trier.spring.domain.Corrida;
import br.com.trier.spring.domain.Pista;
import br.com.trier.spring.repositories.CorridaRepository;
import br.com.trier.spring.services.CorridaService;
import br.com.trier.spring.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring.services.exceptions.ViolacaoIntegridade;

@Service
public class CorridaServiceImpl implements CorridaService{
	
	@Autowired
	private CorridaRepository repository;
	
	private void validaCorrida(Corrida corrida) {
		if (corrida == null) {
			throw new ViolacaoIntegridade("Corrida não pode ser nula");
		}
		if (corrida.getCampeonato() == null) {
			throw new ViolacaoIntegridade("Campeonato não pode ser nula");
		}
		if (corrida.getPista() == null) {
			throw new ViolacaoIntegridade("Pista não pode ser nula");
		}
	}
	
	@Override
	public Corrida salvar(Corrida corrida) {
		validaCorrida(corrida);
		return repository.save(corrida);
	}

	@Override
	public Corrida update(Corrida corrida) {
		findById(corrida.getId());
		validaCorrida(corrida);
		return repository.save(corrida);
	}

	@Override
	public void delete(Integer id) {
		Corrida corrida = findById(id);
		repository.delete(corrida);
	}
	
	@Override
	public List<Corrida> listAll() {
		List<Corrida> lista = repository.findAll();
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhuma corrida encontrada");
		}
		return lista;
	}

	@Override
	public Corrida findById(Integer id) {
		return repository.findById(id).orElseThrow(()-> new ObjetoNaoEncontrado("Corrida com id %s não existe".formatted(id)));
	}

	@Override
	public List<Corrida> findByDataBetween(LocalDate dataInicial, LocalDate dataFinal) {
		List<Corrida> lista = repository.findByDataBetween(dataInicial, dataFinal);
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhuma corrida encontrada com as datas informadas");
		}
		return lista;
	}

	@Override
	public List<Corrida> findByCampeonato(Campeonato campeonato) {
		List<Corrida> lista = repository.findByCampeonato(campeonato);
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhum campeonato encontrado na corrida %s".formatted(campeonato));
		}
		return lista;
	}

	@Override
	public List<Corrida> findByPista(Pista pista) {
		List<Corrida> lista = repository.findByPista(pista);
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhuma pista encontrada no país %s".formatted(pista));
		}
		return lista;
	}

}
