
package it.polimi.repository;

import java.util.List;
import it.polimi.model.Photo;
import it.polimi.model.SeedQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository
    extends CrudRepository<Photo, Long>
{


    public List<Photo> findByPhotoId(Long photoId);

    public List<Photo> findByUrl(String url);

    public List<Photo> findBySocial(String social);

    public List<Photo> findByDate(String date);

    public List<Photo> findByStatus(Integer status);

    public List<Photo> findBySocialId(String socialId);

    public List<Photo> findByRelatedPost(String relatedPost);

    public List<Photo> findBySeedQuery(SeedQuery seedQuery);

    @Query("select p from Photo p where  (:photoId is null or cast(:photoId as string)=cast(p.photoId as string)) and (:url is null or :url='' or cast(:url as string)=p.url) and (:social is null or :social='' or cast(:social as string)=p.social) and (:date is null or cast(:date as string)=cast(p.date as string)) and (:status is null or cast(:status as string)=cast(p.status as string)) and (:socialId is null or :socialId='' or cast(:socialId as string)=p.socialId) and (:relatedPost is null or :relatedPost='' or cast(:relatedPost as string)=p.relatedPost) and (:seedQuery=p.seedQuery or :seedQuery is null) ")
    public List<Photo> findByPhotoIdAndUrlAndSocialAndDateAndStatusAndSocialIdAndRelatedPostAndSeedQuery(
        @Param("photoId")
        Long photoId,
        @Param("url")
        String url,
        @Param("social")
        String social,
        @Param("date")
        String date,
        @Param("status")
        Integer status,
        @Param("socialId")
        String socialId,
        @Param("relatedPost")
        String relatedPost,
        @Param("seedQuery")
        SeedQuery seedQuery);

}
