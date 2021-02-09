package br.com.deliverit.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.deliverit.controller.generic.BasicController;
import br.com.deliverit.domain.Conta;
import br.com.deliverit.dto.ContaDTO;
import br.com.deliverit.service.ContaService;

@RestController
@RequestMapping(value = "/conta")
@CrossOrigin(origins = "*")
public class ContaController extends BasicController<ContaService, Conta, Long> {

	public ContaController(ContaService service) {
		super(service);
	}
	
	@PostMapping("/pagarConta")
	public ResponseEntity< ? > pagarConta(
			@RequestBody ContaDTO contaDTO) {
		return ResponseEntity.ok(service.pagarConta(contaDTO));
	}

}
