
package it.anggen.repository.security;

import java.util.List;
import it.anggen.model.security.RestrictionEntity;
import it.anggen.model.security.RestrictionEntityGroup;
import it.anggen.model.security.RestrictionField;
import it.anggen.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository
    extends JpaRepository<it.anggen.model.security.Role, java.lang.Integer>
{


    @Query("select r from Role r")
    public List<it.anggen.model.security.Role> findAll();

    public List<it.anggen.model.security.Role> findByRoleId(java.lang.Integer roleId);

    public List<it.anggen.model.security.Role> findByRole(java.lang.String role);

    @Query("select r from Role r where  (:roleId is null or cast(:roleId as string)=cast(r.roleId as string)) and (:role is null or :role='' or cast(:role as string)=r.role) and (:restrictionEntity in elements(r.restrictionEntityList)  or :restrictionEntity is null) and (:restrictionField in elements(r.restrictionFieldList)  or :restrictionField is null) and (:user in elements(r.userList)  or :user is null) and (:restrictionEntityGroup in elements(r.restrictionEntityGroupList)  or :restrictionEntityGroup is null) ")
    public List<it.anggen.model.security.Role> findByRoleIdAndRoleAndRestrictionEntityAndRestrictionFieldAndUserAndRestrictionEntityGroup(
        @org.springframework.data.repository.query.Param("roleId")
        java.lang.Integer roleId,
        @org.springframework.data.repository.query.Param("role")
        java.lang.String role,
        @org.springframework.data.repository.query.Param("restrictionEntity")
        RestrictionEntity restrictionEntity,
        @org.springframework.data.repository.query.Param("restrictionField")
        RestrictionField restrictionField,
        @org.springframework.data.repository.query.Param("user")
        User user,
        @org.springframework.data.repository.query.Param("restrictionEntityGroup")
        RestrictionEntityGroup restrictionEntityGroup);

}
