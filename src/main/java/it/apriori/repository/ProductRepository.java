
package it.apriori.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.apriori.domain.Product;

@Repository
public interface ProductRepository
    extends JpaRepository<Product, java.lang.Integer>
{


    public List<Product> findByProductId(java.lang.Integer productId);

    public List<Product> findByProductName(java.lang.String productName);

    @Query("select p from Product p where  (:productId is null or cast(:productId as string)=cast(p.productId as string)) and (:productName is null or :productName='' or cast(:productName as string)=p.productName) ")
    public List<Product> findByProductIdAndProductName(
        @org.springframework.data.repository.query.Param("productId")
        java.lang.Integer productId,
        @org.springframework.data.repository.query.Param("productName")
        java.lang.String productName);

}
