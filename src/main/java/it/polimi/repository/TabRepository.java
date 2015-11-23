
package it.polimi.repository;

import java.util.List;
import it.polimi.model.domain.Entity;
import it.polimi.model.domain.EnumField;
import it.polimi.model.domain.Field;
import it.polimi.model.domain.Relationship;
import it.polimi.model.domain.Tab;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TabRepository
    extends CrudRepository<Tab, Long>
{


    public List<Tab> findByTabId(Long tabId);

    public List<Tab> findByName(String name);

    public List<Tab> findByEntity(Entity entity);

    @Query("select t from Tab t where  (:tabId is null or cast(:tabId as string)=cast(t.tabId as string)) and (:name is null or :name='' or cast(:name as string)=t.name) and (:entity=t.entity or :entity is null) and (:field in elements(t.fieldList)  or :field is null) and (:relationship in elements(t.relationshipList)  or :relationship is null) and (:enumField in elements(t.enumFieldList)  or :enumField is null) ")
    public List<Tab> findByTabIdAndNameAndEntityAndFieldAndRelationshipAndEnumField(
        @Param("tabId")
        Long tabId,
        @Param("name")
        String name,
        @Param("entity")
        Entity entity,
        @Param("field")
        Field field,
        @Param("relationship")
        Relationship relationship,
        @Param("enumField")
        EnumField enumField);

}
