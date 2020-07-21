package br.com.endereco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.endereco.resource.dto.EnderecoDTO;

@Service
public class EnderecoService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	public EnderecoDTO getEnderecoV1(String cep) {
		
		ResponseEntity<EnderecoDTO> responseEntity = restTemplate.getForEntity("http://localhost:8083/correios/v1/"+cep,EnderecoDTO.class);
		
		return responseEntity.getBody();
	}
	

	public EnderecoDTO getEnderecoV2(String cep) {
		
		ResponseEntity<EnderecoDTO> responseEntity = restTemplate.getForEntity("http://localhost:8083/correios/v2/"+cep,EnderecoDTO.class);
		
		return responseEntity.getBody();
	}

}
