
package it.polimi.service.security;

import java.util.List;

import it.polimi.repository.security.RestrictionEntityGroupRepository;
import it.polimi.repository.security.RestrictionEntityRepository;
import it.polimi.repository.security.RestrictionFieldRepository;
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
    @org.springframework.beans.factory.annotation.Autowired
    public RestrictionEntityRepository restrictionEntityRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public RestrictionFieldRepository restrictionFieldRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public RestrictionEntityGroupRepository restrictionEntityGroupRepository;

    @Override
    public List<it.polimi.model.security.Role> findById(Integer roleId) {
        return roleRepository.findByRoleId(roleId);
    }

    @Override
    public List<it.polimi.model.security.Role> find(RoleSearchBean role) {
        return roleRepository.findByRoleIdAndRoleAndUserAndRestrictionEntityAndRestrictionFieldAndRestrictionEntityGroup(role.getRoleId(),role.getRole(),role.getUserList()==null? null :role.getUserList().get(0),role.getRestrictionEntityList()==null? null :role.getRestrictionEntityList().get(0),role.getRestrictionFieldList()==null? null :role.getRestrictionFieldList().get(0),role.getRestrictionEntityGroupList()==null? null :role.getRestrictionEntityGroupList().get(0));
    }

    @Override
    public void deleteById(Integer roleId) {
        roleRepository.delete(roleId);
        return;
    }

    @Override
    public it.polimi.model.security.Role insert(it.polimi.model.security.Role role) {
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public it.polimi.model.security.Role update(it.polimi.model.security.Role role) {
        if (role.getUserList()!=null)
        for (it.polimi.model.security.User user: role.getUserList())
        {
        it.polimi.model.security.User savedUser = userRepository.findOne(user.getUserId());
        Boolean found=false; 
        for (it.polimi.model.security.Role tempRole : savedUser.getRoleList())
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
        it.polimi.model.security.Role returnedRole=roleRepository.save(role);
         return returnedRole;
    }

}
