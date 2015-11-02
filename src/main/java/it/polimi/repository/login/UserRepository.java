
package it.polimi.repository.login;

import java.util.List;
import it.polimi.model.login.Authority;
import it.polimi.model.login.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
    extends CrudRepository<User, Long>
{


    public List<User> findByUserId(Long userId);

    public List<User> findByUsername(String username);

    public List<User> findByPassword(String password);

    public List<User> findByRole(String role);

    public List<User> findByEnabled(Boolean enabled);

    @Query("select u from User u where  (:userId is null or cast(:userId as string)=cast(u.userId as string)) and (:username is null or :username='' or cast(:username as string)=u.username) and (:password is null or :password='' or cast(:password as string)=u.password) and (:role is null or :role='' or cast(:role as string)=u.role) and (:enabled is null or cast(:enabled as string)=cast(u.enabled as string)) and (:authority in elements(u.authorityList)  or :authority is null) ")
    public List<User> findByUserIdAndUsernameAndPasswordAndRoleAndEnabledAndAuthority(
        @Param("userId")
        Long userId,
        @Param("username")
        String username,
        @Param("password")
        String password,
        @Param("role")
        String role,
        @Param("enabled")
        Boolean enabled,
        @Param("authority")
        Authority authority);

}
