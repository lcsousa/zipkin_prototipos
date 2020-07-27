package br.com.endereco.resource;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.endereco.resource.dto.EnderecoDTO;
import br.com.endereco.service.EnderecoService;

@RestController
@RequestMapping("/endereco")
public class EnderecoResource {

	private static final Logger LOG = LoggerFactory.getLogger(EnderecoResource.class);
	@Autowired
	private EnderecoService enderecoService;

	@RequestMapping(method = RequestMethod.POST,path = "v1")
	public ResponseEntity<EnderecoDTO> getEnderecoV1(@RequestBody EnderecoDTO endereco) {
		return ResponseEntity.ok(enderecoService.getEnderecoV1(endereco.getCep()));
	}
	
	@RequestMapping(method = RequestMethod.POST,path = "v2")
	public ResponseEntity<EnderecoDTO> getEnderecoV2( @Valid @RequestBody  EnderecoDTO endereco) {
		return ResponseEntity.ok(enderecoService.getEnderecoV2(endereco.getCep()));
	}
	
	@RequestMapping(method = RequestMethod.GET,path = "v3")
	public ResponseEntity<EnderecoDTO> getEnderecoV3(@RequestParam(value = "cep" )String cep) {
		return ResponseEntity.ok(enderecoService.getEnderecoV1(cep));
	}
	/*
	@GetMapping("/v1/{cep}")
	public ResponseEntity<EnderecoDTO> getEnderecov1(@PathVariable String cep) {
		return ResponseEntity.ok(enderecoService.getEndereco1(cep));
	}*/

}
