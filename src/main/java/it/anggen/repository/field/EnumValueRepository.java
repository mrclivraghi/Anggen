
package it.anggen.repository.field;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EnumValueRepository
    extends JpaRepository<it.anggen.model.field.EnumValue, java.lang.Long>
{


    @Query("select e from EnumValue e")
    public List<it.anggen.model.field.EnumValue> findAll();

    public List<it.anggen.model.field.EnumValue> findByEnumValueId(java.lang.Long enumValueId);

    public List<it.anggen.model.field.EnumValue> findByName(java.lang.String name);

    public List<it.anggen.model.field.EnumValue> findByValue(java.lang.Integer value);

    public List<it.anggen.model.field.EnumValue> findByEnumEntity(it.anggen.model.entity.EnumEntity enumEntity);

    @Query("select e from EnumValue e where  (:enumValueId is null or cast(:enumValueId as string)=cast(e.enumValueId as string)) and (:name is null or :name='' or cast(:name as string)=e.name) and (:value is null or cast(:value as string)=cast(e.value as string)) and (:enumEntity=e.enumEntity or :enumEntity is null) ")
    public List<it.anggen.model.field.EnumValue> findByEnumValueIdAndNameAndValueAndEnumEntity(
        @org.springframework.data.repository.query.Param("enumValueId")
        java.lang.Long enumValueId,
        @org.springframework.data.repository.query.Param("name")
        java.lang.String name,
        @org.springframework.data.repository.query.Param("value")
        java.lang.Integer value,
        @org.springframework.data.repository.query.Param("enumEntity")
        it.anggen.model.entity.EnumEntity enumEntity);

}
