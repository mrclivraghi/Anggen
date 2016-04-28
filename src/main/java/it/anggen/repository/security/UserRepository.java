
package it.anggen.repository.security;

import java.util.List;
import it.anggen.model.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
    extends JpaRepository<it.anggen.model.security.User, java.lang.Long>
{


    @Query("select u from User u")
    public List<it.anggen.model.security.User> findAll();

    public List<it.anggen.model.security.User> findByUserId(java.lang.Long userId);

    public List<it.anggen.model.security.User> findByEnabled(java.lang.Boolean enabled);

    public List<it.anggen.model.security.User> findByUsername(java.lang.String username);

    public List<it.anggen.model.security.User> findByPassword(java.lang.String password);

    @Query("select u from User u where  (:userId is null or cast(:userId as string)=cast(u.userId as string)) and (:enabled is null or cast(:enabled as string)=cast(u.enabled as string)) and (:username is null or :username='' or cast(:username as string)=u.username) and (:password is null or :password='' or cast(:password as string)=u.password) and (:role in elements(u.roleList)  or :role is null) ")
    public List<it.anggen.model.security.User> findByUserIdAndEnabledAndUsernameAndPasswordAndRole(
        @org.springframework.data.repository.query.Param("userId")
        java.lang.Long userId,
        @org.springframework.data.repository.query.Param("enabled")
        java.lang.Boolean enabled,
        @org.springframework.data.repository.query.Param("username")
        java.lang.String username,
        @org.springframework.data.repository.query.Param("password")
        java.lang.String password,
        @org.springframework.data.repository.query.Param("role")
        Role role);

}
