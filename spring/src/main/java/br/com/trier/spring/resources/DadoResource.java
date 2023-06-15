package br.com.trier.spring.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@RestController
@RequestMapping(value = "/jogatina")
public class DadoResource {
	
	private Integer qtd;
	private Float aposta;
	
	@GetMapping("/quantidade-dados/{qtd}")
	public String dadoQtd(@PathVariable (name = "qtd")Integer qtd) {
		if(qtd >= 1 && qtd <= 4) {
			this.qtd = qtd;
			return("Quantidade de dados escolhida com sucesso!!"
					+ "\nDado quantidade de dados: " + qtd);
		}else {
			return("Quantidade inválida! Informe novamente.");
		}
	}
	
	@GetMapping("/valor-aposta/{valor}")
	public String valor(@PathVariable (name = "valor")Float valor) {
		if(valor > 0 ) {
			this.aposta = valor;
			return("Valor enviado com sucesso!!"
					+ "\nO valor da sua aposta é de: " + valor);
		}else {
			return("Valor inválida");
		}
	}
	
	@GetMapping("/resultado")
	public Integer somar() {
		for (int i = 0; i < getQtd(); i++) {
	        int numAleatorio = (int)(Math.random() * 6 ) + 1;
	        System.out.println("Número sorteado: " + numAleatorio);
	        return null;
	}
	
}
