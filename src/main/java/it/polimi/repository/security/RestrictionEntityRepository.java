
package it.polimi.repository.security;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestrictionEntityRepository
    extends CrudRepository<it.polimi.model.security.RestrictionEntity, java.lang.Long>
{


    public List<it.polimi.model.security.RestrictionEntity> findByRestrictionEntityId(java.lang.Long restrictionEntityId);

    public List<it.polimi.model.security.RestrictionEntity> findByCanCreate(java.lang.Boolean canCreate);

    public List<it.polimi.model.security.RestrictionEntity> findByCanUpdate(java.lang.Boolean canUpdate);

    public List<it.polimi.model.security.RestrictionEntity> findByCanSearch(java.lang.Boolean canSearch);

    public List<it.polimi.model.security.RestrictionEntity> findByCanDelete(java.lang.Boolean canDelete);

    public List<it.polimi.model.security.RestrictionEntity> findByRole(it.polimi.model.security.Role role);

    public List<it.polimi.model.security.RestrictionEntity> findByEntity(it.polimi.model.entity.Entity entity);

    @Query("select r from RestrictionEntity r where  (:restrictionEntityId is null or cast(:restrictionEntityId as string)=cast(r.restrictionEntityId as string)) and (:canCreate is null or cast(:canCreate as string)=cast(r.canCreate as string)) and (:canUpdate is null or cast(:canUpdate as string)=cast(r.canUpdate as string)) and (:canSearch is null or cast(:canSearch as string)=cast(r.canSearch as string)) and (:canDelete is null or cast(:canDelete as string)=cast(r.canDelete as string)) and (:role=r.role or :role is null) and (:entity=r.entity or :entity is null) ")
    public List<it.polimi.model.security.RestrictionEntity> findByRestrictionEntityIdAndCanCreateAndCanUpdateAndCanSearchAndCanDeleteAndRoleAndEntity(
        @org.springframework.data.repository.query.Param("restrictionEntityId")
        java.lang.Long restrictionEntityId,
        @org.springframework.data.repository.query.Param("canCreate")
        java.lang.Boolean canCreate,
        @org.springframework.data.repository.query.Param("canUpdate")
        java.lang.Boolean canUpdate,
        @org.springframework.data.repository.query.Param("canSearch")
        java.lang.Boolean canSearch,
        @org.springframework.data.repository.query.Param("canDelete")
        java.lang.Boolean canDelete,
        @org.springframework.data.repository.query.Param("role")
        it.polimi.model.security.Role role,
        @org.springframework.data.repository.query.Param("entity")
        it.polimi.model.entity.Entity entity);

}
