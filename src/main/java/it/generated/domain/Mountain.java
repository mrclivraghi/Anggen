
package it.generated.domain;

import java.util.List;
import javax.persistence.Entity;
import it.generated.domain.SeedQuery;

@Entity
public class Mountain {

    private Double mountainId;
    private Double name;
    private List<SeedQuery> seedQueryList;

    public Double getMountainId() {
        return this.mountainId;
    }

    public void setMountainId(Double mountainId) {
        this.mountainId=mountainId;
    }

    public Double getName() {
        return this.name;
    }

    public void setName(Double name) {
        this.name=name;
    }

    public List<SeedQuery> getSeedQueryList() {
        return this.seedQueryList;
    }

    public void setSeedQueryList(List<SeedQuery> seedQueryList) {
        this.seedQueryList=seedQueryList;
    }

}
