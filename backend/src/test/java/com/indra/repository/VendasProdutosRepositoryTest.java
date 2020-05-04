/*
package com.indra.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.After;
//import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.indra.entity.Cliente;
import com.indra.entity.VendasProdutos;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class VendasProdutosRepositoryTest {
	
    @Rule
    public final ExpectedException exception = ExpectedException.none();
	
	private static final String NOMEPRODUTO = "NOME_PRODUTO_1";
	private static final Date DATACOLETA = new Date();
	private static final BigDecimal VALORCOMPRA = BigDecimal.valueOf(3.65);
	private static final BigDecimal VALORVENDA = BigDecimal.valueOf(5.1);
	private static final String UNIDADEMEDIDA = "litro";
	private static final String BANDEIRA = "IPIRANGA";

	private Long savedVendasProdutosId = null;
	private Long savedClienteId = null;


	@Autowired
	VendasProdutosRepository repository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	
	@Before
	public void setUp() {
		
		Cliente cliente = new Cliente(1l,1234l,"Nome1","DF","CO","BRASILIA");
		clienteRepository.save(cliente);

		VendasProdutos vp = new VendasProdutos(
				 1l,NOMEPRODUTO,DATACOLETA,VALORCOMPRA,VALORVENDA,UNIDADEMEDIDA,BANDEIRA,cliente
		);
		repository.save(vp);
		
		savedVendasProdutosId = vp.getId();
		savedClienteId = cliente.getId();
		
	}
	
	
	@After
	public void tearDown() {
		repository.deleteAll();
		clienteRepository.deleteAll();
	}
	
	@Test
	public void testSave() {
		
	
		Cliente cliente = new Cliente(1l,1234l,"Nome1","DF","CO","BRASILIA",  new ArrayList<VendasProdutos>() );
		clienteRepository.save(cliente);
		
		VendasProdutos vp = new VendasProdutos(
		 1l,NOMEPRODUTO,DATACOLETA,VALORCOMPRA,VALORVENDA,UNIDADEMEDIDA,BANDEIRA,cliente
		);
		
		VendasProdutos response = repository.save(vp);
		
		assertNotNull(response);
		assertEquals(response.getNomeProduto() , NOMEPRODUTO);
		assertEquals(response.getDataColeta() , DATACOLETA);
		assertEquals(response.getValorCompra() , VALORCOMPRA);
		assertEquals(response.getValorVenda() , VALORVENDA);
		assertEquals(response.getUnidadeMedida() , UNIDADEMEDIDA);
		assertEquals(response.getBandeira(), BANDEIRA);
		assertEquals(response.getCliente().getId(), cliente.getId());
		
		repository.deleteAll();
		clienteRepository.deleteAll();		
		
	}
	
	@Test
	public void testSaveInvalidVendasProdutos() {
		
		VendasProdutos vp = new VendasProdutos(
	 	 1l,null,null,VALORCOMPRA,null,UNIDADEMEDIDA,BANDEIRA,null
		);
		
		exception.expect(ConstraintViolationException.class);
		
		repository.save(vp);
		
		repository.deleteAll();
		clienteRepository.deleteAll();		
	}
	
	@Test
	public void testUpdate() {

		Cliente cliente = new Cliente(1l,1234l,"Nome1","DF","CO","BRASILIA",new ArrayList<VendasProdutos>());
		clienteRepository.save(cliente);

		VendasProdutos vendaProduto = new VendasProdutos(
				 1l,NOMEPRODUTO,DATACOLETA,VALORCOMPRA,VALORVENDA,UNIDADEMEDIDA,BANDEIRA,cliente
		);
		repository.save(vendaProduto);

		//
		
		Optional<VendasProdutos> vp = repository.findById(vendaProduto.getId());
		
		String nomeproduto = "Nome produto alterado";
		
		VendasProdutos vpAlt = vp.get();
		
		vpAlt.setNomeProduto(nomeproduto);
		
		repository.save(vpAlt);
		
		Optional<VendasProdutos> novoVp = repository.findById(vendaProduto.getId());
		
		assertEquals(nomeproduto, novoVp.get().getNomeProduto());
		
		repository.deleteAll();
		clienteRepository.deleteAll();		
		
	}
	
	@Test
	public void deleteVendasProdutos() {
		
		Cliente clienteOld = new Cliente(1l,1234l,"Nome1","DF","CO","BRASILIA",new ArrayList<VendasProdutos>());
		clienteRepository.save(clienteOld);

		VendasProdutos vendaProduto = new VendasProdutos(
				 1l,NOMEPRODUTO,DATACOLETA,VALORCOMPRA,VALORVENDA,UNIDADEMEDIDA,BANDEIRA,clienteOld
		);
		repository.save(vendaProduto);
		//
		
		Optional<Cliente> cliente = clienteRepository.findById(clienteOld.getId());
		
		VendasProdutos vp = new VendasProdutos(
	  	 1l,NOMEPRODUTO,DATACOLETA,VALORCOMPRA,VALORVENDA,UNIDADEMEDIDA,BANDEIRA,cliente.get()
		);
		
		repository.save(vp);
		repository.deleteById(vp.getId());
		
		Optional<VendasProdutos> response = repository.findById(vp.getId());
		
		assertFalse(response.isPresent());
		
		repository.deleteAll();
		clienteRepository.deleteAll();		
		
	}
	
	@Test
	public void testFindBetweenDates() {
		
		Cliente clienteOld = new Cliente(1l,1234l,"Nome1","DF","CO","BRASILIA");
		clienteRepository.save(clienteOld);

		VendasProdutos vp = new VendasProdutos(
				 1l,NOMEPRODUTO,DATACOLETA,VALORCOMPRA,VALORVENDA,UNIDADEMEDIDA,BANDEIRA,clienteOld
		);
		repository.save(vp);		
		
		//
		
		Optional<Cliente> cliente = clienteRepository.findById(clienteOld.getId());
		
		LocalDateTime localDateTime = DATACOLETA.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		
		Date dataAtualCindoDias = Date.from(localDateTime.plusDays(5).atZone(ZoneId.systemDefault()).toInstant());
		Date dataAtualSeteDias = Date.from(localDateTime.plusDays(7).atZone(ZoneId.systemDefault()).toInstant());
		
		repository.save(
			new VendasProdutos(
				 1l,NOMEPRODUTO,dataAtualCindoDias,VALORCOMPRA,VALORVENDA,UNIDADEMEDIDA,BANDEIRA,cliente.get()
			)				
		);
		
		repository.save(
			new VendasProdutos(
			  	 1l,NOMEPRODUTO,dataAtualSeteDias,VALORCOMPRA,VALORVENDA,UNIDADEMEDIDA,BANDEIRA,cliente.get()
			)				
		);		
		
		PageRequest pg = PageRequest.of(0,10,Sort.Direction.ASC);
		
		Page<VendasProdutos> response = repository.findAllByClienteIdAndDateGreaterThanEqualAndDateLessThanEqual(clienteOld.getId(), DATACOLETA, dataAtualCindoDias, pg);
		
		assertEquals(response.getContent().size(),2);
		assertEquals(response.getTotalElements(),2);
		//assertEquals(response.getContent().get(0).getCliente(), vp.getId());
		
		repository.deleteAll();
		clienteRepository.deleteAll();		
		
	}
	
	@Test
	public void testFindByBandeira() {
		
		Cliente clienteOld = new Cliente(1l,1234l,"Nome1","DF","CO","BRASILIA");
		clienteRepository.save(clienteOld);

		VendasProdutos vp = new VendasProdutos(
				 1l,NOMEPRODUTO,DATACOLETA,VALORCOMPRA,VALORVENDA,UNIDADEMEDIDA,BANDEIRA,clienteOld
		);
		repository.save(vp);
		
		//
		
		List<VendasProdutos> response = repository.findByClienteIdAndBandeira(clienteOld.getId(), BANDEIRA);
		
		assertEquals(response.size(),1);
		assertEquals(response.get(0).getBandeira(), BANDEIRA);
		
		repository.deleteAll();
		clienteRepository.deleteAll();		
	}
		

	
}

*/
