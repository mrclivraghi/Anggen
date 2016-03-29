
package it.anggen.repository.security;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestrictionEntityRepository
    extends JpaRepository<it.anggen.model.security.RestrictionEntity, java.lang.Long>
{


    @Query("select r from RestrictionEntity r")
    public List<it.anggen.model.security.RestrictionEntity> findAll();

    public List<it.anggen.model.security.RestrictionEntity> findByRestrictionEntityId(java.lang.Long restrictionEntityId);

    public List<it.anggen.model.security.RestrictionEntity> findByCanCreate(java.lang.Boolean canCreate);

    public List<it.anggen.model.security.RestrictionEntity> findByCanSearch(java.lang.Boolean canSearch);

    public List<it.anggen.model.security.RestrictionEntity> findByCanDelete(java.lang.Boolean canDelete);

    public List<it.anggen.model.security.RestrictionEntity> findByCanUpdate(java.lang.Boolean canUpdate);

    public List<it.anggen.model.security.RestrictionEntity> findByEntity(it.anggen.model.entity.Entity entity);

    public List<it.anggen.model.security.RestrictionEntity> findByRole(it.anggen.model.security.Role role);

    @Query("select r from RestrictionEntity r where  (:restrictionEntityId is null or cast(:restrictionEntityId as string)=cast(r.restrictionEntityId as string)) and (:canCreate is null or cast(:canCreate as string)=cast(r.canCreate as string)) and (:canSearch is null or cast(:canSearch as string)=cast(r.canSearch as string)) and (:canDelete is null or cast(:canDelete as string)=cast(r.canDelete as string)) and (:canUpdate is null or cast(:canUpdate as string)=cast(r.canUpdate as string)) and (:entity=r.entity or :entity is null) and (:role=r.role or :role is null) ")
    public List<it.anggen.model.security.RestrictionEntity> findByRestrictionEntityIdAndCanCreateAndCanSearchAndCanDeleteAndCanUpdateAndEntityAndRole(
        @org.springframework.data.repository.query.Param("restrictionEntityId")
        java.lang.Long restrictionEntityId,
        @org.springframework.data.repository.query.Param("canCreate")
        java.lang.Boolean canCreate,
        @org.springframework.data.repository.query.Param("canSearch")
        java.lang.Boolean canSearch,
        @org.springframework.data.repository.query.Param("canDelete")
        java.lang.Boolean canDelete,
        @org.springframework.data.repository.query.Param("canUpdate")
        java.lang.Boolean canUpdate,
        @org.springframework.data.repository.query.Param("entity")
        it.anggen.model.entity.Entity entity,
        @org.springframework.data.repository.query.Param("role")
        it.anggen.model.security.Role role);

}
