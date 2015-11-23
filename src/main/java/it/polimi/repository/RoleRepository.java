
package it.polimi.repository;

import java.util.List;
import it.polimi.model.domain.Restriction;
import it.polimi.model.domain.Role;
import it.polimi.model.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository
    extends CrudRepository<Role, Integer>
{


    public List<Role> findByRoleId(Integer roleId);

    public List<Role> findByRole(String role);

    @Query("select r from Role r where  (:roleId is null or cast(:roleId as string)=cast(r.roleId as string)) and (:role is null or :role='' or cast(:role as string)=r.role) and (:user in elements(r.userList)  or :user is null) and (:restriction in elements(r.restrictionList)  or :restriction is null) ")
    public List<Role> findByRoleIdAndRoleAndUserAndRestriction(
        @Param("roleId")
        Integer roleId,
        @Param("role")
        String role,
        @Param("user")
        User user,
        @Param("restriction")
        Restriction restriction);

}
