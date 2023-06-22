package br.com.trier.spring.domain;

import java.time.LocalDate;

import br.com.trier.spring.domain.dto.CorridaDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "corrida")
public class Corrida {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_corrida")
	@Setter
	private Integer id;
	
	@Column(name = "data_corrida")
	private LocalDate data;
	
	@ManyToOne
	@NotNull
	private Pista pista;
	
	@ManyToOne
	@NotNull
	private Campeonato campeonato;
	
	public Corrida(CorridaDTO dto) {
		this(dto.getId(), dto.getData(),dto.getPista(), dto.getCampeonato());
	}
	
	public CorridaDTO toDto() {
		return new CorridaDTO(this.id, this.data, this.pista, this.campeonato);
	}
}
