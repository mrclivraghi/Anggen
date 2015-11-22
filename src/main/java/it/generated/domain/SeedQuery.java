
package it.generated.domain;

import javax.persistence.Entity;

@Entity
public class SeedQuery {

    private Double seedQueryId;
    private Double name;
    private it.generated.domain.Mountain mountain;

    public Double getSeedQueryId() {
        return this.seedQueryId;
    }

    public void setSeedQueryId(Double seedQueryId) {
        this.seedQueryId=seedQueryId;
    }

    public Double getName() {
        return this.name;
    }

    public void setName(Double name) {
        this.name=name;
    }

    public it.generated.domain.Mountain getMountain() {
        return this.mountain;
    }

    public void setMountain(it.generated.domain.Mountain mountain) {
        this.mountain=mountain;
    }

}
