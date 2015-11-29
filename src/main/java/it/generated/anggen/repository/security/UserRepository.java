
package it.generated.anggen.repository.security;

import java.util.List;
import it.generated.anggen.model.security.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
    extends CrudRepository<it.generated.anggen.model.security.User, java.lang.Long>
{


    public List<it.generated.anggen.model.security.User> findByUserId(java.lang.Long userId);

    public List<it.generated.anggen.model.security.User> findByUsername(java.lang.String username);

    public List<it.generated.anggen.model.security.User> findByPassword(java.lang.String password);

    public List<it.generated.anggen.model.security.User> findByEnabled(java.lang.Boolean enabled);

    @Query("select u from User u where  (:userId is null or cast(:userId as string)=cast(u.userId as string)) and (:username is null or :username='' or cast(:username as string)=u.username) and (:password is null or :password='' or cast(:password as string)=u.password) and (:enabled is null or cast(:enabled as string)=cast(u.enabled as string)) and (:role in elements(u.roleList)  or :role is null) ")
    public List<it.generated.anggen.model.security.User> findByUserIdAndUsernameAndPasswordAndEnabledAndRole(
        @org.springframework.data.repository.query.Param("userId")
        java.lang.Long userId,
        @org.springframework.data.repository.query.Param("username")
        java.lang.String username,
        @org.springframework.data.repository.query.Param("password")
        java.lang.String password,
        @org.springframework.data.repository.query.Param("enabled")
        java.lang.Boolean enabled,
        @org.springframework.data.repository.query.Param("role")
        Role role);

}
