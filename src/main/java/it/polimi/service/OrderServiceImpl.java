package it.polimi.service;

import it.polimi.model.Order;
import it.polimi.repository.OrderRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl implements OrderService{

	
	@Autowired
	OrderRepository orderRepository;
	
	@Override
	public Order findByOrderId(Long orderId) {
		return orderRepository.findByOrderId(orderId);
	}

	@Override
	public List<Order> findByTimeslotDate(Date timeslotDate) {
		return orderRepository.findByTimeslotDate(timeslotDate);
	}

	@Override
	public List<Order> findAll() {
		List<Order> orderList= new ArrayList<Order>();
		Iterable<Order> iterable= orderRepository.findAll();
		Iterator<Order> iterator= iterable.iterator();
		while (iterator.hasNext())
		{
			orderList.add(iterator.next());
		}
		
		return orderList;
	}

	@Override
	public void deleteOrderById(Long orderId) {
		orderRepository.delete(orderId);
	}

	@Override
	public void insertOrder(Order order) {
		orderRepository.save(order);
	}

	@Override
	public void updateOrder(Order order) {
		orderRepository.save(order);
	}

}
