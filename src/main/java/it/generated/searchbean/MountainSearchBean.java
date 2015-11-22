
package it.generated.searchbean;

import java.util.List;
import it.generated.domain.SeedQuery;

public class MountainSearchBean {

    public java.lang.Integer mountainId;
    public java.lang.String name;
    public List<SeedQuery> seedQueryList;

    public java.lang.Integer getMountainId() {
        return this.mountainId;
    }

    public void setMountainId(java.lang.Integer mountainId) {
        this.mountainId=mountainId;
    }

    public java.lang.String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.name=name;
    }

    public List<SeedQuery> getSeedQueryList() {
        return this.seedQueryList;
    }

    public void setSeedQueryList(List<SeedQuery> seedQueryList) {
        this.seedQueryList=seedQueryList;
    }

}
