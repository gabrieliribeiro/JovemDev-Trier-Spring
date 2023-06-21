package br.com.trier.spring.services;

import java.util.List;

import br.com.trier.spring.domain.User;
import jakarta.persistence.criteria.CriteriaBuilder.In;

public interface UserService {
	User salvar (User user);
	List<User> listAll();
	User findById(Integer id);
	User update (User user);
	void delete (Integer id);
	List<User> findByNameStartingWithIgnoreCase(String name);
}
