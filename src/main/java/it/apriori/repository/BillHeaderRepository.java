
package it.apriori.repository;

import java.util.List;

import it.apriori.domain.BillHeader;
import it.apriori.domain.BillRow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BillHeaderRepository
    extends JpaRepository<BillHeader, java.lang.Integer>
{


    public List<BillHeader> findByBillId(java.lang.Integer billId);

    @Query("select b from BillHeader b where  (:billId is null or cast(:billId as string)=cast(b.billId as string)) and (:billRow in elements(b.billRowList)  or :billRow is null) ")
    public List<BillHeader> findByBillIdAndBillRow(
        @org.springframework.data.repository.query.Param("billId")
        java.lang.Integer billId,
        @org.springframework.data.repository.query.Param("billRow")
        BillRow billRow);

}
