package it.apriori.core.dwdomain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import it.apriori.domain.Product;

@Entity
@Table(name="item_set")
public class ItemSet {
	
	@Id
	@GeneratedValue
	private Integer itemId;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@Type(type = "it.apriori.domain.Product")
	@JoinTable(name = "item_product", schema = "public", joinColumns = {
	        @JoinColumn(name = "item_id")
	    }, inverseJoinColumns = {
	        @JoinColumn(name = "product_id")
	    })
	private List<Product> productList;
	
	private Double support;
	
	private Integer quantity;
	
	private Double confidence;

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public Double getSupport() {
		return support;
	}

	public void setSupport(Double support) {
		this.support = support;
	}

	public Double getConfidence() {
		return confidence;
	}

	public void setConfidence(Double confidence) {
		this.confidence = confidence;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
}
