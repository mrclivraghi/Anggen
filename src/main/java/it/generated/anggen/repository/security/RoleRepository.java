
package it.generated.anggen.repository.security;

import java.util.List;
import it.generated.anggen.model.security.RestrictionEntity;
import it.generated.anggen.model.security.RestrictionEntityGroup;
import it.generated.anggen.model.security.RestrictionField;
import it.generated.anggen.model.security.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository
    extends CrudRepository<it.generated.anggen.model.security.Role, java.lang.Integer>
{


    public List<it.generated.anggen.model.security.Role> findByRole(java.lang.String role);

    public List<it.generated.anggen.model.security.Role> findByRoleId(java.lang.Integer roleId);

    @Query("select r from Role r where  (:role is null or :role='' or cast(:role as string)=r.role) and (:roleId is null or cast(:roleId as string)=cast(r.roleId as string)) and (:restrictionEntityGroup in elements(r.restrictionEntityGroupList)  or :restrictionEntityGroup is null) and (:restrictionField in elements(r.restrictionFieldList)  or :restrictionField is null) and (:restrictionEntity in elements(r.restrictionEntityList)  or :restrictionEntity is null) and (:user in elements(r.userList)  or :user is null) ")
    public List<it.generated.anggen.model.security.Role> findByRoleAndRoleIdAndRestrictionEntityGroupAndRestrictionFieldAndRestrictionEntityAndUser(
        @org.springframework.data.repository.query.Param("role")
        java.lang.String role,
        @org.springframework.data.repository.query.Param("roleId")
        java.lang.Integer roleId,
        @org.springframework.data.repository.query.Param("restrictionEntityGroup")
        RestrictionEntityGroup restrictionEntityGroup,
        @org.springframework.data.repository.query.Param("restrictionField")
        RestrictionField restrictionField,
        @org.springframework.data.repository.query.Param("restrictionEntity")
        RestrictionEntity restrictionEntity,
        @org.springframework.data.repository.query.Param("user")
        User user);

}
