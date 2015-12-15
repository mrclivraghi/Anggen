
package it.anggen.controller.security;

import java.util.List;
import it.anggen.searchbean.security.RestrictionEntitySearchBean;
import it.anggen.security.SecurityService;
import it.anggen.service.security.RestrictionEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/restrictionEntity")
public class RestrictionEntityController {

    @org.springframework.beans.factory.annotation.Autowired
    private RestrictionEntityService restrictionEntityService;
    @org.springframework.beans.factory.annotation.Autowired
    private SecurityService securityService;
    private final static Logger log = LoggerFactory.getLogger(it.anggen.model.security.RestrictionEntity.class);
    @Value("${application.security}")
    private Boolean securityEnabled;

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionEntity.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "restrictionEntity";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        RestrictionEntitySearchBean restrictionEntity) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionEntity.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.anggen.model.security.RestrictionEntity> restrictionEntityList;
        if (restrictionEntity.getRestrictionEntityId()!=null)
         log.info("Searching restrictionEntity like {}", restrictionEntity.getRestrictionEntityId()+' '+ restrictionEntity.getEntity().getName());
        restrictionEntityList=restrictionEntityService.find(restrictionEntity);
        getRightMapping(restrictionEntityList);
        getSecurityMapping(restrictionEntityList);
         log.info("Search: returning {} restrictionEntity.",restrictionEntityList.size());
        return ResponseEntity.ok().body(restrictionEntityList);
    }

    @ResponseBody
    @RequestMapping(value = "/{restrictionEntityId}", method = RequestMethod.GET)
    public ResponseEntity getRestrictionEntityById(
        @PathVariable
        String restrictionEntityId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionEntity.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Searching restrictionEntity with id {}",restrictionEntityId);
        List<it.anggen.model.security.RestrictionEntity> restrictionEntityList=restrictionEntityService.findById(Long.valueOf(restrictionEntityId));
        getRightMapping(restrictionEntityList);
         log.info("Search: returning {} restrictionEntity.",restrictionEntityList.size());
        return ResponseEntity.ok().body(restrictionEntityList);
    }

    @ResponseBody
    @RequestMapping(value = "/{restrictionEntityId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRestrictionEntityById(
        @PathVariable
        String restrictionEntityId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionEntity.staticEntityId, it.anggen.model.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting restrictionEntity with id {}",restrictionEntityId);
        restrictionEntityService.deleteById(Long.valueOf(restrictionEntityId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertRestrictionEntity(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.security.RestrictionEntity restrictionEntity) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionEntity.staticEntityId, it.anggen.model.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (restrictionEntity.getRestrictionEntityId()!=null)
        log.info("Inserting restrictionEntity like {}", restrictionEntity.getRestrictionEntityId()+' '+ restrictionEntity.getEntity().getName());
        it.anggen.model.security.RestrictionEntity insertedRestrictionEntity=restrictionEntityService.insert(restrictionEntity);
        getRightMapping(insertedRestrictionEntity);
        log.info("Inserted restrictionEntity with id {}",insertedRestrictionEntity.getRestrictionEntityId());
        return ResponseEntity.ok().body(insertedRestrictionEntity);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateRestrictionEntity(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.security.RestrictionEntity restrictionEntity) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionEntity.staticEntityId, it.anggen.model.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Updating restrictionEntity with id {}",restrictionEntity.getRestrictionEntityId());
        rebuildSecurityMapping(restrictionEntity);
        it.anggen.model.security.RestrictionEntity updatedRestrictionEntity=restrictionEntityService.update(restrictionEntity);
        getRightMapping(updatedRestrictionEntity);
        getSecurityMapping(updatedRestrictionEntity);
        return ResponseEntity.ok().body(updatedRestrictionEntity);
    }

    private List<it.anggen.model.security.RestrictionEntity> getRightMapping(List<it.anggen.model.security.RestrictionEntity> restrictionEntityList) {
        for (it.anggen.model.security.RestrictionEntity restrictionEntity: restrictionEntityList)
        {
        getRightMapping(restrictionEntity);
        }
        return restrictionEntityList;
    }

    private void getRightMapping(it.anggen.model.security.RestrictionEntity restrictionEntity) {
        if (restrictionEntity.getRole()!=null)
        {
        restrictionEntity.getRole().setRestrictionEntityGroupList(null);
        restrictionEntity.getRole().setRestrictionFieldList(null);
        restrictionEntity.getRole().setRestrictionEntityList(null);
        restrictionEntity.getRole().setUserList(null);
        }
        if (restrictionEntity.getEntity()!=null)
        {
        restrictionEntity.getEntity().setFieldList(null);
        restrictionEntity.getEntity().setEntityGroup(null);
        restrictionEntity.getEntity().setRestrictionEntityList(null);
        restrictionEntity.getEntity().setTabList(null);
        restrictionEntity.getEntity().setEnumFieldList(null);
        restrictionEntity.getEntity().setRelationshipList(null);
        }
    }

    private void rebuildSecurityMapping(it.anggen.model.security.RestrictionEntity restrictionEntity) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.Role.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        restrictionEntity.setRole(restrictionEntityService.findById(restrictionEntity.getRestrictionEntityId()).get(0).getRole());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        restrictionEntity.setEntity(restrictionEntityService.findById(restrictionEntity.getRestrictionEntityId()).get(0).getEntity());
    }

    private List<it.anggen.model.security.RestrictionEntity> getSecurityMapping(List<it.anggen.model.security.RestrictionEntity> restrictionEntityList) {
        for (it.anggen.model.security.RestrictionEntity restrictionEntity: restrictionEntityList)
        {
        getSecurityMapping(restrictionEntity);
        }
        return restrictionEntityList;
    }

    private void getSecurityMapping(it.anggen.model.security.RestrictionEntity restrictionEntity) {
        if (securityEnabled && restrictionEntity.getRole()!=null  && !securityService.hasPermission(it.anggen.model.security.Role.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        restrictionEntity.setRole(null);

        if (securityEnabled && restrictionEntity.getEntity()!=null  && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        restrictionEntity.setEntity(null);

    }

}
