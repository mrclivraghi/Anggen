
package it.generated.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import it.polimi.utils.annotation.DescriptionField;
import it.polimi.utils.annotation.Filter;

@Entity
@Table(schema = "public", name = "place")
public class Place {

    @Column(name = "place_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @DescriptionField
    private Boolean placeId;
    @Column(name = "place_name")
    @Filter
    private Boolean placeName;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "example_id_example")
    private it.generated.domain.Example example;

    public Boolean getPlaceId() {
        return this.placeId;
    }

    public void setPlaceId(Boolean placeId) {
        this.placeId=placeId;
    }

    public Boolean getPlaceName() {
        return this.placeName;
    }

    public void setPlaceName(Boolean placeName) {
        this.placeName=placeName;
    }

    public it.generated.domain.Example getExample() {
        return this.example;
    }

    public void setExample(it.generated.domain.Example example) {
        this.example=example;
    }

}
