
package it.polimi.searchbean.login;

import java.util.List;
import it.polimi.model.login.User;

public class AuthoritySearchBean {

    public Long authorityId;
    public String name;
    public List<User> userList;

    public Long getAuthorityId() {
        return this.authorityId;
    }

    public void setAuthorityId(Long authorityId) {
        this.authorityId=authorityId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public List<User> getUserList() {
        return this.userList;
    }

    public void setUserList(List<User> userList) {
        this.userList=userList;
    }

}
