
package it.polimi.service;

import java.util.List;
import it.polimi.model.Order;

public interface OrderService {


    public List<Order> findById(Long orderId);

    public List<Order> find(Order order);

    public void deleteById(Long orderId);

    public Order insert(Order order);

    public Order update(Order order);

}
