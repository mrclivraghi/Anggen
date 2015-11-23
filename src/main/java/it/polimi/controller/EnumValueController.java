
package it.polimi.controller;

import java.util.List;

import it.polimi.model.domain.EnumValue;
import it.polimi.searchbean.EnumValueSearchBean;
import it.polimi.service.EnumValueService;

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
@RequestMapping("/enumValue")
public class EnumValueController {

    @Autowired
    public EnumValueService enumValueService;
    private final static Logger log = LoggerFactory.getLogger(EnumValue.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "enumValue";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        EnumValueSearchBean enumValue) {
        List<EnumValue> enumValueList;
        if (enumValue.getEnumValueId()!=null)
         log.info("Searching enumValue like {}",enumValue.toString());
        enumValueList=enumValueService.find(enumValue);
        getRightMapping(enumValueList);
         log.info("Search: returning {} enumValue.",enumValueList.size());
        return ResponseEntity.ok().body(enumValueList);
    }

    @ResponseBody
    @RequestMapping(value = "/{enumValueId}", method = RequestMethod.GET)
    public ResponseEntity getenumValueById(
        @PathVariable
        String enumValueId) {
        log.info("Searching enumValue with id {}",enumValueId);
        List<EnumValue> enumValueList=enumValueService.findById(java.lang.Long.valueOf(enumValueId));
        getRightMapping(enumValueList);
         log.info("Search: returning {} enumValue.",enumValueList.size());
        return ResponseEntity.ok().body(enumValueList);
    }

    @ResponseBody
    @RequestMapping(value = "/{enumValueId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteenumValueById(
        @PathVariable
        String enumValueId) {
        log.info("Deleting enumValue with id {}",enumValueId);
        enumValueService.deleteById(java.lang.Long.valueOf(enumValueId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertenumValue(
        @org.springframework.web.bind.annotation.RequestBody
        EnumValue enumValue) {
        if (enumValue.getEnumValueId()!=null)
        log.info("Inserting enumValue like {}",enumValue.toString());
        EnumValue insertedenumValue=enumValueService.insert(enumValue);
        getRightMapping(insertedenumValue);
        log.info("Inserted enumValue with id {}",insertedenumValue.getEnumValueId());
        return ResponseEntity.ok().body(insertedenumValue);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateenumValue(
        @org.springframework.web.bind.annotation.RequestBody
        EnumValue enumValue) {
        log.info("Updating enumValue with id {}",enumValue.getEnumValueId());
        EnumValue updatedenumValue=enumValueService.update(enumValue);
        getRightMapping(updatedenumValue);
        return ResponseEntity.ok().body(updatedenumValue);
    }

    private List<EnumValue> getRightMapping(List<EnumValue> enumValueList) {
        for (EnumValue enumValue: enumValueList)
        {
        getRightMapping(enumValue);
        }
        return enumValueList;
    }

    private void getRightMapping(EnumValue enumValue) {
        if (enumValue.getEnumField()!=null)
        {
        enumValue.getEnumField().setEnumValueList(null);
        enumValue.getEnumField().setEntity(null);
        enumValue.getEnumField().setAnnotationList(null);
        enumValue.getEnumField().setTab(null);
        }
    }

}
