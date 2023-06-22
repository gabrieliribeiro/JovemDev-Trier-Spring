package br.com.trier.spring.domain.dto;

import br.com.trier.spring.domain.Equipe;
import br.com.trier.spring.domain.Pais;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PilotoDTO {
	
	private Integer id;
	private String nome;
	private Pais pais;
	private Equipe equipe;
	
}
