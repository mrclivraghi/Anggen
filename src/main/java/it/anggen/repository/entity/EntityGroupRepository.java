
package it.anggen.repository.entity;

import java.util.List;
import it.anggen.model.entity.Entity;
import it.anggen.model.security.RestrictionEntityGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityGroupRepository
    extends JpaRepository<it.anggen.model.entity.EntityGroup, java.lang.Long>
{


    @Query("select e from EntityGroup e")
    public List<it.anggen.model.entity.EntityGroup> findAll();

    public List<it.anggen.model.entity.EntityGroup> findByEntityGroupId(java.lang.Long entityGroupId);

    public List<it.anggen.model.entity.EntityGroup> findByName(java.lang.String name);

    public List<it.anggen.model.entity.EntityGroup> findBySecurityType(java.lang.Integer securityType);

    public List<it.anggen.model.entity.EntityGroup> findByProject(it.anggen.model.entity.Project project);

    @Query("select e from EntityGroup e where  (:entityGroupId is null or cast(:entityGroupId as string)=cast(e.entityGroupId as string)) and (:name is null or :name='' or cast(:name as string)=e.name) and (:securityType is null or cast(:securityType as string)=cast(e.securityType as string)) and (:project=e.project or :project is null) and (:restrictionEntityGroup in elements(e.restrictionEntityGroupList)  or :restrictionEntityGroup is null) and (:entity in elements(e.entityList)  or :entity is null) ")
    public List<it.anggen.model.entity.EntityGroup> findByEntityGroupIdAndNameAndSecurityTypeAndProjectAndRestrictionEntityGroupAndEntity(
        @org.springframework.data.repository.query.Param("entityGroupId")
        java.lang.Long entityGroupId,
        @org.springframework.data.repository.query.Param("name")
        java.lang.String name,
        @org.springframework.data.repository.query.Param("securityType")
        java.lang.Integer securityType,
        @org.springframework.data.repository.query.Param("project")
        it.anggen.model.entity.Project project,
        @org.springframework.data.repository.query.Param("restrictionEntityGroup")
        RestrictionEntityGroup restrictionEntityGroup,
        @org.springframework.data.repository.query.Param("entity")
        Entity entity);

}
