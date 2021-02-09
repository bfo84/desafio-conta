package br.com.deliverit.enums;

import java.time.Duration;
import java.time.LocalDateTime;

import br.com.deliverit.domain.Conta;

public class TesteEnum {

	public static void main(String[] args) {
		System.out.println(JurosEnum.MAIOR_CINCO_DIAS.getJurosTaxa() + " - "  + JurosEnum.MAIOR_CINCO_DIAS.getJurosDia());
		System.out.println(JurosEnum.MAIOR_TRES_DIAS.getJurosTaxa() + " - "  + JurosEnum.MAIOR_TRES_DIAS.getJurosDia());
		System.out.println(JurosEnum.MENOR_TRES_DIAS.getJurosTaxa() + " - "  + JurosEnum.MENOR_TRES_DIAS.getJurosDia());
		
		Conta conta = new Conta();
		conta.setDataPagamento(LocalDateTime.now().plusDays(3));
		conta.setDataVencimento(LocalDateTime.now());
		System.out.println(conta.getDataVencimento());
		System.out.println(conta.getDataPagamento());
		System.out.println(Duration.between(conta.getDataVencimento(), conta.getDataPagamento()).toDays());
		System.out.println("0%");

	}

}
