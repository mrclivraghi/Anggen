
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

    public List<Order> findByPerson(Person person);

    @Query("select o from Order o where  (:orderId is null or cast(:orderId as string)=cast(o.orderId as string)) and (:name is null or :name='' or cast(:name as string)=o.name) and (:timeslotDate is null or cast(:timeslotDate as string)=cast(o.timeslotDate as string)) and (:person=o.person or :person is null) and (:place in elements(o.placeList)  or :place is null) ")
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
