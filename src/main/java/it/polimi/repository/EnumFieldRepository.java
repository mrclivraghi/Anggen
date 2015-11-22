
package it.polimi.repository;

import it.polimi.model.domain.Entity;
import it.polimi.model.domain.EnumField;
import it.polimi.model.domain.EnumValue;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EnumFieldRepository
    extends CrudRepository<EnumField, Long>
{


    public List<EnumField> findByEnumFieldId(Long enumFieldId);

    public List<EnumField> findByName(String name);

    public List<EnumField> findByEntity(Entity entity);

    @Query("select e from EnumField e where  (:enumFieldId is null or cast(:enumFieldId as string)=cast(e.enumFieldId as string)) and (:name is null or :name='' or cast(:name as string)=e.name) and (:enumValue in elements(e.enumValueList)  or :enumValue is null) and (:entity=e.entity or :entity is null) ")
    public List<EnumField> findByEnumFieldIdAndNameAndEnumValueAndEntity(
        @Param("enumFieldId")
        Long enumFieldId,
        @Param("name")
        String name,
        @Param("enumValue")
        EnumValue enumValue,
        @Param("entity")
        Entity entity);

}
