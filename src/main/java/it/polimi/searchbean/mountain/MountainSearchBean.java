
package it.polimi.searchbean.mountain;

import java.util.List;
import it.polimi.model.mountain.SeedQuery;

public class MountainSearchBean {

    public Long mountainId;
    public String name;
    public String height;
    public List<SeedQuery> seedQuery;

    public Long getMountainId() {
        return this.mountainId;
    }

    public void setMountainId(Long mountainId) {
        this.mountainId=mountainId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getHeight() {
        return this.height;
    }

    public void setHeight(String height) {
        this.height=height;
    }

    public List<SeedQuery> getSeedQueryList() {
        return this.seedQuery;
    }

    public void setSeedQueryList(List<SeedQuery> seedQuery) {
        this.seedQuery=seedQuery;
    }

}
