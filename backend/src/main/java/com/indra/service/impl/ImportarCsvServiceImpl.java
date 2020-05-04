package com.indra.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indra.entity.Cliente;
import com.indra.entity.HistoricoPreco;
import com.indra.entity.Produto;
import com.indra.entity.Venda;
import com.indra.service.ClienteService;
import com.indra.service.HistoricoPrecoService;
import com.indra.service.ImportarCsvService;
import com.indra.service.ProdutoService;
import com.indra.service.VendaService;

@Service
public class ImportarCsvServiceImpl implements ImportarCsvService {
	
	@Autowired
	private ClienteService service;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private VendaService vendaService;	
	
	@Autowired
	private HistoricoPrecoService historicoService;	
	
	private static Pattern PADRAO_DATA = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/[12][0-9]{3}$");
	
	Optional<Cliente> cliente;
	
	Cliente novoCliente;
	
	Optional<Produto> produto;
	
	Produto novoProduto;	
	
	Venda novaVenda;
	
	HistoricoPreco hp;
	
	LocalDateTime convertData = null;
	
	Integer flagIndexData = -1;
	
	private String[] valoresSplit;
	
	private String nomeCliente = "";
	
	private String sCodigoInstalacao = "";
	
	private Long codigoInstalacao = null;
	
	private String sPrecoCompra = "";
	
	private String sPrecoVenda = "";
	
	BigDecimal precoCompra = null;
	
	BigDecimal precoVenda = null;
	
	private void limparVariaveis() {
		convertData = null;
		valoresSplit = null;
		nomeCliente = "";
		sCodigoInstalacao = "";
		codigoInstalacao = null;
		sPrecoCompra = "";
		sPrecoVenda = "";
		precoCompra = null;
		precoVenda = null;
		flagIndexData = -1;
		cliente = null;
		novoCliente = null;
		produto = null;
		novoProduto = null;
		novaVenda = null;
	}
	
	private void setarCodigoInstalacaoEnomeCliente() {
		
		if (valoresSplit[4].indexOf("LTDA") != -1) {
			nomeCliente = valoresSplit[3].trim() + " " + valoresSplit[4].trim();
			sCodigoInstalacao = valoresSplit[5].trim();
		} else if (valoresSplit[5].indexOf("LTDA") != -1) {
			nomeCliente = valoresSplit[3].trim() + " " + valoresSplit[4].trim() + " " + valoresSplit[5].trim();
			sCodigoInstalacao = valoresSplit[6].trim();
		} else {
			nomeCliente = valoresSplit[3].trim();
			sCodigoInstalacao = valoresSplit[4].trim();
		}
		
		try {
			
			if (sCodigoInstalacao.length() > 0) {
				codigoInstalacao = Long.parseLong(sCodigoInstalacao);
			}
			
		} catch(Exception e) {
			System.out.println(e);
			nomeCliente += " " + sCodigoInstalacao;
			codigoInstalacao = null;
		};		
		
	}
	
	private void setarDataColeta() {
		
		for (int i=valoresSplit.length-1 ; i > 1 ; i--) {
			if (PADRAO_DATA.matcher(valoresSplit[i].trim()).matches()) {
				convertData = 
				        LocalDateTime.of(
				        		Integer.parseInt(valoresSplit[i].trim().split("/")[2]), 
				        		Integer.parseInt(valoresSplit[i].trim().split("/")[1]), 
				        		Integer.parseInt(valoresSplit[i].trim().split("/")[1]),
				        		12 , 0 , 0 , 0 );
				
				flagIndexData = i;
			}
		}	
		
	}

	private void setarPrecoVendaCompra() {
		
		sPrecoCompra = valoresSplit[flagIndexData+1].replace(",",".").trim();
		sPrecoVenda = valoresSplit[flagIndexData+2].replace(",",".").trim();
		
		try {
			precoCompra = new BigDecimal(sPrecoCompra);
		} catch(Exception e) {
			precoCompra = null;
		}
		
		try {
			precoVenda = new BigDecimal(sPrecoVenda);
		} catch(Exception e) {
			precoVenda = null;
		}	
		
	}
	
	private void adicionarCliente() {
		
		cliente = service.findByNome(nomeCliente);
		novoCliente = new Cliente();
		
		if (cliente.isPresent() == false) {
			
			novoCliente.setInstalacaoCodigo(codigoInstalacao);
			novoCliente.setSiglaRegiao(valoresSplit[0].trim());
			novoCliente.setSiglaEstado(valoresSplit[1].trim());
			novoCliente.setMunicipio(valoresSplit[2].trim());
			novoCliente.setNome(nomeCliente);
			service.save(novoCliente);
			
		}		
		
	}
	
	private void adicionarProduto() {
		
		produto = produtoService.findByNome(valoresSplit[flagIndexData-1].trim());
		novoProduto = new Produto();
				
		if (produto.isPresent() == false) {
			novoProduto.setNome(valoresSplit[flagIndexData-1].trim());
			novoProduto.setPreco(precoCompra);
			novoProduto.setUnidadeMedida(valoresSplit[valoresSplit.length-2]);
			produtoService.save(novoProduto);
		}	
		
	}
	
	private void adicionarVenda() {

		novaVenda = new Venda();
		
		novaVenda.setBandeira(valoresSplit[valoresSplit.length-1]);
		novaVenda.setValorCompra(precoCompra);
		novaVenda.setValorVenda(precoVenda);
		novaVenda.setDataColeta(convertData);				
	
		if ( cliente.isPresent() ) {
			novaVenda.setCliente(cliente.get());
		} else {
			novaVenda.setCliente(novoCliente);
		}
		
		if ( produto.isPresent() ) {
			novaVenda.setProduto(produto.get());
		} else {
			novaVenda.setProduto(novoProduto);
		}				
		
		vendaService.save(novaVenda);		
	}
	
	private void adicionarHistoricoPreco() {
		hp = new HistoricoPreco();
		
		if ( produto.isPresent() ) {
			hp.setProduto(produto.get());
		} else {
			hp.setProduto(novoProduto);
		}	
		
		hp.setValorVenda(precoVenda);
		hp.setDataColeta(convertData);				
		historicoService.save(hp);		
	}
	
	@Override
	public boolean importarCsv() throws IOException {
		
		try {
			
			File arquivoCsv = new File(new File("").getAbsolutePath()+"/src/main/resources/base.csv");
			
			Scanner leitor = new Scanner(new FileInputStream(arquivoCsv), "UTF-8");
			String linhas = new String();
			
			leitor.nextLine();
			
			while(leitor.hasNextLine()) {
				
				linhas = leitor.nextLine();
				valoresSplit = linhas.split("  ");
				
				this.setarCodigoInstalacaoEnomeCliente();
			
				this.adicionarCliente();

				this.setarDataColeta();
				this.setarPrecoVendaCompra();
				
				this.adicionarProduto();
				this.adicionarVenda();
				this.adicionarHistoricoPreco();
				
				this.limparVariaveis();
			}
			
		} catch(FileNotFoundException e) {
			System.out.println("e :"  + e );
			return false;
		}
		
		return true;
	}

}
