package it.polimi.repository;

import java.sql.Date;
import java.util.List;

import javax.persistence.Temporal;

import it.polimi.model.Order;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface OrderRepository extends CrudRepository<Order, Long> {
	
	Order findByOrderId(Long orderId);
	
	List<Order> findByTimeslotDate(Date timeslotDate);
	
	@Query("select o from Order o where "
			+ "(:orderId is null or cast(o.orderId as string)=cast(:orderId as string)) "
			+ "and (:name is null or :name='' or o.name=cast(:name as string)) "
			+ "and (:timeslotDate is null or cast(o.timeslotDate as string)=cast(:timeslotDate as string))")
	List<Order> findByOrderIdAndNameAndTimeslotDate(@Param("orderId") Long orderId, @Param("name")String name,@Param("timeslotDate") String timeslotDate);
}
