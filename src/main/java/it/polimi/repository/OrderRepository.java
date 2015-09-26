
package it.polimi.repository;

import java.util.List;
import it.polimi.model.Order;
import it.polimi.model.Person;
import it.polimi.model.Place;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository
    extends CrudRepository<Order, Long>
{


    public List<Order> findByOrderId(Long orderId);

    public List<Order> findByName(String name);

    public List<Order> findByTimeslotDate(String timeslotDate);

    @Query("select i from it.polimi.model.Order i where  (:orderId is null or cast(:orderId as string)=cast(i.orderId as string)) and (:name is null or :name='' or cast(:name as string)=i.name) and (:timeslotDate is null or cast(:timeslotDate as string)=cast(i.timeslotDate as string)) and (:person=i.person or :person is null) and (:place in elements(i.placeList)  or :place is null) ")
    public List<Order> findByOrderIdAndNameAndTimeslotDateAndPersonAndPlace(
        @Param("orderId")
        Long orderId,
        @Param("name")
        String name,
        @Param("timeslotDate")
        String timeslotDate,
        @Param("person")
        Person person,
        @Param("place")
        Place place);

}
