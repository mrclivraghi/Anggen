
package it.generated.anggen.repository.field;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnumValueRepository
    extends CrudRepository<it.generated.anggen.model.field.EnumValue, java.lang.Long>
{


    public List<it.generated.anggen.model.field.EnumValue> findByName(java.lang.String name);

    public List<it.generated.anggen.model.field.EnumValue> findByValue(java.lang.Integer value);

    public List<it.generated.anggen.model.field.EnumValue> findByEnumValueId(java.lang.Long enumValueId);

    public List<it.generated.anggen.model.field.EnumValue> findByEnumField(it.generated.anggen.model.field.EnumField enumField);

    @Query("select e from EnumValue e where  (:name is null or :name='' or cast(:name as string)=e.name) and (:value is null or cast(:value as string)=cast(e.value as string)) and (:enumValueId is null or cast(:enumValueId as string)=cast(e.enumValueId as string)) and (:enumField=e.enumField or :enumField is null) ")
    public List<it.generated.anggen.model.field.EnumValue> findByNameAndValueAndEnumValueIdAndEnumField(
        @org.springframework.data.repository.query.Param("name")
        java.lang.String name,
        @org.springframework.data.repository.query.Param("value")
        java.lang.Integer value,
        @org.springframework.data.repository.query.Param("enumValueId")
        java.lang.Long enumValueId,
        @org.springframework.data.repository.query.Param("enumField")
        it.generated.anggen.model.field.EnumField enumField);

}
