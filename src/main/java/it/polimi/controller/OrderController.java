package it.polimi.controller;

import java.sql.Date;
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
	
	
	@RequestMapping(method=RequestMethod.GET)
	public String manage()
	{
		return "order";
	}
	
	@RequestMapping(value="/all",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getAll ()
	{
		return ResponseEntity.ok().body(orderService.findAll());
	}
	
	@RequestMapping(value="/search",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity search (@RequestBody Order order)
	{
		return ResponseEntity.ok().body(orderService.findLike(order));
	}
	
	@RequestMapping(value="/{orderId}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getOrderById(@PathVariable String orderId)
	{
		return ResponseEntity.ok().body(orderService.findByOrderId(Long.valueOf(orderId)));
	}
	
	@RequestMapping(value="/{orderId}",method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity deleteOrderById(@PathVariable String orderId)
	{
		orderService.deleteOrderById(Long.valueOf(orderId));
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity insertOrder(@RequestBody Order order)
	{
		orderService.insertOrder(order);
		return ResponseEntity.ok().body(order);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity updateOrder(@RequestBody Order order)
	{
		orderService.updateOrder(order);
		return ResponseEntity.ok().body(order);
	}
	
	
	
	
}
