
package it.anggen.repository.entity;

import java.util.List;
import it.anggen.model.entity.Entity;
import it.anggen.model.security.RestrictionEntityGroup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityGroupRepository
    extends CrudRepository<it.anggen.model.entity.EntityGroup, java.lang.Long>
{


    public List<it.anggen.model.entity.EntityGroup> findByEntityGroupId(java.lang.Long entityGroupId);

    public List<it.anggen.model.entity.EntityGroup> findByEntityId(java.lang.Long entityId);

    public List<it.anggen.model.entity.EntityGroup> findByName(java.lang.String name);

    public List<it.anggen.model.entity.EntityGroup> findByProject(it.anggen.model.entity.Project project);

    @Query("select e from EntityGroup e where  (:entityGroupId is null or cast(:entityGroupId as string)=cast(e.entityGroupId as string)) and (:entityId is null or cast(:entityId as string)=cast(e.entityId as string)) and (:name is null or :name='' or cast(:name as string)=e.name) and (:entity in elements(e.entityList)  or :entity is null) and (:restrictionEntityGroup in elements(e.restrictionEntityGroupList)  or :restrictionEntityGroup is null) and (:project=e.project or :project is null) ")
    public List<it.anggen.model.entity.EntityGroup> findByEntityGroupIdAndEntityIdAndNameAndEntityAndRestrictionEntityGroupAndProject(
        @org.springframework.data.repository.query.Param("entityGroupId")
        java.lang.Long entityGroupId,
        @org.springframework.data.repository.query.Param("entityId")
        java.lang.Long entityId,
        @org.springframework.data.repository.query.Param("name")
        java.lang.String name,
        @org.springframework.data.repository.query.Param("entity")
        Entity entity,
        @org.springframework.data.repository.query.Param("restrictionEntityGroup")
        RestrictionEntityGroup restrictionEntityGroup,
        @org.springframework.data.repository.query.Param("project")
        it.anggen.model.entity.Project project);

}