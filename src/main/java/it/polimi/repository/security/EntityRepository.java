
package it.polimi.repository.security;

import java.util.List;
import it.polimi.model.security.Entity;
import it.polimi.model.security.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityRepository
    extends CrudRepository<Entity, Long>
{


    public List<Entity> findByEntityId(Long entityId);

    public List<Entity> findByEntityName(String entityName);

    @Query("select e from Entity e where  (:entityId is null or cast(:entityId as string)=cast(e.entityId as string)) and (:entityName is null or :entityName='' or cast(:entityName as string)=e.entityName) and (:role in elements(e.roleList)  or :role is null) ")
    public List<Entity> findByEntityIdAndEntityNameAndRole(
        @Param("entityId")
        Long entityId,
        @Param("entityName")
        String entityName,
        @Param("role")
        Role role);

}
