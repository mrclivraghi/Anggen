package it.polimi.service;

import it.polimi.model.Order;
import it.polimi.model.Place;
import it.polimi.repository.OrderRepository;
import it.polimi.repository.PlaceRepository;
import it.polimi.utils.Utility;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class OrderServiceImpl implements OrderService{

	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	PlaceRepository placeRepository;
	
	@Override
	public Order findByOrderId(Long orderId) {
		return orderRepository.findByOrderId(orderId);
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
	public Order insertOrder(Order order) {
		return orderRepository.save(order);
	}

	@Override
	@Transactional
	public Order updateOrder(Order order) {
		Order returnedOrder=orderRepository.save(order);
		if (order.getPlaceList()!=null)
		for (Place place: order.getPlaceList())
		{
			place.setOrder(order);
			placeRepository.save(place);
		}
		return returnedOrder;
	}

	@Override
	public List<Order> findLike(Order order) {
		return orderRepository.findByOrderIdAndNameAndTimeslotDate(order.getOrderId(), order.getName(), Utility.formatDate(order.getTimeslotDate()),null,null);
	}

}
