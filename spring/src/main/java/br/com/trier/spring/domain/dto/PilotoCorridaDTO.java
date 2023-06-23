package br.com.trier.spring.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PilotoCorridaDTO {
	
	private Integer id;
	private Integer pilotoId;
	private String nomePiloto;
	private Integer corridaId;
	private Integer colocacao;
}
