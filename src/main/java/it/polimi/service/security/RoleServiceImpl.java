
package it.polimi.service.security;

import java.util.List;
import it.polimi.model.security.Role;
import it.polimi.repository.security.RoleRepository;
import it.polimi.repository.security.UserRepository;
import it.polimi.searchbean.security.RoleSearchBean;
import it.polimi.service.security.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImpl
    implements RoleService
{

    @org.springframework.beans.factory.annotation.Autowired
    public RoleRepository roleRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public UserRepository userRepository;

    @Override
    public List<Role> findById(Integer roleId) {
        return roleRepository.findByRoleId(roleId);
    }

    @Override
    public List<Role> find(RoleSearchBean role) {
        return roleRepository.findByRoleIdAndRoleAndUser(role.getRoleId(),role.getRole(),role.getUserList()==null? null :role.getUserList().get(0));
    }

    @Override
    public void deleteById(Integer roleId) {
        roleRepository.delete(roleId);
        return;
    }

    @Override
    public Role insert(Role role) {
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public Role update(Role role) {
        if (role.getUserList()!=null)
        for (it.polimi.model.security.User user: role.getUserList())
        {
        user.setRole(role);
        }
        Role returnedRole=roleRepository.save(role);
         return returnedRole;
    }

}
