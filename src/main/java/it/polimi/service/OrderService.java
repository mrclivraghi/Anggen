package it.polimi.service;

import java.util.Date;
import java.util.List;

import it.polimi.model.Order;

public interface OrderService {
	
	public Order findByOrderId(Long orderId);
	
	public List<Order> findByTimeslotDate(Date timeslotDate);
	
	public List<Order> findAll();
	
	public void deleteOrderById(Long orderId);
	
	public void insertOrder(Order order);
	
	public void updateOrder(Order order);
}
