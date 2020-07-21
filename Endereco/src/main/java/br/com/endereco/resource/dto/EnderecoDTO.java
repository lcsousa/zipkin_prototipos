package br.com.endereco.resource.dto;

import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

public class EnderecoDTO {
	private String rua;
	
	@NotBlank(message = "Cep é Obrigatório")
	private String cep;
	private String bairro;
	private String cidade;
	private String uf;
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	
	
}
