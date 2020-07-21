package br.com.pessoa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.pessoa.resource.dto.EnderecoDTO;
import br.com.pessoa.resource.dto.PessoaDTO;

@Service
public class PessoaService {
	@Autowired
	private RestTemplate restTemplate;

	public PessoaDTO criarPessoa(PessoaDTO pessoa) {
		
		HttpEntity<EnderecoDTO> request = new HttpEntity<>(pessoa.getEndereco());
		EnderecoDTO endereco = restTemplate.postForObject("http://localhost:8082/endereco/v1", request, EnderecoDTO.class);
		
		
		pessoa.setEndereco(endereco);
		return pessoa;
	}
	
 public PessoaDTO criarPessoaV2(PessoaDTO pessoa) {
		
		HttpEntity<EnderecoDTO> request = new HttpEntity<>(pessoa.getEndereco());
		EnderecoDTO endereco = restTemplate.postForObject("http://localhost:8082/endereco/v2", request, EnderecoDTO.class);
		
		
		pessoa.setEndereco(endereco);
		return pessoa;
	}
	
	

}
