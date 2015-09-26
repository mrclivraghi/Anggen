
package it.polimi.controller;

import java.util.List;
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
@RequestMapping("/order")
public class OrderController {

    @Autowired
    public OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "order";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @RequestBody
        Order order) {
        List<Order> orderList;
        orderList=orderService.find(order);
        return ResponseEntity.ok().body(orderList);
    }

    @ResponseBody
    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
    public ResponseEntity getorderById(
        @PathVariable
        String orderId) {
        return ResponseEntity.ok().body(orderService.findById(Long.valueOf(orderId)));
    }

    @ResponseBody
    @RequestMapping(value = "/{orderId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteorderById(
        @PathVariable
        String orderId) {
        orderService.deleteById(Long.valueOf(orderId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertorder(
        @RequestBody
        Order order) {
        Order insertedorder=orderService.insert(order);
        return ResponseEntity.ok().body(insertedorder);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateorder(
        @RequestBody
        Order order) {
        Order updatedorder=orderService.update(order);
        return ResponseEntity.ok().body(updatedorder);
    }

}
