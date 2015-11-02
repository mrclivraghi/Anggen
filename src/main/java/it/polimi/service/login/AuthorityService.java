
package it.polimi.service.login;

import java.util.List;
import it.polimi.model.login.Authority;
import it.polimi.searchbean.login.AuthoritySearchBean;

public interface AuthorityService {


    public List<Authority> findById(Long authorityId);

    public List<Authority> find(AuthoritySearchBean authority);

    public void deleteById(Long authorityId);

    public Authority insert(Authority authority);

    public Authority update(Authority authority);

}
