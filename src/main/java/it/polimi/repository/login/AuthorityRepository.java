
package it.polimi.repository.login;

import java.util.List;
import it.polimi.model.login.Authority;
import it.polimi.model.login.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository
    extends CrudRepository<Authority, Long>
{


    public List<Authority> findByAuthorityId(Long authorityId);

    public List<Authority> findByName(String name);

    @Query("select a from Authority a where  (:authorityId is null or cast(:authorityId as string)=cast(a.authorityId as string)) and (:name is null or :name='' or cast(:name as string)=a.name) and (:user in elements(a.userList)  or :user is null) ")
    public List<Authority> findByAuthorityIdAndNameAndUser(
        @Param("authorityId")
        Long authorityId,
        @Param("name")
        String name,
        @Param("user")
        User user);

}
