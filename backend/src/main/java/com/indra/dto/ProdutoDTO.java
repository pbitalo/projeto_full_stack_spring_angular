package com.indra.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class ProdutoDTO {
	
	private Long id;
	
	@Length(min = 2, message = "O nome do produto deve conter no mínimo 2 caracteres")
	@NotNull(message = "O nome do produto não pode ser nulo")
	private String nome;
	
	@NotNull(message = "O preço não pode ser nulo")
	private BigDecimal preco;	
	
	@Length(min = 2, message = "A unidade de medida deve conter no mínimo 2 caracteres")
	@NotNull(message = "O nome da unidade de medidade não pode ser nulo")
	private String unidadeMedida;
		

}
