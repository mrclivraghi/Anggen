
package it.generated.anggen.controller.security;

import java.util.List;
import it.generated.anggen.searchbean.security.RestrictionEntitySearchBean;
import it.generated.anggen.security.SecurityService;
import it.generated.anggen.service.security.RestrictionEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final static Logger log = LoggerFactory.getLogger(it.generated.anggen.model.security.RestrictionEntity.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (!securityService.isAllowed(it.generated.anggen.model.security.RestrictionEntity.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "restrictionEntity";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        RestrictionEntitySearchBean restrictionEntity) {
        if (!securityService.isAllowed(it.generated.anggen.model.security.RestrictionEntity.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.generated.anggen.model.security.RestrictionEntity> restrictionEntityList;
        if (restrictionEntity.getRestrictionEntityId()!=null)
         log.info("Searching restrictionEntity like {}",restrictionEntity.toString());
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
        if (!securityService.isAllowed(it.generated.anggen.model.security.RestrictionEntity.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Searching restrictionEntity with id {}",restrictionEntityId);
        List<it.generated.anggen.model.security.RestrictionEntity> restrictionEntityList=restrictionEntityService.findById(Long.valueOf(restrictionEntityId));
        getRightMapping(restrictionEntityList);
         log.info("Search: returning {} restrictionEntity.",restrictionEntityList.size());
        return ResponseEntity.ok().body(restrictionEntityList);
    }

    @ResponseBody
    @RequestMapping(value = "/{restrictionEntityId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRestrictionEntityById(
        @PathVariable
        String restrictionEntityId) {
        if (!securityService.isAllowed(it.generated.anggen.model.security.RestrictionEntity.staticEntityId, it.polimi.model.security.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting restrictionEntity with id {}",restrictionEntityId);
        restrictionEntityService.deleteById(Long.valueOf(restrictionEntityId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertRestrictionEntity(
        @org.springframework.web.bind.annotation.RequestBody
        it.generated.anggen.model.security.RestrictionEntity restrictionEntity) {
        if (!securityService.isAllowed(it.generated.anggen.model.security.RestrictionEntity.staticEntityId, it.polimi.model.security.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (restrictionEntity.getRestrictionEntityId()!=null)
        log.info("Inserting restrictionEntity like {}",restrictionEntity.toString());
        it.generated.anggen.model.security.RestrictionEntity insertedRestrictionEntity=restrictionEntityService.insert(restrictionEntity);
        getRightMapping(insertedRestrictionEntity);
        log.info("Inserted restrictionEntity with id {}",insertedRestrictionEntity.getRestrictionEntityId());
        return ResponseEntity.ok().body(insertedRestrictionEntity);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateRestrictionEntity(
        @org.springframework.web.bind.annotation.RequestBody
        it.generated.anggen.model.security.RestrictionEntity restrictionEntity) {
        if (!securityService.isAllowed(it.generated.anggen.model.security.RestrictionEntity.staticEntityId, it.polimi.model.security.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Updating restrictionEntity with id {}",restrictionEntity.getRestrictionEntityId());
        rebuildSecurityMapping(restrictionEntity);
        it.generated.anggen.model.security.RestrictionEntity updatedRestrictionEntity=restrictionEntityService.update(restrictionEntity);
        getRightMapping(updatedRestrictionEntity);
        getSecurityMapping(updatedRestrictionEntity);
        return ResponseEntity.ok().body(updatedRestrictionEntity);
    }

    private List<it.generated.anggen.model.security.RestrictionEntity> getRightMapping(List<it.generated.anggen.model.security.RestrictionEntity> restrictionEntityList) {
        for (it.generated.anggen.model.security.RestrictionEntity restrictionEntity: restrictionEntityList)
        {
        getRightMapping(restrictionEntity);
        }
        return restrictionEntityList;
    }

    private void getRightMapping(it.generated.anggen.model.security.RestrictionEntity restrictionEntity) {
        if (restrictionEntity.getRole()!=null)
        {
        restrictionEntity.getRole().setUserList(null);
        restrictionEntity.getRole().setRestrictionEntityList(null);
        restrictionEntity.getRole().setRestrictionFieldList(null);
        restrictionEntity.getRole().setRestrictionEntityGroupList(null);
        }
        if (restrictionEntity.getEntity()!=null)
        {
        restrictionEntity.getEntity().setFieldList(null);
        restrictionEntity.getEntity().setRelationshipList(null);
        restrictionEntity.getEntity().setEnumFieldList(null);
        restrictionEntity.getEntity().setTabList(null);
        restrictionEntity.getEntity().setRestrictionEntityList(null);
        restrictionEntity.getEntity().setEntityGroup(null);
        }
    }

    private void rebuildSecurityMapping(it.generated.anggen.model.security.RestrictionEntity restrictionEntity) {
        if (!securityService.isAllowed(it.generated.anggen.model.security.Role.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        restrictionEntity.setRole(restrictionEntityService.findById(restrictionEntity.getRestrictionEntityId()).get(0).getRole());
        if (!securityService.isAllowed(it.generated.anggen.model.entity.Entity.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        restrictionEntity.setEntity(restrictionEntityService.findById(restrictionEntity.getRestrictionEntityId()).get(0).getEntity());
    }

    private List<it.generated.anggen.model.security.RestrictionEntity> getSecurityMapping(List<it.generated.anggen.model.security.RestrictionEntity> restrictionEntityList) {
        for (it.generated.anggen.model.security.RestrictionEntity restrictionEntity: restrictionEntityList)
        {
        getSecurityMapping(restrictionEntity);
        }
        return restrictionEntityList;
    }

    private void getSecurityMapping(it.generated.anggen.model.security.RestrictionEntity restrictionEntity) {
        if (restrictionEntity.getRole()!=null  && !securityService.isAllowed(it.generated.anggen.model.security.Role.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        restrictionEntity.setRole(null);

        if (restrictionEntity.getEntity()!=null  && !securityService.isAllowed(it.generated.anggen.model.entity.Entity.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        restrictionEntity.setEntity(null);

    }

}
