
package it.polimi.searchbean.mountain;

import java.util.List;
import it.polimi.model.mountain.Mountain;
import it.polimi.model.mountain.Photo;

public class SeedQuerySearchBean {

    public Long seedQueryId;
    public String seedKeyword;
    public Integer status;
    public Mountain mountain;
    public String mountainHeight;
    public List<Photo> photoList;

    public Long getSeedQueryId() {
        return this.seedQueryId;
    }

    public void setSeedQueryId(Long seedQueryId) {
        this.seedQueryId=seedQueryId;
    }

    public String getSeedKeyword() {
        return this.seedKeyword;
    }

    public void setSeedKeyword(String seedKeyword) {
        this.seedKeyword=seedKeyword;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status=status;
    }

    public Mountain getMountain() {
        return this.mountain;
    }

    public void setMountain(Mountain mountain) {
        this.mountain=mountain;
    }

    public String getMountainHeight() {
        return this.mountainHeight;
    }

    public void setMountainHeight(String mountainHeight) {
        this.mountainHeight=mountainHeight;
    }

    public List<Photo> getPhotoList() {
        return this.photoList;
    }

    public void setPhotoList(List<Photo> photoList) {
        this.photoList=photoList;
    }

}
