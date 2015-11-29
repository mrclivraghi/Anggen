
package it.polimi.service;

import java.util.List;

import it.polimi.model.security.Role;
import it.polimi.repository.RestrictionEntityGroupRepository;
import it.polimi.repository.RestrictionEntityRepository;
import it.polimi.repository.RestrictionFieldRepository;
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
    @org.springframework.beans.factory.annotation.Autowired
    public RestrictionEntityRepository restrictionEntityRepository;
    
    @Autowired
    public UserRepository userRepository;
    
    @org.springframework.beans.factory.annotation.Autowired
    public RestrictionFieldRepository restrictionFieldRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public RestrictionEntityGroupRepository restrictionEntityGroupRepository;

    @Override
    public List<Role> findById(Integer roleId) {
        return roleRepository.findByRoleId(roleId);
    }

    @Override
    public List<Role> find(RoleSearchBean role) {
        return roleRepository.findByRoleIdAndRoleAndUserAndRestrictionEntityAndRestrictionFieldAndRestrictionEntityGroup(role.getRoleId(),role.getRole(),role.getUserList()==null? null :role.getUserList().get(0),role.getRestrictionEntityList()==null? null :role.getRestrictionEntityList().get(0),role.getRestrictionFieldList()==null? null :role.getRestrictionFieldList().get(0),role.getRestrictionEntityGroupList()==null? null :role.getRestrictionEntityGroupList().get(0));
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
        it.polimi.model.security.User savedUser = userRepository.findOne(user.getUserId());
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
        if (role.getRestrictionEntityList()!=null)
        for (it.polimi.model.security.RestrictionEntity restrictionEntity: role.getRestrictionEntityList())
        {
        restrictionEntity.setRole(role);
        }
        if (role.getRestrictionFieldList()!=null)
        for (it.polimi.model.security.RestrictionField restrictionField: role.getRestrictionFieldList())
        {
        restrictionField.setRole(role);
        }
        if (role.getRestrictionEntityGroupList()!=null)
        for (it.polimi.model.security.RestrictionEntityGroup restrictionEntityGroup: role.getRestrictionEntityGroupList())
        {
        restrictionEntityGroup.setRole(role);
        }
        Role returnedRole=roleRepository.save(role);
         return returnedRole;
    }

}
