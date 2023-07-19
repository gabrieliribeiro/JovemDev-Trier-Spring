package br.com.trier.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring.domain.Pais;
import br.com.trier.spring.domain.Pista;

@Repository
public interface PistaRepository extends JpaRepository<Pista, Integer> {
	List<Pista> findByName(String name);
	List<Pista> findByTamanhoBetween(Integer tamanhoInicial, Integer tamanhoFinal);
	List<Pista> findByPaisOrderByTamanhoDesc(Pais pais);
}
