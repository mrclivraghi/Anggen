
package it.polimi.service.test;

import java.util.List;
import it.polimi.model.test.Order;
import it.polimi.searchbean.test.OrderSearchBean;

public interface OrderService {


    public List<Order> findById(Long orderId);

    public List<Order> find(OrderSearchBean order);

    public void deleteById(Long orderId);

    public Order insert(Order order);

    public Order update(Order order);

}
