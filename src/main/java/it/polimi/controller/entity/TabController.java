
package it.polimi.controller.entity;

import java.util.List;

import it.polimi.model.RestrictionType;
import it.polimi.searchbean.entity.TabSearchBean;
import it.polimi.security.SecurityService;
import it.polimi.service.entity.TabService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final static Logger log = LoggerFactory.getLogger(it.polimi.model.entity.Tab.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (!securityService.hasPermission(it.polimi.model.entity.Tab.staticEntityId, RestrictionType.SEARCH)) 
return "forbidden"; 

        return "tab";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        TabSearchBean tab) {
        if (!securityService.hasPermission(it.polimi.model.entity.Tab.staticEntityId, RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.polimi.model.entity.Tab> tabList;
        if (tab.getTabId()!=null)
         log.info("Searching tab like {}",tab.toString());
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
        if (!securityService.hasPermission(it.polimi.model.entity.Tab.staticEntityId, RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Searching tab with id {}",tabId);
        List<it.polimi.model.entity.Tab> tabList=tabService.findById(Long.valueOf(tabId));
        getRightMapping(tabList);
         log.info("Search: returning {} tab.",tabList.size());
        return ResponseEntity.ok().body(tabList);
    }

    @ResponseBody
    @RequestMapping(value = "/{tabId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTabById(
        @PathVariable
        String tabId) {
        if (!securityService.hasPermission(it.polimi.model.entity.Tab.staticEntityId, RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting tab with id {}",tabId);
        tabService.deleteById(Long.valueOf(tabId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertTab(
        @org.springframework.web.bind.annotation.RequestBody
        it.polimi.model.entity.Tab tab) {
        if (!securityService.hasPermission(it.polimi.model.entity.Tab.staticEntityId, RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (tab.getTabId()!=null)
        log.info("Inserting tab like {}",tab.toString());
        it.polimi.model.entity.Tab insertedTab=tabService.insert(tab);
        getRightMapping(insertedTab);
        log.info("Inserted tab with id {}",insertedTab.getTabId());
        return ResponseEntity.ok().body(insertedTab);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateTab(
        @org.springframework.web.bind.annotation.RequestBody
        it.polimi.model.entity.Tab tab) {
        if (!securityService.hasPermission(it.polimi.model.entity.Tab.staticEntityId, RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Updating tab with id {}",tab.getTabId());
        rebuildSecurityMapping(tab);
        it.polimi.model.entity.Tab updatedTab=tabService.update(tab);
        getRightMapping(updatedTab);
        getSecurityMapping(updatedTab);
        return ResponseEntity.ok().body(updatedTab);
    }

    private List<it.polimi.model.entity.Tab> getRightMapping(List<it.polimi.model.entity.Tab> tabList) {
        for (it.polimi.model.entity.Tab tab: tabList)
        {
        getRightMapping(tab);
        }
        return tabList;
    }

    private void getRightMapping(it.polimi.model.entity.Tab tab) {
        if (tab.getEntity()!=null)
        {
        tab.getEntity().setRelationshipList(null);
        tab.getEntity().setEnumFieldList(null);
        tab.getEntity().setTabList(null);
        tab.getEntity().setRestrictionEntityList(null);
        tab.getEntity().setEntityGroup(null);
        tab.getEntity().setFieldList(null);
        }
        if (tab.getFieldList()!=null)
        for (it.polimi.model.field.Field field :tab.getFieldList())

        {

        field.setEntity(null);
        field.setAnnotationList(null);
        field.setRestrictionFieldList(null);
        field.setTab(null);
        }
        if (tab.getRelationshipList()!=null)
        for (it.polimi.model.relationship.Relationship relationship :tab.getRelationshipList())

        {

        relationship.setEntity(null);
        relationship.setEntity(null);
        relationship.setAnnotationList(null);
        relationship.setTab(null);
        }
        if (tab.getEnumFieldList()!=null)
        for (it.polimi.model.field.EnumField enumField :tab.getEnumFieldList())

        {

        enumField.setEnumValueList(null);
        enumField.setEntity(null);
        enumField.setAnnotationList(null);
        enumField.setTab(null);
        }
    }

    private void rebuildSecurityMapping(it.polimi.model.entity.Tab tab) {
        if (!securityService.hasPermission(it.polimi.model.entity.Entity.staticEntityId, RestrictionType.SEARCH))
        tab.setEntity(tabService.findById(tab.getTabId()).get(0).getEntity());
        if (!securityService.hasPermission(it.polimi.model.field.Field.staticEntityId, RestrictionType.SEARCH))
        tab.setFieldList(tabService.findById(tab.getTabId()).get(0).getFieldList());
        if (!securityService.hasPermission(it.polimi.model.relationship.Relationship.staticEntityId, RestrictionType.SEARCH))
        tab.setRelationshipList(tabService.findById(tab.getTabId()).get(0).getRelationshipList());
        if (!securityService.hasPermission(it.polimi.model.field.EnumField.staticEntityId, RestrictionType.SEARCH))
        tab.setEnumFieldList(tabService.findById(tab.getTabId()).get(0).getEnumFieldList());
    }

    private List<it.polimi.model.entity.Tab> getSecurityMapping(List<it.polimi.model.entity.Tab> tabList) {
        for (it.polimi.model.entity.Tab tab: tabList)
        {
        getSecurityMapping(tab);
        }
        return tabList;
    }

    private void getSecurityMapping(it.polimi.model.entity.Tab tab) {
        if (tab.getEntity()!=null  && !securityService.hasPermission(it.polimi.model.entity.Entity.staticEntityId, RestrictionType.SEARCH) )
        tab.setEntity(null);

        if (tab.getFieldList()!=null && !securityService.hasPermission(it.polimi.model.field.Field.staticEntityId, RestrictionType.SEARCH) )
        tab.setFieldList(null);

        if (tab.getRelationshipList()!=null && !securityService.hasPermission(it.polimi.model.relationship.Relationship.staticEntityId, RestrictionType.SEARCH) )
        tab.setRelationshipList(null);

        if (tab.getEnumFieldList()!=null && !securityService.hasPermission(it.polimi.model.field.EnumField.staticEntityId, RestrictionType.SEARCH) )
        tab.setEnumFieldList(null);

    }

}
