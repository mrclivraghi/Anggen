
package it.polimi.repository;

import java.util.List;
import it.polimi.model.domain.EntityGroup;
import it.polimi.model.domain.RestrictionEntityGroup;
import it.polimi.model.domain.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RestrictionEntityGroupRepository
    extends CrudRepository<RestrictionEntityGroup, Long>
{


    public List<RestrictionEntityGroup> findByRestrictionEntityGroupId(Long restrictionEntityGroupId);

    public List<RestrictionEntityGroup> findByCanCreate(Boolean canCreate);

    public List<RestrictionEntityGroup> findByCanUpdate(Boolean canUpdate);

    public List<RestrictionEntityGroup> findByCanSearch(Boolean canSearch);

    public List<RestrictionEntityGroup> findByCanDelete(Boolean canDelete);

    public List<RestrictionEntityGroup> findByEntityGroup(EntityGroup entityGroup);

    public List<RestrictionEntityGroup> findByRole(Role role);

    @Query("select r from RestrictionEntityGroup r where  (:restrictionEntityGroupId is null or cast(:restrictionEntityGroupId as string)=cast(r.restrictionEntityGroupId as string)) and (:canCreate is null or cast(:canCreate as string)=cast(r.canCreate as string)) and (:canUpdate is null or cast(:canUpdate as string)=cast(r.canUpdate as string)) and (:canSearch is null or cast(:canSearch as string)=cast(r.canSearch as string)) and (:canDelete is null or cast(:canDelete as string)=cast(r.canDelete as string)) and (:entityGroup=r.entityGroup or :entityGroup is null) and (:role=r.role or :role is null) ")
    public List<RestrictionEntityGroup> findByRestrictionEntityGroupIdAndCanCreateAndCanUpdateAndCanSearchAndCanDeleteAndEntityGroupAndRole(
        @Param("restrictionEntityGroupId")
        Long restrictionEntityGroupId,
        @Param("canCreate")
        Boolean canCreate,
        @Param("canUpdate")
        Boolean canUpdate,
        @Param("canSearch")
        Boolean canSearch,
        @Param("canDelete")
        Boolean canDelete,
        @Param("entityGroup")
        EntityGroup entityGroup,
        @Param("role")
        Role role);

}
