
package it.polimi.repository;

import java.util.List;
import it.polimi.model.Mountain;
import it.polimi.model.SeedQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MountainRepository
    extends CrudRepository<Mountain, Long>
{


    public List<Mountain> findByMountainId(Long mountainId);

    public List<Mountain> findByName(String name);

    public List<Mountain> findByHeight(String height);

    @Query("select m from Mountain m where  (:mountainId is null or cast(:mountainId as string)=cast(m.mountainId as string)) and (:name is null or :name='' or cast(:name as string)=m.name) and (:height is null or :height='' or cast(:height as string)=m.height) and (:seedQuery in elements(m.seedQueryList)  or :seedQuery is null) ")
    public List<Mountain> findByMountainIdAndNameAndHeightAndSeedQuery(
        @Param("mountainId")
        Long mountainId,
        @Param("name")
        String name,
        @Param("height")
        String height,
        @Param("seedQuery")
        SeedQuery seedQuery);

}
