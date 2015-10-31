
package it.polimi.searchbean.test;

import it.polimi.model.test.Order;

public class PlaceSearchBean {

    public Long placeId;
    public String description;
    public Order order;

    public Long getPlaceId() {
        return this.placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId=placeId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order=order;
    }

}
