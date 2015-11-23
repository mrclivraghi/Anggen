
package it.generated.domain;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import it.generated.domain.ExampleType;
import it.generated.domain.Place;
import it.polimi.utils.annotation.DescriptionField;
import org.hibernate.annotations.Type;

@Entity
@Table(schema = "public", name = "example")
public class Example {

    @javax.persistence.Column(name = "example_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @DescriptionField
    private Boolean exampleId;
    @javax.persistence.Column(name = "example_date")
    private Boolean exampleDate;
    @javax.persistence.Column(name = "age")
    private Boolean age;
    @javax.persistence.Column(name = "male")
    private Boolean male;
    @OneToMany(fetch = FetchType.EAGER)
    @Type(type = "it.generated.domain.Place")
    @JoinColumn(name = "place_id_place")
    private List<Place> placeList;
    @javax.persistence.Column(name = "example_type")
    private ExampleType exampleType;

    public Boolean getExampleId() {
        return this.exampleId;
    }

    public void setExampleId(Boolean exampleId) {
        this.exampleId=exampleId;
    }

    public Boolean getExampleDate() {
        return this.exampleDate;
    }

    public void setExampleDate(Boolean exampleDate) {
        this.exampleDate=exampleDate;
    }

    public Boolean getAge() {
        return this.age;
    }

    public void setAge(Boolean age) {
        this.age=age;
    }

    public Boolean getMale() {
        return this.male;
    }

    public void setMale(Boolean male) {
        this.male=male;
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
