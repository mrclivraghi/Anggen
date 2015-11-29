
package it.polimi.repository;

import java.util.List;

import it.polimi.model.entity.Entity;
import it.polimi.model.entity.EntityGroup;
import it.polimi.model.security.RestrictionEntityGroup;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityGroupRepository
    extends CrudRepository<EntityGroup, Long>
{


    public List<EntityGroup> findByEntityGroupId(Long entityGroupId);

    public List<EntityGroup> findByName(String name);

    @Query("select e from EntityGroup e where  (:entityGroupId is null or cast(:entityGroupId as string)=cast(e.entityGroupId as string)) and (:name is null or :name='' or cast(:name as string)=e.name) and (:entity in elements(e.entityList)  or :entity is null) and (:restrictionEntityGroup in elements(e.restrictionEntityGroupList)  or :restrictionEntityGroup is null) ")
    public List<EntityGroup> findByEntityGroupIdAndNameAndEntityAndRestrictionEntityGroup(
        @Param("entityGroupId")
        Long entityGroupId,
        @Param("name")
        String name,
        @Param("entity")
        Entity entity,
        @Param("restrictionEntityGroup")
        RestrictionEntityGroup restrictionEntityGroup);

}
