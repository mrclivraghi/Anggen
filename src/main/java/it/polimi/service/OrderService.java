package it.polimi.service;

import java.util.List;

import it.polimi.model.Order;

public interface OrderService {
	
	public Order findByOrderId(Long orderId);
	
	public List<Order> findAll();
	
	public void deleteOrderById(Long orderId);
	
	public void insertOrder(Order order);
	
	public void updateOrder(Order order);
	
	public List<Order> findLike(Order order);
}
