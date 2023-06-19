package br.com.trier.spring.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring.domain.User;
import br.com.trier.spring.repositories.UserRepositoy;
import br.com.trier.spring.services.UserService;
import br.com.trier.spring.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring.services.exceptions.ViolacaoIntegridade;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepositoy repository;
	
	private void findByEmail(User obj) {
		User user = repository.findByEmail(obj.getEmail());
		if (user != null && user.getId() != obj.getId()) {
			throw new ViolacaoIntegridade("E-mail já cadastrado: %s".formatted(obj.getEmail()));
		}
	}
	
	@Override
	public User salvar(User user) {
		findByEmail(user);
		return repository.save(user);
	}

	@Override
	public List<User> listAll() {
		return repository.findAll();
	}

	@Override
	public User findById(Integer id) {
		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjetoNaoEncontrado("Usuário %s não encontrado".formatted(id)));
		
		//return repositoy.findById(id).orElse(null);
	}

	@Override
	public User update(User user) {
		findByEmail(user);
		return repository.save(user);
	}

	@Override
	public void delete(Integer id) {
		User user = findById(id);
		repository.delete(user);
	}

	@Override
	public List<User> findByName(String name) {
		return repository.findByName(name);
	}
	
}
