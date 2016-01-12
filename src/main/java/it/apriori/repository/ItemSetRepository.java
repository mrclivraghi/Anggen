
package it.apriori.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.apriori.core.dwdomain.ItemSet;
import it.apriori.domain.Product;

@Repository
public interface ItemSetRepository
    extends JpaRepository<ItemSet, java.lang.Integer>
{


   
}
