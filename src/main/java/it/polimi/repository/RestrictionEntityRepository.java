
package it.polimi.repository;

import java.util.List;

import it.polimi.model.entity.Entity;
import it.polimi.model.security.RestrictionEntity;
import it.polimi.model.security.Role;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RestrictionEntityRepository
    extends CrudRepository<RestrictionEntity, Long>
{


    public List<RestrictionEntity> findByRestrictionEntityId(Long restrictionEntityId);

    public List<RestrictionEntity> findByCanCreate(Boolean canCreate);

    public List<RestrictionEntity> findByCanUpdate(Boolean canUpdate);

    public List<RestrictionEntity> findByCanSearch(Boolean canSearch);

    public List<RestrictionEntity> findByCanDelete(Boolean canDelete);

    public List<RestrictionEntity> findByRole(Role role);

    public List<RestrictionEntity> findByEntity(Entity entity);

    @Query("select r from RestrictionEntity r where  (:restrictionEntityId is null or cast(:restrictionEntityId as string)=cast(r.restrictionEntityId as string)) and (:canCreate is null or cast(:canCreate as string)=cast(r.canCreate as string)) and (:canUpdate is null or cast(:canUpdate as string)=cast(r.canUpdate as string)) and (:canSearch is null or cast(:canSearch as string)=cast(r.canSearch as string)) and (:canDelete is null or cast(:canDelete as string)=cast(r.canDelete as string)) and (:role=r.role or :role is null) and (:entity=r.entity or :entity is null) ")
    public List<RestrictionEntity> findByRestrictionEntityIdAndCanCreateAndCanUpdateAndCanSearchAndCanDeleteAndRoleAndEntity(
        @Param("restrictionEntityId")
        Long restrictionEntityId,
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
