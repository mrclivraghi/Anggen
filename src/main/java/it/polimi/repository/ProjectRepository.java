
package it.polimi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository
    extends CrudRepository<it.polimi.model.entity.Project, java.lang.Integer>
{


    public List<it.polimi.model.entity.Project> findByName(java.lang.String name);

    public List<it.polimi.model.entity.Project> findByProjectId(java.lang.Integer projectId);

    @Query("select p from Project p where  (:name is null or :name='' or cast(:name as string)=p.name) and (:projectId is null or cast(:projectId as string)=cast(p.projectId as string)) and (:entityGroup in elements(p.entityGroupList)  or :entityGroup is null) ")
    public List<it.polimi.model.entity.Project> findByNameAndProjectIdAndEntityGroup(
        @org.springframework.data.repository.query.Param("name")
        java.lang.String name,
        @org.springframework.data.repository.query.Param("projectId")
        java.lang.Integer projectId,
        @org.springframework.data.repository.query.Param("entityGroup")
        it.polimi.model.entity.EntityGroup entityGroup);

}
