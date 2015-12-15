
package it.anggen.controller.entity;

import java.util.List;
import it.anggen.searchbean.entity.EnumEntitySearchBean;
import it.anggen.security.SecurityService;
import it.anggen.service.entity.EnumEntityService;
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
@RequestMapping("/enumEntity")
public class EnumEntityController {

    @org.springframework.beans.factory.annotation.Autowired
    private EnumEntityService enumEntityService;
    @org.springframework.beans.factory.annotation.Autowired
    private SecurityService securityService;
    private final static Logger log = LoggerFactory.getLogger(it.anggen.model.entity.EnumEntity.class);
    @Value("${application.security}")
    private Boolean securityEnabled;

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EnumEntity.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "enumEntity";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        EnumEntitySearchBean enumEntity) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EnumEntity.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.anggen.model.entity.EnumEntity> enumEntityList;
        if (enumEntity.getEnumEntityId()!=null)
         log.info("Searching enumEntity like {}", enumEntity.getName());
        enumEntityList=enumEntityService.find(enumEntity);
        getRightMapping(enumEntityList);
        getSecurityMapping(enumEntityList);
         log.info("Search: returning {} enumEntity.",enumEntityList.size());
        return ResponseEntity.ok().body(enumEntityList);
    }

    @ResponseBody
    @RequestMapping(value = "/{enumEntityId}", method = RequestMethod.GET)
    public ResponseEntity getEnumEntityById(
        @PathVariable
        String enumEntityId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EnumEntity.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Searching enumEntity with id {}",enumEntityId);
        List<it.anggen.model.entity.EnumEntity> enumEntityList=enumEntityService.findById(Long.valueOf(enumEntityId));
        getRightMapping(enumEntityList);
         log.info("Search: returning {} enumEntity.",enumEntityList.size());
        return ResponseEntity.ok().body(enumEntityList);
    }

    @ResponseBody
    @RequestMapping(value = "/{enumEntityId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEnumEntityById(
        @PathVariable
        String enumEntityId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EnumEntity.staticEntityId, it.anggen.model.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting enumEntity with id {}",enumEntityId);
        enumEntityService.deleteById(Long.valueOf(enumEntityId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertEnumEntity(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.entity.EnumEntity enumEntity) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EnumEntity.staticEntityId, it.anggen.model.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (enumEntity.getEnumEntityId()!=null)
        log.info("Inserting enumEntity like {}", enumEntity.getName());
        it.anggen.model.entity.EnumEntity insertedEnumEntity=enumEntityService.insert(enumEntity);
        getRightMapping(insertedEnumEntity);
        log.info("Inserted enumEntity with id {}",insertedEnumEntity.getEnumEntityId());
        return ResponseEntity.ok().body(insertedEnumEntity);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateEnumEntity(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.entity.EnumEntity enumEntity) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EnumEntity.staticEntityId, it.anggen.model.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Updating enumEntity with id {}",enumEntity.getEnumEntityId());
        rebuildSecurityMapping(enumEntity);
        it.anggen.model.entity.EnumEntity updatedEnumEntity=enumEntityService.update(enumEntity);
        getRightMapping(updatedEnumEntity);
        getSecurityMapping(updatedEnumEntity);
        return ResponseEntity.ok().body(updatedEnumEntity);
    }

    private List<it.anggen.model.entity.EnumEntity> getRightMapping(List<it.anggen.model.entity.EnumEntity> enumEntityList) {
        for (it.anggen.model.entity.EnumEntity enumEntity: enumEntityList)
        {
        getRightMapping(enumEntity);
        }
        return enumEntityList;
    }

    private void getRightMapping(it.anggen.model.entity.EnumEntity enumEntity) {
        if (enumEntity.getProject()!=null)
        {
        enumEntity.getProject().setEnumEntityList(null);
        enumEntity.getProject().setEntityGroupList(null);
        }
        if (enumEntity.getEnumValueList()!=null)
        for (it.anggen.model.field.EnumValue enumValue :enumEntity.getEnumValueList())

        {

        enumValue.setEnumEntity(null);
        }
    }

    private void rebuildSecurityMapping(it.anggen.model.entity.EnumEntity enumEntity) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Project.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        enumEntity.setProject(enumEntityService.findById(enumEntity.getEnumEntityId()).get(0).getProject());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.EnumValue.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        enumEntity.setEnumValueList(enumEntityService.findById(enumEntity.getEnumEntityId()).get(0).getEnumValueList());
    }

    private List<it.anggen.model.entity.EnumEntity> getSecurityMapping(List<it.anggen.model.entity.EnumEntity> enumEntityList) {
        for (it.anggen.model.entity.EnumEntity enumEntity: enumEntityList)
        {
        getSecurityMapping(enumEntity);
        }
        return enumEntityList;
    }

    private void getSecurityMapping(it.anggen.model.entity.EnumEntity enumEntity) {
        if (securityEnabled && enumEntity.getProject()!=null  && !securityService.hasPermission(it.anggen.model.entity.Project.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        enumEntity.setProject(null);

        if (securityEnabled && enumEntity.getEnumValueList()!=null && !securityService.hasPermission(it.anggen.model.field.EnumValue.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        enumEntity.setEnumValueList(null);

    }

}
