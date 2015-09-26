
package it.polimi.repository;

import java.util.List;
import it.polimi.model.Order;
import it.polimi.model.Place;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository
    extends CrudRepository<Place, Long>
{


    public List<Place> findByPlaceId(Long placeId);

    public List<Place> findByDescription(String description);

    @Query("select i from it.polimi.model.Place i where  (:placeId is null or cast(:placeId as string)=cast(i.placeId as string)) and (:description is null or :description='' or cast(:description as string)=i.description) and (:order=i.order or :order is null) ")
    public List<Place> findByPlaceIdAndDescriptionAndOrder(
        @Param("placeId")
        Long placeId,
        @Param("description")
        String description,
        @Param("order")
        Order order);

}
