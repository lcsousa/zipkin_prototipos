package br.com.correios.service;

import org.springframework.stereotype.Service;

import br.com.correios.resource.dto.EnderecoDTO;

@Service
public class CorreiosService {

	public EnderecoDTO getEderecoByCepV1(String cep) {
		
		EnderecoDTO dto = getEndereco(cep);
		return dto;
	}
	
	public EnderecoDTO getEderecoByCepV2(String cep) {
		String a =null;
		a.toString();
		EnderecoDTO dto = getEndereco(cep);
		return dto;
	}
	
	private EnderecoDTO getEndereco(String cep) {
		EnderecoDTO endereco = new EnderecoDTO();
		endereco.setBairro("Brotas");
		endereco.setCep(cep);
		endereco.setCidade("Salvador");
		endereco.setRua("Acupe de Brotas");
		endereco.setUf("BA");
		return endereco;
	}

}
