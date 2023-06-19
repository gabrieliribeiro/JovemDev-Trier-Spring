package br.com.trier.spring.services.exceptions;

public class ViolacaoIntegridade extends RuntimeException{
	
	public ViolacaoIntegridade(String mensagem) {
		super(mensagem);
	}
	
}
