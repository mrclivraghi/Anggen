
package it.polimi.service.login;

import java.util.List;
import it.polimi.model.login.Authority;
import it.polimi.repository.login.AuthorityRepository;
import it.polimi.repository.login.UserRepository;
import it.polimi.searchbean.login.AuthoritySearchBean;
import it.polimi.service.login.AuthorityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorityServiceImpl
    implements AuthorityService
{

    @org.springframework.beans.factory.annotation.Autowired
    public AuthorityRepository authorityRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public UserRepository userRepository;

    @Override
    public List<Authority> findById(Long authorityId) {
        return authorityRepository.findByAuthorityId(authorityId);
    }

    @Override
    public List<Authority> find(AuthoritySearchBean authority) {
        return authorityRepository.findByAuthorityIdAndNameAndUser(authority.getAuthorityId(),authority.getName(),authority.getUserList()==null? null :authority.getUserList().get(0));
    }

    @Override
    public void deleteById(Long authorityId) {
        authorityRepository.delete(authorityId);
        return;
    }

    @Override
    public Authority insert(Authority authority) {
        return authorityRepository.save(authority);
    }

    @Override
    @Transactional
    public Authority update(Authority authority) {
        if (authority.getUserList()!=null)
        for (it.polimi.model.login.User user: authority.getUserList())
        {
        it.polimi.model.login.User savedUser = userRepository.findOne(user.getUserId());
        Boolean found=false; 
        for (Authority tempAuthority : savedUser.getAuthorityList())
        {
        if (tempAuthority.getAuthorityId().equals(authority.getAuthorityId()))
        {
        found=true;
        break;
        }
        }
        if (!found)
        savedUser.getAuthorityList().add(authority);
        }
        Authority returnedAuthority=authorityRepository.save(authority);
         return returnedAuthority;
    }

}
