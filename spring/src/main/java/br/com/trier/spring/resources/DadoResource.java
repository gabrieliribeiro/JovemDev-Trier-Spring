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
import lombok.Setter;

@Getter
@Setter
@RestController
@RequestMapping(value = "/jogatina")
public class DadoResource {
	
	int soma = 0;
	
	@GetMapping("/aposta/{qtd}/{valor}")
	public String dadoQtd(@PathVariable (name = "qtd")Integer qtd, @PathVariable (name = "valor")Integer valor) {
		//refatorar estrutura do código, fazer métodos para validação, e para sorteio e percentual(gpt)
		//e tentar fazer retorno JSON
		if(qtd >= 1 && qtd <= 4 && valor > 0) {
			System.out.println("Aposta feita com sucesso!!"
					+ "\nDado quantidade de dados: " + qtd
					+ "\nO valor da sua aposta é de: " + valor);
			for (int i = 1; i < qtd; i++) {
		        int numAleatorio = (int)(Math.random() * 6 ) + 1;
		        return ("Número sorteado: " + numAleatorio);
			}
			return("O valor: " + valor);

		}else {
			return("Quantidade ou valor inválido! Informe novamente.");
		}
	}
	
}
