
package it.polimi.repository;

import java.util.List;

import it.polimi.model.security.RestrictionEntity;
import it.polimi.model.security.RestrictionEntityGroup;
import it.polimi.model.security.RestrictionField;
import it.polimi.model.security.Role;
import it.polimi.model.security.User;

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

    @Query("select r from Role r where  (:roleId is null or cast(:roleId as string)=cast(r.roleId as string)) and (:role is null or :role='' or cast(:role as string)=r.role) and (:user in elements(r.userList)  or :user is null) and (:restrictionEntity in elements(r.restrictionEntityList)  or :restrictionEntity is null) and (:restrictionField in elements(r.restrictionFieldList)  or :restrictionField is null) and (:restrictionEntityGroup in elements(r.restrictionEntityGroupList)  or :restrictionEntityGroup is null) ")
    public List<Role> findByRoleIdAndRoleAndUserAndRestrictionEntityAndRestrictionFieldAndRestrictionEntityGroup(
        @Param("roleId")
        Integer roleId,
        @Param("role")
        String role,
        @Param("user")
        User user,
        @Param("restrictionEntity")
        RestrictionEntity restrictionEntity,
        @Param("restrictionField")
        RestrictionField restrictionField,
        @Param("restrictionEntityGroup")
        RestrictionEntityGroup restrictionEntityGroup);

}
