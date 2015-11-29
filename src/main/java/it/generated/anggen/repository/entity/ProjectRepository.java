
package it.generated.anggen.repository.entity;

import java.util.List;
import it.generated.anggen.model.entity.EntityGroup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository
    extends CrudRepository<it.generated.anggen.model.entity.Project, java.lang.Integer>
{


    public List<it.generated.anggen.model.entity.Project> findByEntityId(java.lang.Long entityId);

    public List<it.generated.anggen.model.entity.Project> findByProjectId(java.lang.Integer projectId);

    public List<it.generated.anggen.model.entity.Project> findByName(java.lang.String name);

    @Query("select p from Project p where  (:entityId is null or cast(:entityId as string)=cast(p.entityId as string)) and (:projectId is null or cast(:projectId as string)=cast(p.projectId as string)) and (:name is null or :name='' or cast(:name as string)=p.name) and (:entityGroup in elements(p.entityGroupList)  or :entityGroup is null) ")
    public List<it.generated.anggen.model.entity.Project> findByEntityIdAndProjectIdAndNameAndEntityGroup(
        @org.springframework.data.repository.query.Param("entityId")
        java.lang.Long entityId,
        @org.springframework.data.repository.query.Param("projectId")
        java.lang.Integer projectId,
        @org.springframework.data.repository.query.Param("name")
        java.lang.String name,
        @org.springframework.data.repository.query.Param("entityGroup")
        EntityGroup entityGroup);

}
