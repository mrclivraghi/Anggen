
package it.polimi.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import it.polimi.domain.ExampleType;
import it.polimi.domain.Place;
import it.polimi.utils.annotation.DescriptionField;
import org.hibernate.annotations.Type;

@Entity
@Table(schema = "public", name = "example")
public class Example {

    public final static Long entityId = 80L;
    @javax.persistence.Column(name = "male")
    private Boolean male;
    @javax.persistence.Column(name = "age")
    private java.lang.Integer age;
    @javax.persistence.Column(name = "example_date")
    private Date exampleDate;
    @javax.persistence.Column(name = "example_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @DescriptionField
    private java.lang.Integer exampleId;
    @OneToMany(fetch = FetchType.EAGER)
    @Type(type = "it.polimi.domain.Place")
    @JoinColumn(name = "place_id_place")
    private List<Place> placeList;
    @javax.persistence.Column(name = "example_type")
    private ExampleType exampleType;

    public Boolean getMale() {
        return this.male;
    }

    public void setMale(Boolean male) {
        this.male=male;
    }

    public java.lang.Integer getAge() {
        return this.age;
    }

    public void setAge(java.lang.Integer age) {
        this.age=age;
    }

    public Date getExampleDate() {
        return this.exampleDate;
    }

    public void setExampleDate(Date exampleDate) {
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

    public ExampleType getExampleType() {
        return this.exampleType;
    }

    public void setExampleType(ExampleType exampleType) {
        this.exampleType=exampleType;
    }

}
