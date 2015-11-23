
package it.polimi.searchbean.mountain;

import java.util.List;
import it.polimi.model.mountain.SeedQuery;

public class MountainSearchBean {

    public Long mountainId;
    public String name;
    public String height;
    public List<SeedQuery> seedQueryList;
    public String seedQuerySeedKeyword;

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
        return this.seedQueryList;
    }

    public void setSeedQueryList(List<SeedQuery> seedQueryList) {
        this.seedQueryList=seedQueryList;
    }

    public String getSeedQuerySeedKeyword() {
        return this.seedQuerySeedKeyword;
    }

    public void setSeedQuerySeedKeyword(String seedQuerySeedKeyword) {
        this.seedQuerySeedKeyword=seedQuerySeedKeyword;
    }

}
