package com.indra.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente implements Serializable {

	private static final long serialVersionUID = 5186029770455450381L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long instalacaoCodigo;
	private String nome;
	private String siglaRegiao;
	private String siglaEstado;
	private String municipio;
	@OneToMany
	private List<Venda> venda = new ArrayList<>();

}
