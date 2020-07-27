package br.com.pessoa.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.pessoa.resource.dto.PessoaDTO;
import br.com.pessoa.service.PessoaService;

@RestController
@RequestMapping("/pessoa")
public class PessoaResource {

	private static final Logger LOG = LoggerFactory.getLogger(PessoaResource.class);
	@Autowired
	private PessoaService pessoaService;
	
	@RequestMapping(method = RequestMethod.POST,path = "v1")
	public  ResponseEntity<PessoaDTO>  realizaCompra(@RequestBody PessoaDTO pessoa) {
		pessoa = pessoaService.criarPessoa(pessoa);
		return ResponseEntity.ok(pessoa);
	}
	
	@RequestMapping(method = RequestMethod.POST,path = "v2")
	public  ResponseEntity<PessoaDTO>  realizaCompraV2(@RequestBody PessoaDTO pessoa) {
		pessoa = pessoaService.criarPessoaV2(pessoa);
		return ResponseEntity.ok(pessoa);
	}
	
	@RequestMapping(method = RequestMethod.POST,path = "v3")
	public  ResponseEntity<PessoaDTO>  realizaCompra3(@RequestBody PessoaDTO pessoa) {
		pessoa = pessoaService.criarPessoa3(pessoa);
		return ResponseEntity.ok(pessoa);
	}

	
}
