package com.indra.dto;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class VendaDTO {
	
	private Long id;
	private LocalDateTime dataColeta = LocalDateTime.now();
	private BigDecimal valorCompra;
	private BigDecimal valorVenda;
	private String bandeira;
	
	@NotNull(message = "O cliente não pode ser nulo")
	private Long clienteId;
	
	@NotNull(message = "O produto não pode ser nulo")
	private Long ProdutoId;
	
}
