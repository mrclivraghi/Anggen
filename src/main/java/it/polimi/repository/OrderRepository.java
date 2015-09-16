package it.polimi.repository;

import java.util.Date;
import java.util.List;

import it.polimi.model.Order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

 @Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
	
	Order findByOrderId(Long orderId);
	
	List<Order> findByTimeslotDate(Date timeslotDate);
}
