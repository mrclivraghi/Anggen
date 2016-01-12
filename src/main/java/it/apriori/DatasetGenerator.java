package it.apriori;

import it.anggen.generation.Generator;
import it.AnggenApplication;
import it.anggen.repository.entity.ProjectRepository;
import it.anggen.repository.field.EnumFieldRepository;
import it.apriori.core.AprioriInterface;
import it.apriori.core.dwdomain.ItemSet;
import it.apriori.domain.BillHeader;
import it.apriori.domain.BillRow;
import it.apriori.domain.Product;
import it.apriori.repository.BillHeaderRepository;
import it.apriori.repository.BillRowRepository;
import it.apriori.repository.ItemSetRepository;
import it.apriori.repository.ProductRepository;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=AnggenApplication.class)
public class DatasetGenerator {
	
	private static final Integer NUM_PRODUCTS=1000;
	
	private static final Integer NUM_BILL_HEADER=1000;
	
	private static final Integer MAX_BILL_ROWS=50;
	
	

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private BillHeaderRepository billHeaderRepository;
	@Autowired
	private BillRowRepository billRowRepository;
	@Autowired
	ItemSetRepository itemSetRepository;
	
	@Autowired
	private AprioriInterface aprioriAlgorithm;
	
	public DatasetGenerator() {
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public void createJs()
	{
		SecureRandom random = new SecureRandom();
		for (int i=0; i<NUM_PRODUCTS; i++)
		{
			Product product = new Product();
			product.setProductId(i);
			String name= "prodotto"+i;
			product.setProductName(name);
			productRepository.save(product);
		}
		for (int i=0; i<NUM_BILL_HEADER; i++)
		{
			BillHeader billHeader = new BillHeader();
			billHeader.setBillId(i);
			billHeaderRepository.save(billHeader);
			Random rn = new Random();
			int n = 50;
			int numRows = Math.abs(rn.nextInt() % n);
			for (int j=0; j<numRows; j++)
			{
				BillRow billRow = new BillRow();
				billRow.setBillHeader(billHeader);
				billRow.setBillRowId(i*MAX_BILL_ROWS+j);
				
				Integer quantity  = Math.abs(rn.nextInt() % 20);
				billRow.setQuantity(quantity);
				
				Integer productId = Math.abs(rn.nextInt() % NUM_PRODUCTS);
				Product product = productRepository.findByProductId(productId).get(0);
				billRow.setProduct(product);
				billRowRepository.save(billRow);
			}
		}
		
	}
	@Test
	public void runApriori()
	{
		//createJs();
		//aprioriAlgorithm.generateFrequentItemSet();
		List<ItemSet> itemSetList = itemSetRepository.findAll();
		List<ItemSet> nextLevelItemSet= aprioriAlgorithm.getNextLevel(itemSetList);
		System.out.println("Trovati: "+nextLevelItemSet.size());
	}

}
