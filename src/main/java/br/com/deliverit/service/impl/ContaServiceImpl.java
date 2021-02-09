package br.com.deliverit.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.deliverit.domain.Conta;
import br.com.deliverit.dto.ContaDTO;
import br.com.deliverit.enums.JurosEnum;
import br.com.deliverit.repository.ContaRepository;
import br.com.deliverit.service.ContaService;
import br.com.deliverit.service.exception.ServiceException;
import br.com.deliverit.service.generic.impl.BasicServiceImpl;
import br.com.deliverit.util.DateUtil;
import br.com.deliverit.util.StringUtil;

@Service
public class ContaServiceImpl extends BasicServiceImpl<ContaRepository, Conta, Long> implements ContaService {
	
	private static final String ZERO_PORCENTO = "0.00%";
	
	@Autowired
	private ModelMapper mapper;
	
	protected ContaServiceImpl(ContaRepository repository) {
		super(repository);
	}

	@Transactional(rollbackOn = ServiceException.class)
	@Override
	public Boolean pagarConta(ContaDTO contaDTO) {
		
		try {
			
			validarConta(contaDTO);
			
			BigDecimal valorCorrigido = null;
			if(contaDTO.getDataPagamento().isAfter(contaDTO.getDataVencimento())) {
				
				Long diasAtraso = Duration.between(contaDTO.getDataVencimento(), contaDTO.getDataPagamento()).toDays();
				contaDTO.setQtdeDiasAtraso(diasAtraso);
				
				valorCorrigido = calcularValorCorrigido(contaDTO, diasAtraso);
			}else {
				valorCorrigido = contaDTO.getValorOriginal();
				contaDTO.setQtdeDiasAtraso(new Long(0));
				contaDTO.setMulta(ZERO_PORCENTO);
				contaDTO.setJurosDia(ZERO_PORCENTO);
			}
			contaDTO.setValorCorrigido(valorCorrigido.setScale(2, RoundingMode.HALF_UP));
			contaDTO.setValorOriginal(contaDTO.getValorOriginal().setScale(2, RoundingMode.HALF_UP));
			
			Conta conta = mapper.map(contaDTO, Conta.class);
			conta.setAtivo(true);
			repository.save(conta);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return true;
	}

	private void validarConta(ContaDTO contaDTO) {
		
		if(contaDTO == null) {
			throw new ServiceException("Favor enviar um registro válido");
		}
		
		if(StringUtils.isBlank(contaDTO.getNome())) {
			throw new ServiceException("Favor enviar um nome de conta válida");
		}else {
			contaDTO.setNome(StringUtil.removerAcentosCaracterEspecial(contaDTO.getNome().toUpperCase()));
		}
		
		if(contaDTO.getDataVencimento() == null || !DateUtil.validarData(contaDTO.getDataVencimento())){
			throw new ServiceException("Favor enviar uma data de vencimento válida");		
		}
		
		if(contaDTO.getDataPagamento() == null || !DateUtil.validarData(contaDTO.getDataPagamento())){
			throw new ServiceException("Favor enviar uma data de pagamento válida");		
		}
		
		if(contaDTO.getValorOriginal() == null || contaDTO.getValorOriginal().equals(0)) {
			throw new ServiceException("Favor enviar um valor de conta válido");
		}
	}
	
	private BigDecimal calcularValorCorrigido(ContaDTO contaDTO, Long diasAtraso) {
		
		BigDecimal valorCorrigido;
		try {
			valorCorrigido = null;
			
			if(diasAtraso <= 3) {
				valorCorrigido = contaDTO.getValorOriginal().add(contaDTO.getValorOriginal().multiply(JurosEnum.MENOR_TRES_DIAS.getJurosTaxa()));
				valorCorrigido = valorCorrigido.add(valorCorrigido.multiply((JurosEnum.MENOR_TRES_DIAS.getJurosDia().multiply(new BigDecimal(diasAtraso)))));
				contaDTO.setMulta(JurosEnum.MENOR_TRES_DIAS.getJurosTaxa().setScale(2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)) + "%");
				contaDTO.setJurosDia(JurosEnum.MENOR_TRES_DIAS.getJurosDia().setScale(3, RoundingMode.HALF_UP).multiply(new BigDecimal(100)) + "%");
			}else if(diasAtraso > 3 && diasAtraso <= 5) {
				valorCorrigido = contaDTO.getValorOriginal().add(contaDTO.getValorOriginal().multiply(JurosEnum.MAIOR_TRES_DIAS.getJurosTaxa()));
				valorCorrigido = valorCorrigido.add(valorCorrigido.multiply((JurosEnum.MAIOR_TRES_DIAS.getJurosDia().multiply(new BigDecimal(diasAtraso)))));
				contaDTO.setMulta(JurosEnum.MAIOR_TRES_DIAS.getJurosTaxa().setScale(2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)) + "%");
				contaDTO.setJurosDia(JurosEnum.MAIOR_TRES_DIAS.getJurosDia().setScale(3, RoundingMode.HALF_UP).multiply(new BigDecimal(100)) + "%");
			}else if(diasAtraso > 5) {
				valorCorrigido = contaDTO.getValorOriginal().add(contaDTO.getValorOriginal().multiply(JurosEnum.MAIOR_CINCO_DIAS.getJurosTaxa()));
				valorCorrigido = valorCorrigido.add(valorCorrigido.multiply((JurosEnum.MAIOR_CINCO_DIAS.getJurosDia().multiply(new BigDecimal(diasAtraso)))));
				contaDTO.setMulta(JurosEnum.MAIOR_CINCO_DIAS.getJurosTaxa().setScale(2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)) + "%");
				contaDTO.setJurosDia(JurosEnum.MAIOR_CINCO_DIAS.getJurosDia().setScale(3, RoundingMode.HALF_UP).multiply(new BigDecimal(100)) + "%");
			}
		} catch (Exception e) {
			throw new ServiceException("Houve um erro ao calcular o valor corrigido", e);
		}
		
		return valorCorrigido;
	}
	
}
