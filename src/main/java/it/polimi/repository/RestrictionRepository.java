
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

    public List<Restriction> findByCanCreate(Boolean canCreate);

    public List<Restriction> findByCanUpdate(Boolean canUpdate);

    public List<Restriction> findByCanSearch(Boolean canSearch);

    public List<Restriction> findByCanDelete(Boolean canDelete);

    public List<Restriction> findByRole(Role role);

    public List<Restriction> findByEntity(Entity entity);

    @Query("select r from Restriction r where  (:restrictionId is null or cast(:restrictionId as string)=cast(r.restrictionId as string)) and (:canCreate is null or cast(:canCreate as string)=cast(r.canCreate as string)) and (:canUpdate is null or cast(:canUpdate as string)=cast(r.canUpdate as string)) and (:canSearch is null or cast(:canSearch as string)=cast(r.canSearch as string)) and (:canDelete is null or cast(:canDelete as string)=cast(r.canDelete as string)) and (:role=r.role or :role is null) and (:entity=r.entity or :entity is null) ")
    public List<Restriction> findByRestrictionIdAndCanCreateAndCanUpdateAndCanSearchAndCanDeleteAndRoleAndEntity(
        @Param("restrictionId")
        Long restrictionId,
        @Param("canCreate")
        Boolean canCreate,
        @Param("canUpdate")
        Boolean canUpdate,
        @Param("canSearch")
        Boolean canSearch,
        @Param("canDelete")
        Boolean canDelete,
        @Param("role")
        Role role,
        @Param("entity")
        Entity entity);

}
