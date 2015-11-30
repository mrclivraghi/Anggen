
package it.generated.anggen.service.security;

import java.util.List;
import it.generated.anggen.repository.security.RestrictionEntityGroupRepository;
import it.generated.anggen.repository.security.RestrictionEntityRepository;
import it.generated.anggen.repository.security.RestrictionFieldRepository;
import it.generated.anggen.repository.security.RoleRepository;
import it.generated.anggen.repository.security.UserRepository;
import it.generated.anggen.searchbean.security.RoleSearchBean;
import it.generated.anggen.service.security.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImpl
    implements RoleService
{

    @org.springframework.beans.factory.annotation.Autowired
    public RoleRepository roleRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public RestrictionEntityGroupRepository restrictionEntityGroupRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public RestrictionFieldRepository restrictionFieldRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public RestrictionEntityRepository restrictionEntityRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public UserRepository userRepository;

    @Override
    public List<it.generated.anggen.model.security.Role> findById(Integer roleId) {
        return roleRepository.findByRoleId(roleId);
    }

    @Override
    public List<it.generated.anggen.model.security.Role> find(RoleSearchBean role) {
        return roleRepository.findByRoleAndRoleIdAndRestrictionEntityGroupAndRestrictionFieldAndRestrictionEntityAndUser(role.getRole(),role.getRoleId(),role.getRestrictionEntityGroupList()==null? null :role.getRestrictionEntityGroupList().get(0),role.getRestrictionFieldList()==null? null :role.getRestrictionFieldList().get(0),role.getRestrictionEntityList()==null? null :role.getRestrictionEntityList().get(0),role.getUserList()==null? null :role.getUserList().get(0));
    }

    @Override
    public void deleteById(Integer roleId) {
        roleRepository.delete(roleId);
        return;
    }

    @Override
    public it.generated.anggen.model.security.Role insert(it.generated.anggen.model.security.Role role) {
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public it.generated.anggen.model.security.Role update(it.generated.anggen.model.security.Role role) {
        if (role.getRestrictionEntityGroupList()!=null)
        for (it.generated.anggen.model.security.RestrictionEntityGroup restrictionEntityGroup: role.getRestrictionEntityGroupList())
        {
        restrictionEntityGroup.setRole(role);
        }
        if (role.getRestrictionFieldList()!=null)
        for (it.generated.anggen.model.security.RestrictionField restrictionField: role.getRestrictionFieldList())
        {
        restrictionField.setRole(role);
        }
        if (role.getRestrictionEntityList()!=null)
        for (it.generated.anggen.model.security.RestrictionEntity restrictionEntity: role.getRestrictionEntityList())
        {
        restrictionEntity.setRole(role);
        }
        if (role.getUserList()!=null)
        for (it.generated.anggen.model.security.User user: role.getUserList())
        {
        it.generated.anggen.model.security.User savedUser = userRepository.findOne(user.getUserId());
        Boolean found=false; 
        for (it.generated.anggen.model.security.Role tempRole : savedUser.getRoleList())
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
        it.generated.anggen.model.security.Role returnedRole=roleRepository.save(role);
         return returnedRole;
    }

}
