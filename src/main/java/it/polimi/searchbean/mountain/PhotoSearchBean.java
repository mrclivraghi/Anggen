
package it.polimi.searchbean.mountain;

import java.util.Date;
import it.polimi.model.mountain.SeedQuery;

public class PhotoSearchBean {

    public Long photoId;
    public String url;
    public String social;
    public Date date;
    public Integer status;
    public String socialId;
    public String relatedPost;
    public SeedQuery seedQuery;
    public String seedQuerySeedKeyword;

    public Long getPhotoId() {
        return this.photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId=photoId;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url=url;
    }

    public String getSocial() {
        return this.social;
    }

    public void setSocial(String social) {
        this.social=social;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date=date;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status=status;
    }

    public String getSocialId() {
        return this.socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId=socialId;
    }

    public String getRelatedPost() {
        return this.relatedPost;
    }

    public void setRelatedPost(String relatedPost) {
        this.relatedPost=relatedPost;
    }

    public SeedQuery getSeedQuery() {
        return this.seedQuery;
    }

    public void setSeedQuery(SeedQuery seedQuery) {
        this.seedQuery=seedQuery;
    }

    public String getSeedQuerySeedKeyword() {
        return this.seedQuerySeedKeyword;
    }

    public void setSeedQuerySeedKeyword(String seedQuerySeedKeyword) {
        this.seedQuerySeedKeyword=seedQuerySeedKeyword;
    }

}
