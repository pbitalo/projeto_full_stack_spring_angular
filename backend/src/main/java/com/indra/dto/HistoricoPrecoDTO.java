package com.indra.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class HistoricoPrecoDTO {
	
	private Long id;
	private LocalDateTime dataColeta = LocalDateTime.now();
	private BigDecimal valorVenda;
	private Long produtoId;	

}
