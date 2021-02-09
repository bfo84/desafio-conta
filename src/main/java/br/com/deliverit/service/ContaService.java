package br.com.deliverit.service;

import br.com.deliverit.domain.Conta;
import br.com.deliverit.dto.ContaDTO;
import br.com.deliverit.service.generic.BasicService;

public interface ContaService extends BasicService<Conta, Long> {

	Boolean pagarConta(ContaDTO contaDTO);
	
}
