package it.apriori.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.apriori.core.dwdomain.ItemSet;
import it.apriori.domain.BillHeader;
import it.apriori.domain.BillRow;
import it.apriori.domain.Product;
import it.apriori.repository.BillHeaderRepository;
import it.apriori.repository.BillRowRepository;
import it.apriori.repository.ItemSetRepository;
import it.apriori.repository.ProductRepository;

@Service
public class AprioriImpl implements AprioriInterface{

	private static final Integer minSupport = 30;
	
	@Autowired
	BillRowRepository billRowRepository;
	
	@Autowired
	BillHeaderRepository billHeaderRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ItemSetRepository itemSetRepository;
	
	private List<BillHeader> billHeaderList;
	
	
	@Override
	public void generateFrequentItemSetFirstLevel() {
		
		List<BillRow> billRowList = billRowRepository.findAll();
		List<Product> productList = productRepository.findAll();
		Map<Integer,ItemSet> itemSetList = new HashMap<Integer,ItemSet>();
		for (Product product: productList)
		{
			ItemSet itemSet = new ItemSet();
			List<Product> itemProductList=itemSet.getProductList();
			if (itemProductList==null)
				itemProductList= new ArrayList<Product>();
			itemProductList.add(product);
			itemSet.setProductList(itemProductList);
			itemSet.setConfidence(0.0);
			itemSet.setSupport(0.0);
			itemSetList.put(product.getProductId(), itemSet);
		}
		itemSetRepository.save(itemSetList.values());
		for (BillRow billRow : billRowList)
		{
			ItemSet itemSet = itemSetList.get(billRow.getProduct().getProductId());
			Double support = itemSet.getSupport();
			if (support==null)
				support=0.0;
			Integer quantity = itemSet.getQuantity();
			if (quantity==null)
				quantity=0;
			itemSet.setSupport(support+1);
			itemSet.setQuantity(quantity+billRow.getQuantity());
			itemSetRepository.save(itemSet);
		}
	}
	@Override
	public Boolean generateFrequentItemSet(Integer level) {
		
		if (level<=1) return false;
		
		List<ItemSet> itemSetList = itemSetRepository.findAll();
		
		
		
		return false;
	}
	
	@Override
	public void generateRule() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<ItemSet> getNextLevel(List<ItemSet> itemSetList) {
		billHeaderList = billHeaderRepository.findAll();
		
		for (int i=0; i<itemSetList.size(); i++)
		{
			if (itemSetList.get(i).getSupport()<minSupport)
			{
				itemSetList.remove(i);
				i--;
			}
		}
		if (1==0)
		return itemSetList;
		List<ItemSet> returnedItemSetList = new ArrayList<ItemSet>();
		Integer first=0;
		Integer second=0;
		for (ItemSet firstItemSet : itemSetList)
		{
			first++;
			second=0;
			for (ItemSet secondItemSet: itemSetList)
			{
				second++;
				System.out.println("first "+first+" second "+second);
				Collection<Product> productSet=canMix(firstItemSet,secondItemSet);
				if (productSet!=null)
				{
					ItemSet itemSet= new ItemSet();
					itemSet.setProductList(new ArrayList(productSet));
					calculateParameters(itemSet);
					if (itemSet.getSupport()>0)
					returnedItemSetList.add(itemSet);
				}
			}
		}
		
		return returnedItemSetList;
	}

	private void calculateParameters(ItemSet itemSet) {
		List<Integer> productIdList = new ArrayList<Integer>();
		for (Product product: itemSet.getProductList())
		{
			productIdList.add(product.getProductId());
		}
		Double support=0.0;
		Integer quantity=0;
		for (BillHeader billHeader: billHeaderList)
		{
			Integer toBeFound=itemSet.getProductList().size();
			for (BillRow billRow: billHeader.getBillRowList())
			{
				if (productIdList.contains(billRow.getProduct().getProductId()))
				{
						toBeFound--;
						quantity+=billRow.getQuantity();
				}
			}
			if (toBeFound==0)
			{
				support++;
			}
		}
		itemSet.setQuantity(quantity);
		itemSet.setSupport(support);
		
	}
	public Collection<Product> canMix(ItemSet firstItemSet,ItemSet secondItemSet)
	{
		if (firstItemSet.getSupport()<minSupport || secondItemSet.getSupport()<minSupport)
			return null;
		List<Product> firstProductList= firstItemSet.getProductList();
		List<Product> secondProductList= secondItemSet.getProductList();
		Integer length=firstProductList.size();
		Set<Product> productSet = new HashSet<>();
		productSet.addAll(firstProductList);
		productSet.addAll(secondProductList);
		if (productSet.size()==length+1)
		{
			//should check all combination of the new set.
			return productSet; //they differ just for one element: ok
			
		}
		return null;
	}


}
