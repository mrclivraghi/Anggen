
package it.polimi.repository.test;

import java.util.List;
import it.polimi.model.test.Order;
import it.polimi.model.test.Place;
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

    public List<Place> findByOrder(Order order);

    @Query("select p from Place p where  (:placeId is null or cast(:placeId as string)=cast(p.placeId as string)) and (:description is null or :description='' or cast(:description as string)=p.description) and (:order=p.order or :order is null) ")
    public List<Place> findByPlaceIdAndDescriptionAndOrder(
        @Param("placeId")
        Long placeId,
        @Param("description")
        String description,
        @Param("order")
        Order order);

}
