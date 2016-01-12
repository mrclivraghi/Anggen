package it.apriori.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="bill_header")
public class BillHeader {
	
	@Id
	private Integer billId;
	
	@OneToMany(fetch=FetchType.EAGER)
    @Type(type = "it.apriori.domain.BillRow")
    @javax.persistence.JoinColumn(name = "bill_id")
	private List<BillRow> billRowList;
	
	public Integer getBillId() {
		return billId;
	}

	public void setBillId(Integer billId) {
		this.billId = billId;
	}

	public List<BillRow> getBillRowList() {
		return billRowList;
	}

	public void setBillRowList(List<BillRow> billRowList) {
		this.billRowList = billRowList;
	}
}
