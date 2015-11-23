
package it.generated.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository
    extends CrudRepository<it.generated.domain.Place, java.lang.Integer>
{


    public List<it.generated.domain.Place> findByPlaceId(java.lang.Integer placeId);

    public List<it.generated.domain.Place> findByPlaceName(java.lang.String placeName);

    public List<it.generated.domain.Place> findByExample(it.generated.domain.Example example);

    @Query("select p from Place p where  (:placeId is null or cast(:placeId as string)=cast(p.placeId as string)) and (:placeName is null or :placeName='' or cast(:placeName as string)=p.placeName) and (:example=p.example or :example is null) ")
    public List<it.generated.domain.Place> findByPlaceIdAndPlaceNameAndExample(
        @org.springframework.data.repository.query.Param("placeId")
        java.lang.Integer placeId,
        @org.springframework.data.repository.query.Param("placeName")
        java.lang.String placeName,
        @org.springframework.data.repository.query.Param("example")
        it.generated.domain.Example example);

}
