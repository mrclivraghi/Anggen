
package it.polimi.repository;

import it.polimi.model.domain.EnumField;
import it.polimi.model.domain.EnumValue;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EnumValueRepository
    extends CrudRepository<EnumValue, Long>
{


    public List<EnumValue> findByEnumValueId(Long enumValueId);

    public List<EnumValue> findByValue(Long value);

    public List<EnumValue> findByName(String name);

    public List<EnumValue> findByEnumField(EnumField enumField);

    @Query("select e from EnumValue e where  (:enumValueId is null or cast(:enumValueId as string)=cast(e.enumValueId as string)) and (:value is null or cast(:value as string)=cast(e.value as string)) and (:name is null or :name='' or cast(:name as string)=e.name) and (:enumField=e.enumField or :enumField is null) ")
    public List<EnumValue> findByEnumValueIdAndValueAndNameAndEnumField(
        @Param("enumValueId")
        Long enumValueId,
        @Param("value")
        Long value,
        @Param("name")
        String name,
        @Param("enumField")
        EnumField enumField);

}
