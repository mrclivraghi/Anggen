
package it.polimi.repository.domain;

import java.util.List;
import it.polimi.model.domain.Entity;
import it.polimi.model.domain.EnumField;
import it.polimi.model.domain.Field;
import it.polimi.model.domain.Relationship;
import it.polimi.model.domain.Restriction;
import it.polimi.model.domain.Tab;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityRepository
    extends CrudRepository<Entity, Long>
{


    public List<Entity> findByEntityId(Long entityId);

    public List<Entity> findByName(String name);

    @Query("select e from Entity e where  (:entityId is null or cast(:entityId as string)=cast(e.entityId as string)) and (:name is null or :name='' or cast(:name as string)=e.name) and (:field in elements(e.fieldList)  or :field is null) and (:relationship in elements(e.relationshipList)  or :relationship is null) and (:enumField in elements(e.enumFieldList)  or :enumField is null) and (:tab in elements(e.tabList)  or :tab is null) and (:restriction in elements(e.restrictionList)  or :restriction is null) ")
    public List<Entity> findByEntityIdAndNameAndFieldAndRelationshipAndEnumFieldAndTabAndRestriction(
        @Param("entityId")
        Long entityId,
        @Param("name")
        String name,
        @Param("field")
        Field field,
        @Param("relationship")
        Relationship relationship,
        @Param("enumField")
        EnumField enumField,
        @Param("tab")
        Tab tab,
        @Param("restriction")
        Restriction restriction);

}
