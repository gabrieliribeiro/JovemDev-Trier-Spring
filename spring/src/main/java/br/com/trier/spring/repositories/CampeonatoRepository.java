package br.com.trier.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring.domain.Campeonato;

@Repository
public interface CampeonatoRepository extends JpaRepository<Campeonato, Integer>{
	
	List<Campeonato> findByAno(Integer ano);
	List<Campeonato> findByAnoBetween(Integer anoInicial, Integer anoFinal);
	List<Campeonato> findByDescricaoContainsIgnoreCase(String descricao);
	List<Campeonato> findByDescricaoContainsIgnoreCaseAndAnoEquals(String descricao, Integer ano);

}
