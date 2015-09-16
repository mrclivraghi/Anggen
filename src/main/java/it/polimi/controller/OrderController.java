package it.polimi.controller;

import java.util.List;

import javax.xml.ws.Response;

import it.polimi.model.Order;
import it.polimi.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/order")

public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@RequestMapping(value="/all",method=RequestMethod.GET)
	@ResponseBody
	public List<Order> hello ()
	{
		return orderService.findAll();
	}
	
	@RequestMapping(value="/{orderId}",method=RequestMethod.GET)
	@ResponseBody
	public Order getOrderById(@PathVariable String orderId)
	{
		return orderService.findByOrderId(Long.valueOf(orderId));
	}
	
	@RequestMapping(value="/{orderId}",method=RequestMethod.DELETE)
	@ResponseBody
	public String deleteOrderById(@PathVariable String orderId)
	{
		orderService.deleteOrderById(Long.valueOf(orderId));
		return "deleted";
	}
	
	@RequestMapping(value="/insert",method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity insertOrder(@RequestBody Order order)
	{
		orderService.insertOrder(order);
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public Order updateOrder(@RequestBody Order order)
	{
		orderService.updateOrder(order);
		return order;
	}
	
	
	
	
}
