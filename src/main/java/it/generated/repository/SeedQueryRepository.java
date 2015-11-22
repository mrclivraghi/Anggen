
package it.generated.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeedQueryRepository
    extends CrudRepository<it.generated.domain.SeedQuery, java.lang.Integer>
{


    public List<it.generated.domain.SeedQuery> findBySeedQueryId(java.lang.Integer seedQueryId);

    public List<it.generated.domain.SeedQuery> findByName(java.lang.String name);

    public List<it.generated.domain.SeedQuery> findByMountain(it.generated.domain.Mountain mountain);

    @Query("select s from SeedQuery s where  (:seedQueryId is null or cast(:seedQueryId as string)=cast(s.seedQueryId as string)) and (:name is null or :name='' or cast(:name as string)=s.name) and (:mountain=s.mountain or :mountain is null) ")
    public List<it.generated.domain.SeedQuery> findBySeedQueryIdAndNameAndMountain(
        @org.springframework.data.repository.query.Param("seedQueryId")
        java.lang.Integer seedQueryId,
        @org.springframework.data.repository.query.Param("name")
        java.lang.String name,
        @org.springframework.data.repository.query.Param("mountain")
        it.generated.domain.Mountain mountain);

}
