package it.apriori.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import it.apriori.core.dwdomain.ItemSet;

@Entity
@Table(name="product")
public class Product {
	
	@Id
	private Integer productId;
	
	private String productName;

	@ManyToMany(mappedBy = "productList")
	private List<ItemSet> itemSetList;
	
	
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public List<ItemSet> getItemSetList() {
		return itemSetList;
	}

	public void setItemSetList(List<ItemSet> itemSetList) {
		this.itemSetList = itemSetList;
	}
	
}
