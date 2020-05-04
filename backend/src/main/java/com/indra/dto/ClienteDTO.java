package com.indra.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class ClienteDTO {
	
	private Long id;
	
	@NotNull(message = "O código da instalação não pode ser nulo")
	private Long instalacaoCodigo;
	
	@Length(min = 3, message = "O nome deve conter no mínimo 3 caracteres")
	@NotNull(message = "O nome não pode ser nulo")
	private String nome;

	@Length(min = 1, message = "A sigla da região deve conter no mínimo 1 caracteres")
	@NotNull(message = "A sigla da região não pode ser nulo")
	private String siglaRegiao;

	@Length(min = 1, message = "A sigla da estado deve conter no mínimo 1 caracteres")
	@NotNull(message = "A sigla da estado não pode ser nulo")
	private String siglaEstado;

	@Length(min = 1, message = "O município deve conter no mínimo 1 caracteres")	
	@NotNull(message = "O município não pode ser nulo")
	private String municipio;
	
}
