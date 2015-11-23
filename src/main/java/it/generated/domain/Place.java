
package it.generated.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import it.polimi.utils.annotation.DescriptionField;
import it.polimi.utils.annotation.Filter;

@Entity
@Table(schema = "public", name = "place")
public class Place {

    @javax.persistence.Column(name = "place_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @DescriptionField
    private Integer placeId;
    @javax.persistence.Column(name = "place_name")
    @Filter
    @Size(min = 2, max = 10)
    private String placeName;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "example_id_example")
    private it.generated.domain.Example example;

    public Integer getPlaceId() {
        return this.placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId=placeId;
    }

    public String getPlaceName() {
        return this.placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName=placeName;
    }

    public it.generated.domain.Example getExample() {
        return this.example;
    }

    public void setExample(it.generated.domain.Example example) {
        this.example=example;
    }

}
