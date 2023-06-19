package br.com.trier.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring.domain.User;

@Repository
public interface UserRepositoy extends JpaRepository<User, Integer>{
	
	//FIXME arrumar para procurar com inicial
	List<User> findByName(String name);

	//List<User> findByNameStartingWithIgnoreCase(String name);
	
	User findByEmail (String email);
}
