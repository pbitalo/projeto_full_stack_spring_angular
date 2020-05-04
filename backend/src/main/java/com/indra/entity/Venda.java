package com.indra.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venda implements Serializable {

	private static final long serialVersionUID = -4901378597164641906L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime dataColeta = LocalDateTime.now();	
	private BigDecimal valorCompra;
	private BigDecimal valorVenda;
	private String bandeira;	
	@ManyToOne
	private Cliente cliente;
	@OneToOne
	private Produto produto;

}
