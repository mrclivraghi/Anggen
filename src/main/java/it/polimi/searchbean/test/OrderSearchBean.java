
package it.polimi.searchbean.test;

import java.sql.Date;
import java.util.List;
import it.polimi.model.test.Person;
import it.polimi.model.test.Place;

public class OrderSearchBean {

    public Long orderId;
    public String name;
    public Date timeslotDate;
    public Person person;
    public List<Place> place;

    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId=orderId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public Date getTimeslotDate() {
        return this.timeslotDate;
    }

    public void setTimeslotDate(Date timeslotDate) {
        this.timeslotDate=timeslotDate;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person=person;
    }

    public List<Place> getPlaceList() {
        return this.place;
    }

    public void setPlaceList(List<Place> place) {
        this.place=place;
    }

}
