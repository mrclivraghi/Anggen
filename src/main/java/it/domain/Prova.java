
package it.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import it.anggen.utils.annotation.DescriptionField;
import it.anggen.utils.annotation.Embedded;
import it.anggen.utils.annotation.GenerateFrontEnd;
import it.anggen.utils.annotation.MaxDescendantLevel;
import it.anggen.utils.annotation.Priority;

@Entity
@GenerateFrontEnd
public class Prova {

    @Id
    @Priority(6)
    private Integer provaId;
    
    private String name;

    @Embedded
    private String embed;
    
    private Date myDate;
    
    public Integer getProvaId() {
        return this.provaId;
    }

    public void setProvaId(Integer provaId) {
        this.provaId=provaId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

}
