package br.com.trier.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring.domain.Equipe;
import br.com.trier.spring.domain.Pais;
import br.com.trier.spring.domain.Piloto;

@Repository
public interface PilotoRepository  extends JpaRepository<Piloto, Integer>{
	List<Piloto> findByNomeStartingWithIgnoreCase(String nome);
	List<Piloto> findByEquipe(Equipe equipe);
	List<Piloto> findByPais(Pais pais);
}
