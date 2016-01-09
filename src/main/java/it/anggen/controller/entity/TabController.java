
package it.anggen.controller.entity;

import java.util.List;
import it.anggen.searchbean.entity.TabSearchBean;
import it.anggen.security.SecurityService;
import it.anggen.service.entity.TabService;
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
@RequestMapping("/tab")
public class TabController {

    @org.springframework.beans.factory.annotation.Autowired
    private TabService tabService;
    @org.springframework.beans.factory.annotation.Autowired
    private SecurityService securityService;
    private final static Logger log = LoggerFactory.getLogger(it.anggen.model.entity.Tab.class);
    @Value("${application.security}")
    private Boolean securityEnabled;

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Tab.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "tab";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        TabSearchBean tab) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Tab.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.anggen.model.entity.Tab> tabList;
        if (tab.getTabId()!=null)
         log.info("Searching tab like {}", tab.getName()+' '+ tab.getTabId());
        tabList=tabService.find(tab);
        getRightMapping(tabList);
        getSecurityMapping(tabList);
         log.info("Search: returning {} tab.",tabList.size());
        return ResponseEntity.ok().body(tabList);
    }

    @ResponseBody
    @RequestMapping(value = "/{tabId}", method = RequestMethod.GET)
    public ResponseEntity getTabById(
        @PathVariable
        String tabId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Tab.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Searching tab with id {}",tabId);
        List<it.anggen.model.entity.Tab> tabList=tabService.findById(Long.valueOf(tabId));
        getRightMapping(tabList);
        getSecurityMapping(tabList);
         log.info("Search: returning {} tab.",tabList.size());
        return ResponseEntity.ok().body(tabList);
    }

    @ResponseBody
    @RequestMapping(value = "/{tabId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTabById(
        @PathVariable
        String tabId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Tab.staticEntityId, it.anggen.model.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting tab with id {}",tabId);
        tabService.deleteById(Long.valueOf(tabId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertTab(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.entity.Tab tab) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Tab.staticEntityId, it.anggen.model.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (tab.getTabId()!=null)
        log.info("Inserting tab like {}", tab.getName()+' '+ tab.getTabId());
        it.anggen.model.entity.Tab insertedTab=tabService.insert(tab);
        getRightMapping(insertedTab);
        log.info("Inserted tab with id {}",insertedTab.getTabId());
        return ResponseEntity.ok().body(insertedTab);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateTab(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.entity.Tab tab) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Tab.staticEntityId, it.anggen.model.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Updating tab with id {}",tab.getTabId());
        rebuildSecurityMapping(tab);
        it.anggen.model.entity.Tab updatedTab=tabService.update(tab);
        getRightMapping(updatedTab);
        getSecurityMapping(updatedTab);
        return ResponseEntity.ok().body(updatedTab);
    }

    private List<it.anggen.model.entity.Tab> getRightMapping(List<it.anggen.model.entity.Tab> tabList) {
        for (it.anggen.model.entity.Tab tab: tabList)
        {
        getRightMapping(tab);
        }
        return tabList;
    }

    private void getRightMapping(it.anggen.model.entity.Tab tab) {
        if (tab.getRelationshipList()!=null)
        for (it.anggen.model.relationship.Relationship relationship :tab.getRelationshipList())

        {

        relationship.setTab(null);
        relationship.setEntity(null);
        relationship.setEntityTarget(null);
        relationship.setAnnotationList(null);
        }
        if (tab.getEntity()!=null)
        {
        tab.getEntity().setRelationshipList(null);
        tab.getEntity().setEntityGroup(null);
        tab.getEntity().setTabList(null);
        tab.getEntity().setFieldList(null);
        tab.getEntity().setEnumFieldList(null);
        tab.getEntity().setRestrictionEntityList(null);
        }
        if (tab.getFieldList()!=null)
        for (it.anggen.model.field.Field field :tab.getFieldList())

        {

        field.setTab(null);
        field.setEntity(null);
        field.setAnnotationList(null);
        field.setRestrictionFieldList(null);
        }
        if (tab.getEnumFieldList()!=null)
        for (it.anggen.model.field.EnumField enumField :tab.getEnumFieldList())

        {

        enumField.setTab(null);
        enumField.setEnumEntity(null);
        enumField.setEntity(null);
        enumField.setAnnotationList(null);
        }
    }

    private void rebuildSecurityMapping(it.anggen.model.entity.Tab tab) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.relationship.Relationship.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        tab.setRelationshipList(tabService.findById(tab.getTabId()).get(0).getRelationshipList());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        tab.setEntity(tabService.findById(tab.getTabId()).get(0).getEntity());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Field.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        tab.setFieldList(tabService.findById(tab.getTabId()).get(0).getFieldList());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.EnumField.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        tab.setEnumFieldList(tabService.findById(tab.getTabId()).get(0).getEnumFieldList());
    }

    private List<it.anggen.model.entity.Tab> getSecurityMapping(List<it.anggen.model.entity.Tab> tabList) {
        for (it.anggen.model.entity.Tab tab: tabList)
        {
        getSecurityMapping(tab);
        }
        return tabList;
    }

    private void getSecurityMapping(it.anggen.model.entity.Tab tab) {
        if (securityEnabled && tab.getRelationshipList()!=null && !securityService.hasPermission(it.anggen.model.relationship.Relationship.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        tab.setRelationshipList(null);

        if (securityEnabled && tab.getEntity()!=null  && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        tab.setEntity(null);

        if (securityEnabled && tab.getFieldList()!=null && !securityService.hasPermission(it.anggen.model.field.Field.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        tab.setFieldList(null);

        if (securityEnabled && tab.getEnumFieldList()!=null && !securityService.hasPermission(it.anggen.model.field.EnumField.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        tab.setEnumFieldList(null);

    }

}
