
package it.polimi.repository;

import java.util.List;
import it.polimi.model.domain.Entity;
import it.polimi.model.domain.Restriction;
import it.polimi.model.domain.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RestrictionRepository
    extends CrudRepository<Restriction, Long>
{


    public List<Restriction> findByRestrictionId(Long restrictionId);

    public List<Restriction> findByCreate(Boolean create);

    public List<Restriction> findByUpdate(Boolean update);

    public List<Restriction> findBySearch(Boolean search);

    public List<Restriction> findByDelete(Boolean delete);

    public List<Restriction> findByRole(Role role);

    public List<Restriction> findByEntity(Entity entity);

    @Query("select r from Restriction r where  (:restrictionId is null or cast(:restrictionId as string)=cast(r.restrictionId as string)) and (:create is null or cast(:create as string)=cast(r.create as string)) and (:update is null or cast(:update as string)=cast(r.update as string)) and (:search is null or cast(:search as string)=cast(r.search as string)) and (:delete is null or cast(:delete as string)=cast(r.delete as string)) and (:role=r.role or :role is null) and (:entity=r.entity or :entity is null) ")
    public List<Restriction> findByRestrictionIdAndCreateAndUpdateAndSearchAndDeleteAndRoleAndEntity(
        @Param("restrictionId")
        Long restrictionId,
        @Param("create")
        Boolean create,
        @Param("update")
        Boolean update,
        @Param("search")
        Boolean search,
        @Param("delete")
        Boolean delete,
        @Param("role")
        Role role,
        @Param("entity")
        Entity entity);

}
