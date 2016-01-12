
package it.apriori.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.apriori.domain.BillHeader;
import it.apriori.domain.BillRow;
import it.apriori.domain.Product;

@Repository
public interface BillRowRepository
    extends JpaRepository<BillRow, java.lang.Integer>
{


    public List<BillRow> findByBillRowId(java.lang.Integer billRowId);

    public List<BillRow> findByQuantity(java.lang.Integer quantity);

    public List<BillRow> findByBillHeader(BillHeader billHeader);

    public List<BillRow> findByProduct(Product product);

    @Query("select b from BillRow b where  (:billRowId is null or cast(:billRowId as string)=cast(b.billRowId as string)) and (:quantity is null or cast(:quantity as string)=cast(b.quantity as string)) and (:billHeader=b.billHeader or :billHeader is null) and (:product=b.product or :product is null) ")
    public List<BillRow> findByBillRowIdAndQuantityAndBillHeaderAndProduct(
        @org.springframework.data.repository.query.Param("billRowId")
        java.lang.Integer billRowId,
        @org.springframework.data.repository.query.Param("quantity")
        java.lang.Integer quantity,
        @org.springframework.data.repository.query.Param("billHeader")
        BillHeader billHeader,
        @org.springframework.data.repository.query.Param("product")
        Product product);

}
