package br.com.trier.spring.services;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.trier.spring.BaseTests;
import jakarta.transaction.Transactional;

@Transactional
public class EquipeServiceTest extends BaseTests{
	
	@Autowired
	EquipeService equipeService;
}
