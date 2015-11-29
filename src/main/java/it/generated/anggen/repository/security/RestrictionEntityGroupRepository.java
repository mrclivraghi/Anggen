
package it.generated.anggen.repository.security;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestrictionEntityGroupRepository
    extends CrudRepository<it.generated.anggen.model.security.RestrictionEntityGroup, java.lang.Long>
{


    public List<it.generated.anggen.model.security.RestrictionEntityGroup> findByRestrictionEntityGroupId(java.lang.Long restrictionEntityGroupId);

    public List<it.generated.anggen.model.security.RestrictionEntityGroup> findByCanCreate(java.lang.Boolean canCreate);

    public List<it.generated.anggen.model.security.RestrictionEntityGroup> findByCanUpdate(java.lang.Boolean canUpdate);

    public List<it.generated.anggen.model.security.RestrictionEntityGroup> findByCanSearch(java.lang.Boolean canSearch);

    public List<it.generated.anggen.model.security.RestrictionEntityGroup> findByCanDelete(java.lang.Boolean canDelete);

    public List<it.generated.anggen.model.security.RestrictionEntityGroup> findByEntityGroup(it.generated.anggen.model.entity.EntityGroup entityGroup);

    public List<it.generated.anggen.model.security.RestrictionEntityGroup> findByRole(it.generated.anggen.model.security.Role role);

    @Query("select r from RestrictionEntityGroup r where  (:restrictionEntityGroupId is null or cast(:restrictionEntityGroupId as string)=cast(r.restrictionEntityGroupId as string)) and (:canCreate is null or cast(:canCreate as string)=cast(r.canCreate as string)) and (:canUpdate is null or cast(:canUpdate as string)=cast(r.canUpdate as string)) and (:canSearch is null or cast(:canSearch as string)=cast(r.canSearch as string)) and (:canDelete is null or cast(:canDelete as string)=cast(r.canDelete as string)) and (:entityGroup=r.entityGroup or :entityGroup is null) and (:role=r.role or :role is null) ")
    public List<it.generated.anggen.model.security.RestrictionEntityGroup> findByRestrictionEntityGroupIdAndCanCreateAndCanUpdateAndCanSearchAndCanDeleteAndEntityGroupAndRole(
        @org.springframework.data.repository.query.Param("restrictionEntityGroupId")
        java.lang.Long restrictionEntityGroupId,
        @org.springframework.data.repository.query.Param("canCreate")
        java.lang.Boolean canCreate,
        @org.springframework.data.repository.query.Param("canUpdate")
        java.lang.Boolean canUpdate,
        @org.springframework.data.repository.query.Param("canSearch")
        java.lang.Boolean canSearch,
        @org.springframework.data.repository.query.Param("canDelete")
        java.lang.Boolean canDelete,
        @org.springframework.data.repository.query.Param("entityGroup")
        it.generated.anggen.model.entity.EntityGroup entityGroup,
        @org.springframework.data.repository.query.Param("role")
        it.generated.anggen.model.security.Role role);

}
