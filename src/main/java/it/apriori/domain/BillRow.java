package it.apriori.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="bill_row")
public class BillRow {
	
	@Id
	@Column(name="bill_row_id")
	private Integer billRowId;
	
	private Integer quantity;
	
	@ManyToOne
	@javax.persistence.JoinColumn(name = "bill_id")
	private BillHeader billHeader;
	
	@ManyToOne(fetch= FetchType.EAGER)
	@javax.persistence.JoinColumn(name = "product_id")
	private Product product;

	public Integer getBillRowId() {
		return billRowId;
	}

	public void setBillRowId(Integer billRowId) {
		this.billRowId = billRowId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BillHeader getBillHeader() {
		return billHeader;
	}

	public void setBillHeader(BillHeader billHeader) {
		this.billHeader = billHeader;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
