package br.com.trier.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring.domain.Pais;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Integer>{
	
	List<Pais> findByName(String name);

}
