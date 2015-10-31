
package it.polimi.service.test;

import java.util.List;
import it.polimi.model.test.Order;
import it.polimi.repository.test.OrderRepository;
import it.polimi.repository.test.PlaceRepository;
import it.polimi.searchbean.test.OrderSearchBean;
import it.polimi.service.test.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl
    implements OrderService
{

    @org.springframework.beans.factory.annotation.Autowired
    public OrderRepository orderRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public PlaceRepository placeRepository;

    @Override
    public List<Order> findById(Long orderId) {
        return orderRepository.findByOrderId(orderId);
    }

    @Override
    public List<Order> find(OrderSearchBean order) {
        return orderRepository.findByOrderIdAndNameAndTimeslotDateAndPersonAndPlace(order.getOrderId(),order.getName(),it.polimi.utils.Utility.formatDate(order.getTimeslotDate()),order.getPerson(),order.getPlaceList()==null? null :order.getPlaceList().get(0));
    }

    @Override
    public void deleteById(Long orderId) {
        orderRepository.delete(orderId);
        return;
    }

    @Override
    public Order insert(Order order) {
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order update(Order order) {
        if (order.getPlaceList()!=null)
        for (it.polimi.model.test.Place place: order.getPlaceList())
        {
        place.setOrder(order);
        }
        Order returnedOrder=orderRepository.save(order);
         return returnedOrder;
    }

}
