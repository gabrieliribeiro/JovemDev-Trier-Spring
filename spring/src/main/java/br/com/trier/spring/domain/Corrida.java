package br.com.trier.spring.domain;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import br.com.trier.spring.domain.dto.CorridaDTO;
import br.com.trier.spring.utils.DateUtils;
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
	private ZonedDateTime data;
	
	@ManyToOne
	@NotNull
	private Pista pista;
	
	@ManyToOne
	@NotNull
	private Campeonato campeonato;

	public Corrida(CorridaDTO dto) {
		this(dto.getId(),
				DateUtils.strToZonedDateTime(dto.getData()),
				new Pista(dto.getPistaId(), null, null, null),
				new Campeonato(dto.getCampeonatoId(), dto.getCampeonatoNome(), null));
	}

	public Corrida(CorridaDTO dto, Campeonato campeonato, Pista pista) {
		this(dto.getId(), DateUtils.strToZonedDateTime(dto.getData()), pista, campeonato);
	}

	public CorridaDTO toDTO() {
		return new CorridaDTO(id, DateUtils.zonedDateTimeToStr(data), pista.getId(), campeonato.getId(), campeonato.getDescricao());
	}
}
