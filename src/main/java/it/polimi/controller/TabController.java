
package it.polimi.controller;

import java.util.List;

import it.polimi.model.entity.Tab;
import it.polimi.searchbean.TabSearchBean;
import it.polimi.service.TabService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/tab")
public class TabController {

    @Autowired
    public TabService tabService;
    private final static Logger log = LoggerFactory.getLogger(Tab.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "tab";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        TabSearchBean tab) {
        List<Tab> tabList;
        if (tab.getTabId()!=null)
         log.info("Searching tab like {}",tab.toString());
        tabList=tabService.find(tab);
        getRightMapping(tabList);
         log.info("Search: returning {} tab.",tabList.size());
        return ResponseEntity.ok().body(tabList);
    }

    @ResponseBody
    @RequestMapping(value = "/{tabId}", method = RequestMethod.GET)
    public ResponseEntity gettabById(
        @PathVariable
        String tabId) {
        log.info("Searching tab with id {}",tabId);
        List<Tab> tabList=tabService.findById(java.lang.Long.valueOf(tabId));
        getRightMapping(tabList);
         log.info("Search: returning {} tab.",tabList.size());
        return ResponseEntity.ok().body(tabList);
    }

    @ResponseBody
    @RequestMapping(value = "/{tabId}", method = RequestMethod.DELETE)
    public ResponseEntity deletetabById(
        @PathVariable
        String tabId) {
        log.info("Deleting tab with id {}",tabId);
        tabService.deleteById(java.lang.Long.valueOf(tabId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity inserttab(
        @org.springframework.web.bind.annotation.RequestBody
        Tab tab) {
        if (tab.getTabId()!=null)
        log.info("Inserting tab like {}",tab.toString());
        Tab insertedtab=tabService.insert(tab);
        getRightMapping(insertedtab);
        log.info("Inserted tab with id {}",insertedtab.getTabId());
        return ResponseEntity.ok().body(insertedtab);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updatetab(
        @org.springframework.web.bind.annotation.RequestBody
        Tab tab) {
        log.info("Updating tab with id {}",tab.getTabId());
        Tab updatedtab=tabService.update(tab);
        getRightMapping(updatedtab);
        return ResponseEntity.ok().body(updatedtab);
    }

    private List<Tab> getRightMapping(List<Tab> tabList) {
        for (Tab tab: tabList)
        {
        getRightMapping(tab);
        }
        return tabList;
    }

    private void getRightMapping(Tab tab) {
        if (tab.getEntity()!=null)
        {
        tab.getEntity().setFieldList(null);
        tab.getEntity().setRelationshipList(null);
        tab.getEntity().setEnumFieldList(null);
        tab.getEntity().setTabList(null);
        tab.getEntity().setRestrictionEntityList(null);
        tab.getEntity().setEntityGroup(null);
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
        relationship.setEntityTarget(null);
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

}
