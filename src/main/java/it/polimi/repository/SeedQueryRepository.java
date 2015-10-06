
package it.polimi.repository;

import java.util.List;
import it.polimi.model.Mountain;
import it.polimi.model.Photo;
import it.polimi.model.SeedQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SeedQueryRepository
    extends CrudRepository<SeedQuery, Long>
{


    public List<SeedQuery> findBySeedQueryId(Long seedQueryId);

    public List<SeedQuery> findBySeedKeyword(String seedKeyword);

    public List<SeedQuery> findByStatus(Integer status);
    
    public List<SeedQuery> findByMountain(Mountain mountain);
    

    @Query("select s from SeedQuery s where  (:seedQueryId is null or cast(:seedQueryId as string)=cast(s.seedQueryId as string)) and (:seedKeyword is null or :seedKeyword='' or cast(:seedKeyword as string)=s.seedKeyword) and (:status is null or cast(:status as string)=cast(s.status as string)) and (:mountain=s.mountain or :mountain is null) and (:photo in elements(s.photoList)  or :photo is null) ")
    public List<SeedQuery> findBySeedQueryIdAndSeedKeywordAndStatusAndMountainAndPhoto(
        @Param("seedQueryId")
        Long seedQueryId,
        @Param("seedKeyword")
        String seedKeyword,
        @Param("status")
        Integer status,
        @Param("mountain")
        Mountain mountain,
        @Param("photo")
        Photo photo);

}
