
package it.anggen.repository.entity;

import java.util.List;
import it.anggen.model.entity.EntityGroup;
import it.anggen.model.entity.EnumEntity;
import it.anggen.model.generation.GenerationRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository
    extends JpaRepository<it.anggen.model.entity.Project, java.lang.Integer>
{


    @Query("select p from Project p")
    public List<it.anggen.model.entity.Project> findAll();

    public List<it.anggen.model.entity.Project> findByProjectId(java.lang.Integer projectId);

    public List<it.anggen.model.entity.Project> findByName(java.lang.String name);

    public List<it.anggen.model.entity.Project> findByAddDate(java.lang.String addDate);

    public List<it.anggen.model.entity.Project> findByModDate(java.lang.String modDate);

    public List<it.anggen.model.entity.Project> findByGenerationType(java.lang.Integer generationType);

    @Query("select p from Project p where  (:projectId is null or cast(:projectId as string)=cast(p.projectId as string)) and (:name is null or :name='' or cast(:name as string)=p.name) and (:addDate is null or cast(:addDate as string)=cast(date(p.addDate) as string)) and (:modDate is null or cast(:modDate as string)=cast(date(p.modDate) as string)) and (:generationType is null or cast(:generationType as string)=cast(p.generationType as string)) and (:entityGroup in elements(p.entityGroupList)  or :entityGroup is null) and (:enumEntity in elements(p.enumEntityList)  or :enumEntity is null) and (:generationRun in elements(p.generationRunList)  or :generationRun is null) ")
    public List<it.anggen.model.entity.Project> findByProjectIdAndNameAndAddDateAndModDateAndGenerationTypeAndEntityGroupAndEnumEntityAndGenerationRun(
        @org.springframework.data.repository.query.Param("projectId")
        java.lang.Integer projectId,
        @org.springframework.data.repository.query.Param("name")
        java.lang.String name,
        @org.springframework.data.repository.query.Param("addDate")
        java.lang.String addDate,
        @org.springframework.data.repository.query.Param("modDate")
        java.lang.String modDate,
        @org.springframework.data.repository.query.Param("generationType")
        java.lang.Integer generationType,
        @org.springframework.data.repository.query.Param("entityGroup")
        EntityGroup entityGroup,
        @org.springframework.data.repository.query.Param("enumEntity")
        EnumEntity enumEntity,
        @org.springframework.data.repository.query.Param("generationRun")
        GenerationRun generationRun);

}
