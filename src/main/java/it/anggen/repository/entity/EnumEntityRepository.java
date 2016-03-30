
package it.anggen.repository.entity;

import java.util.List;
import it.anggen.model.field.EnumValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EnumEntityRepository
    extends JpaRepository<it.anggen.model.entity.EnumEntity, java.lang.Long>
{


    @Query("select e from EnumEntity e")
    public List<it.anggen.model.entity.EnumEntity> findAll();

    public List<it.anggen.model.entity.EnumEntity> findByEnumEntityId(java.lang.Long enumEntityId);

    public List<it.anggen.model.entity.EnumEntity> findByName(java.lang.String name);

    public List<it.anggen.model.entity.EnumEntity> findByProject(it.anggen.model.entity.Project project);

    @Query("select e from EnumEntity e where  (:enumEntityId is null or cast(:enumEntityId as string)=cast(e.enumEntityId as string)) and (:name is null or :name='' or cast(:name as string)=e.name) and (:project=e.project or :project is null) and (:enumValue in elements(e.enumValueList)  or :enumValue is null) ")
    public List<it.anggen.model.entity.EnumEntity> findByEnumEntityIdAndNameAndProjectAndEnumValue(
        @org.springframework.data.repository.query.Param("enumEntityId")
        java.lang.Long enumEntityId,
        @org.springframework.data.repository.query.Param("name")
        java.lang.String name,
        @org.springframework.data.repository.query.Param("project")
        it.anggen.model.entity.Project project,
        @org.springframework.data.repository.query.Param("enumValue")
        EnumValue enumValue);

}
