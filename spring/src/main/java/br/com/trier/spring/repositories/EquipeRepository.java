package br.com.trier.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring.domain.Equipe;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Integer>{

    Optional<Equipe> findByNome(String nome);
    List<Equipe> findByNomeContainsIgnoreCase(String nome);
}
