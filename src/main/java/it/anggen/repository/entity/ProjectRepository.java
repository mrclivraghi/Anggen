
package it.anggen.repository.entity;

import java.util.List;
import it.anggen.model.entity.EntityGroup;
import it.anggen.model.entity.EnumEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository
    extends JpaRepository<it.anggen.model.entity.Project, java.lang.Integer>
{


    public List<it.anggen.model.entity.Project> findByProjectId(java.lang.Integer projectId);

    public List<it.anggen.model.entity.Project> findByName(java.lang.String name);

    @Query("select p from Project p where  (:projectId is null or cast(:projectId as string)=cast(p.projectId as string)) and (:name is null or :name='' or cast(:name as string)=p.name) and (:enumEntity in elements(p.enumEntityList)  or :enumEntity is null) and (:entityGroup in elements(p.entityGroupList)  or :entityGroup is null) ")
    public List<it.anggen.model.entity.Project> findByProjectIdAndNameAndEnumEntityAndEntityGroup(
        @org.springframework.data.repository.query.Param("projectId")
        java.lang.Integer projectId,
        @org.springframework.data.repository.query.Param("name")
        java.lang.String name,
        @org.springframework.data.repository.query.Param("enumEntity")
        EnumEntity enumEntity,
        @org.springframework.data.repository.query.Param("entityGroup")
        EntityGroup entityGroup);

}
