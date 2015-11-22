
package it.generated.repository;

import java.util.List;
import it.generated.domain.SeedQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MountainRepository
    extends CrudRepository<it.generated.domain.Mountain, java.lang.Integer>
{


    public List<it.generated.domain.Mountain> findByMountainId(java.lang.Integer mountainId);

    public List<it.generated.domain.Mountain> findByName(java.lang.String name);

    @Query("select m from Mountain m where  (:mountainId is null or cast(:mountainId as string)=cast(m.mountainId as string)) and (:name is null or :name='' or cast(:name as string)=m.name) and (:seedQuery in elements(m.seedQueryList)  or :seedQuery is null) ")
    public List<it.generated.domain.Mountain> findByMountainIdAndNameAndSeedQuery(
        @org.springframework.data.repository.query.Param("mountainId")
        java.lang.Integer mountainId,
        @org.springframework.data.repository.query.Param("name")
        java.lang.String name,
        @org.springframework.data.repository.query.Param("seedQuery")
        SeedQuery seedQuery);

}
