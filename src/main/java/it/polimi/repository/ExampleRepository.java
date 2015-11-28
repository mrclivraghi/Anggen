
package it.polimi.repository;

import java.util.List;
import it.polimi.domain.Place;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleRepository
    extends CrudRepository<it.polimi.domain.Example, java.lang.Integer>
{


    public List<it.polimi.domain.Example> findByMale(java.lang.Boolean male);

    public List<it.polimi.domain.Example> findByAge(java.lang.Integer age);

    public List<it.polimi.domain.Example> findByExampleDate(java.lang.String exampleDate);

    public List<it.polimi.domain.Example> findByExampleId(java.lang.Integer exampleId);

    public List<it.polimi.domain.Example> findByExampleType(java.lang.Integer exampleType);

    @Query("select e from Example e where  (:male is null or cast(:male as string)=cast(e.male as string)) and (:age is null or cast(:age as string)=cast(e.age as string)) and (:exampleDate is null or cast(:exampleDate as string)=cast(date(e.exampleDate) as string)) and (:exampleId is null or cast(:exampleId as string)=cast(e.exampleId as string)) and (:place in elements(e.placeList)  or :place is null) and (:exampleType is null or cast(:exampleType as string)=cast(e.exampleType as string)) and ( :placePlaceName is null or cast(:placePlaceName as string)='' or e in (select p.example from Place p where p.placeName=cast(:placePlaceName as string))) ")
    public List<it.polimi.domain.Example> findByMaleAndAgeAndExampleDateAndExampleIdAndPlaceAndExampleTypeAndPlaceName(
        @org.springframework.data.repository.query.Param("male")
        java.lang.Boolean male,
        @org.springframework.data.repository.query.Param("age")
        java.lang.Integer age,
        @org.springframework.data.repository.query.Param("exampleDate")
        java.lang.String exampleDate,
        @org.springframework.data.repository.query.Param("exampleId")
        java.lang.Integer exampleId,
        @org.springframework.data.repository.query.Param("place")
        Place place,
        @org.springframework.data.repository.query.Param("exampleType")
        java.lang.Integer exampleType,
        @org.springframework.data.repository.query.Param("placePlaceName")
        java.lang.String placePlaceName);

}
