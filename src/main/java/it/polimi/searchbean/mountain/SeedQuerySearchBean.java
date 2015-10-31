
package it.polimi.searchbean.mountain;

import java.util.List;
import it.polimi.model.mountain.Mountain;
import it.polimi.model.mountain.Photo;

public class SeedQuerySearchBean {

    public Long seedQueryId;
    public String seedKeyword;
    public Integer status;
    public Mountain mountain;
    public List<Photo> photo;

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

    public List<Photo> getPhotoList() {
        return this.photo;
    }

    public void setPhotoList(List<Photo> photo) {
        this.photo=photo;
    }

}
