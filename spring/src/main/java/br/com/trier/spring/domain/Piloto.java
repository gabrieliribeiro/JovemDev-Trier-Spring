package br.com.trier.spring.domain;

import br.com.trier.spring.domain.dto.PilotoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of ="id")
@Entity(name = "piloto")
public class Piloto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_piloto")
	@Setter
	private Integer id;
	
	@Column(name = "nome_piloto")
	private String nome;
	
	@ManyToOne
	private Pais pais;
	
	@ManyToOne
	private Equipe equipe;

	public Piloto(PilotoDTO dto) {
		this(dto.getId(),
				dto.getNome(),
				new Pais(dto.getPaisId(), dto.getPaisNome()),
				new Equipe(dto.getEquipeId(), dto.getEquipeNome()));
	}

	public PilotoDTO toDTO() {
		return new PilotoDTO(id, nome, pais.getId(), pais.getName(), equipe.getId(), equipe.getNome());
	}
}
