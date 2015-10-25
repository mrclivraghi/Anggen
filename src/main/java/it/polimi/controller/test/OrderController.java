
package it.polimi.controller.test;

import java.util.List;
import it.polimi.model.test.Order;
import it.polimi.service.test.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final static Logger log = LoggerFactory.getLogger(Order.class);

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
        if (order.getOrderId()!=null)
         log.info("Searching order like {}",order.toString());
        orderList=orderService.find(order);
        getRightMapping(orderList);
         log.info("Search: returning {} order.",orderList.size());
        return ResponseEntity.ok().body(orderList);
    }

    @ResponseBody
    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
    public ResponseEntity getorderById(
        @PathVariable
        String orderId) {
        log.info("Searching order with id {}",orderId);
        List<Order> orderList=orderService.findById(java.lang.Long.valueOf(orderId));
        getRightMapping(orderList);
         log.info("Search: returning {} order.",orderList.size());
        return ResponseEntity.ok().body(orderList);
    }

    @ResponseBody
    @RequestMapping(value = "/{orderId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteorderById(
        @PathVariable
        String orderId) {
        log.info("Deleting order with id {}",orderId);
        orderService.deleteById(java.lang.Long.valueOf(orderId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertorder(
        @RequestBody
        Order order) {
        log.info("Inserting order like {}",order.toString());
        Order insertedorder=orderService.insert(order);
        getRightMapping(insertedorder);
        log.info("Inserted order with id {}",insertedorder.getOrderId());
        return ResponseEntity.ok().body(insertedorder);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateorder(
        @RequestBody
        Order order) {
        log.info("Updating order with id {}",order.getOrderId());
        Order updatedorder=orderService.update(order);
        getRightMapping(updatedorder);
        return ResponseEntity.ok().body(updatedorder);
    }

    private List<Order> getRightMapping(List<Order> orderList) {
        for (Order order: orderList)
        {
        getRightMapping(order);
        }
        return orderList;
    }

    private void getRightMapping(Order order) {
        if (order.getPerson()!=null)
        {
        }
        if (order.getPlaceList()!=null)
        for (it.polimi.model.test.Place place :order.getPlaceList())

        {

        place.setOrder(null);
        }
    }

}
