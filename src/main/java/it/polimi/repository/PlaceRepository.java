
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


    public Place findByPlaceId(Long placeId);

    public Place findByDescription(String description);

    public Place findByOrder(Order order);

    @Query("select p from Place p where  (:placeId is null or cast(:placeId as string)=cast(p.placeId as string)) and (:description is null or :description='' or cast(:description as string)=p.description)") 
    public List<Place> findByPlaceIdAndDescription(
        @Param("placeId")
        Long placeId,
        @Param("description")
        String description);

}
