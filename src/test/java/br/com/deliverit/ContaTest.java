package br.com.deliverit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.deliverit.dto.ContaDTO;
import br.com.deliverit.repository.ContaRepository;
import br.com.deliverit.service.impl.ContaServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ContaTest {
	
	@InjectMocks
    private ContaServiceImpl contaService;
	
	@Mock
	private ContaRepository contaRepository; 
	
	@Test
	public void save() {
		ContaDTO conta = new ContaDTO();
		conta.setNome("Teste Conta");
		conta.setDataPagamento(LocalDateTime.now().plusDays(2));
		conta.setDataVencimento(LocalDateTime.now());
		conta.setQtdeDiasAtraso(new Long(2));
		conta.setValorOriginal(new BigDecimal(200));
		boolean retorno = contaService.pagarConta(conta);
		when(retorno).thenThrow(NullPointerException.class);
	    assertEquals(null, retorno);
	}

}
