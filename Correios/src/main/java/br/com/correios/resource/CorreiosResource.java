package br.com.correios.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.correios.resource.dto.EnderecoDTO;
import br.com.correios.service.CorreiosService;

@RestController
@RequestMapping("/correios")
public class CorreiosResource {

	private static final Logger LOG = LoggerFactory.getLogger(CorreiosResource.class);
	@Autowired
	private CorreiosService correiosService;
	
	@GetMapping("/v1/{cep}")
	public ResponseEntity<EnderecoDTO> getEnderecov1(@PathVariable String cep) {
		return ResponseEntity.ok(correiosService.getEderecoByCepV1(cep));
	}
	@GetMapping("/v2/{cep}")
	public ResponseEntity<EnderecoDTO> getEnderecov2(@PathVariable String cep) {
		return ResponseEntity.ok(correiosService.getEderecoByCepV2(cep));
	}

}
