
package it.polimi.searchbean;

import java.util.List;
import it.polimi.domain.Place;

public class ExampleSearchBean {

    public java.lang.Boolean male;
    public java.lang.Integer age;
    public java.util.Date exampleDate;
    public java.lang.Integer exampleId;
    public List<Place> placeList;
    public it.polimi.domain.ExampleType exampleType;
    public java.lang.String placePlaceName;

    public java.lang.Boolean getMale() {
        return this.male;
    }

    public void setMale(java.lang.Boolean male) {
        this.male=male;
    }

    public java.lang.Integer getAge() {
        return this.age;
    }

    public void setAge(java.lang.Integer age) {
        this.age=age;
    }

    public java.util.Date getExampleDate() {
        return this.exampleDate;
    }

    public void setExampleDate(java.util.Date exampleDate) {
        this.exampleDate=exampleDate;
    }

    public java.lang.Integer getExampleId() {
        return this.exampleId;
    }

    public void setExampleId(java.lang.Integer exampleId) {
        this.exampleId=exampleId;
    }

    public List<Place> getPlaceList() {
        return this.placeList;
    }

    public void setPlaceList(List<Place> placeList) {
        this.placeList=placeList;
    }

    public it.polimi.domain.ExampleType getExampleType() {
        return this.exampleType;
    }

    public void setExampleType(it.polimi.domain.ExampleType exampleType) {
        this.exampleType=exampleType;
    }

    public java.lang.String getPlacePlaceName() {
        return this.placePlaceName;
    }

    public void setPlacePlaceName(java.lang.String placePlaceName) {
        this.placePlaceName=placePlaceName;
    }

}