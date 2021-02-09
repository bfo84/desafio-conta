package br.com.deliverit.enums;

import java.math.BigDecimal;

public enum JurosEnum {
    MENOR_TRES_DIAS  (new BigDecimal(0.02), new BigDecimal(0.001)), 
    MAIOR_TRES_DIAS  (new BigDecimal(0.03), new BigDecimal(0.002)),
	MAIOR_CINCO_DIAS (new BigDecimal(0.05), new BigDecimal(0.003));


    private BigDecimal jurosTaxa;
	
	private BigDecimal jurosDia;

    private JurosEnum(BigDecimal jurosTaxa, BigDecimal jurosDia) {
        this.jurosTaxa = jurosTaxa;
		this.jurosDia = jurosDia;
    }
	
	public BigDecimal getJurosTaxa() {
		return jurosTaxa;
	}
	
	public BigDecimal getJurosDia() {
		return jurosDia;
	}
}