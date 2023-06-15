package br.com.trier.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring.domain.User;

@Repository
public interface UserRepositoy extends JpaRepository<User, Integer>{
	
}
