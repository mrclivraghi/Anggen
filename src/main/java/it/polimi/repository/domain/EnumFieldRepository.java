
package it.polimi.repository.domain;

import java.util.List;
import it.polimi.model.domain.Annotation;
import it.polimi.model.domain.Entity;
import it.polimi.model.domain.EnumField;
import it.polimi.model.domain.EnumValue;
import it.polimi.model.domain.Tab;
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

    public List<EnumField> findByTab(Tab tab);

    @Query("select e from EnumField e where  (:enumFieldId is null or cast(:enumFieldId as string)=cast(e.enumFieldId as string)) and (:name is null or :name='' or cast(:name as string)=e.name) and (:enumValue in elements(e.enumValueList)  or :enumValue is null) and (:entity=e.entity or :entity is null) and (:annotation in elements(e.annotationList)  or :annotation is null) and (:tab=e.tab or :tab is null) ")
    public List<EnumField> findByEnumFieldIdAndNameAndEnumValueAndEntityAndAnnotationAndTab(
        @Param("enumFieldId")
        Long enumFieldId,
        @Param("name")
        String name,
        @Param("enumValue")
        EnumValue enumValue,
        @Param("entity")
        Entity entity,
        @Param("annotation")
        Annotation annotation,
        @Param("tab")
        Tab tab);

}
