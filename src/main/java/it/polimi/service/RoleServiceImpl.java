
package it.polimi.service;

import java.util.List;

import it.polimi.model.domain.Role;
import it.polimi.repository.RestrictionRepository;
import it.polimi.repository.RoleRepository;
import it.polimi.repository.UserRepository;
import it.polimi.searchbean.RoleSearchBean;
import it.polimi.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImpl
    implements RoleService
{

    @org.springframework.beans.factory.annotation.Autowired
    public RoleRepository roleRepository;
    
    @Autowired
    public UserRepository userRepository;
    
    @org.springframework.beans.factory.annotation.Autowired
    public RestrictionRepository restrictionRepository;

    @Override
    public List<Role> findById(Integer roleId) {
        return roleRepository.findByRoleId(roleId);
    }

    @Override
    public List<Role> find(RoleSearchBean role) {
        return roleRepository.findByRoleIdAndRoleAndUserAndRestriction(role.getRoleId(),role.getRole(),role.getUserList()==null? null :role.getUserList().get(0),role.getRestrictionList()==null? null :role.getRestrictionList().get(0));
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
        for (it.polimi.model.domain.User user: role.getUserList())
        {
        it.polimi.model.domain.User savedUser = userRepository.findOne(user.getUserId());
        Boolean found=false; 
        for (Role tempRole : savedUser.getRoleList())
        {
        if (tempRole.getRoleId().equals(role.getRoleId()))
        {
        found=true;
        break;
        }
        }
        if (!found)
        savedUser.getRoleList().add(role);
        }
        if (role.getRestrictionList()!=null)
        for (it.polimi.model.domain.Restriction restriction: role.getRestrictionList())
        {
        restriction.setRole(role);
        }
        Role returnedRole=roleRepository.save(role);
         return returnedRole;
    }

}
