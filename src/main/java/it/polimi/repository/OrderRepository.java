
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


    public Order findByOrderId(Long orderId);

    public Order findByName(String name);

    public Order findByTimeslotDate(String timeslotDate);

    public Order findByPerson(Person person);

    public Order findByPlaceList(Place placeList);

    @Query("select o from Order o where  (:orderId is null or cast(:orderId as string)=cast(o.orderId as string)) "
    		+ "and (:name is null or :name='' or cast(:name as string)=o.name) and "
    		+ "(:timeslotDate is null or cast(:timeslotDate as string)=cast(o.timeslotDate as string)) "
    		+ "and (:person is null :person=o.person) and (:placeList is null or :placeList in elements(o.placeList)) ")
    public List<Order> findByOrderIdAndNameAndTimeslotDateAndPersonAndPlaceList(
        @Param("orderId")
        Long orderId,
        @Param("name")
        String name,
        @Param("timeslotDate")
        String timeslotDate,
        @Param("person")
        Person person,
        @Param("placeList")
        Place placeList);

}
