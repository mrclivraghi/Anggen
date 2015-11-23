
package it.polimi.controller.domain;

import java.util.List;
import it.polimi.model.domain.EnumField;
import it.polimi.searchbean.domain.EnumFieldSearchBean;
import it.polimi.service.domain.EnumFieldService;
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
@RequestMapping("/enumField")
public class EnumFieldController {

    @Autowired
    public EnumFieldService enumFieldService;
    private final static Logger log = LoggerFactory.getLogger(EnumField.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "enumField";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        EnumFieldSearchBean enumField) {
        List<EnumField> enumFieldList;
        if (enumField.getEnumFieldId()!=null)
         log.info("Searching enumField like {}",enumField.toString());
        enumFieldList=enumFieldService.find(enumField);
        getRightMapping(enumFieldList);
         log.info("Search: returning {} enumField.",enumFieldList.size());
        return ResponseEntity.ok().body(enumFieldList);
    }

    @ResponseBody
    @RequestMapping(value = "/{enumFieldId}", method = RequestMethod.GET)
    public ResponseEntity getenumFieldById(
        @PathVariable
        String enumFieldId) {
        log.info("Searching enumField with id {}",enumFieldId);
        List<EnumField> enumFieldList=enumFieldService.findById(java.lang.Long.valueOf(enumFieldId));
        getRightMapping(enumFieldList);
         log.info("Search: returning {} enumField.",enumFieldList.size());
        return ResponseEntity.ok().body(enumFieldList);
    }

    @ResponseBody
    @RequestMapping(value = "/{enumFieldId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteenumFieldById(
        @PathVariable
        String enumFieldId) {
        log.info("Deleting enumField with id {}",enumFieldId);
        enumFieldService.deleteById(java.lang.Long.valueOf(enumFieldId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertenumField(
        @org.springframework.web.bind.annotation.RequestBody
        EnumField enumField) {
        if (enumField.getEnumFieldId()!=null)
        log.info("Inserting enumField like {}",enumField.toString());
        EnumField insertedenumField=enumFieldService.insert(enumField);
        getRightMapping(insertedenumField);
        log.info("Inserted enumField with id {}",insertedenumField.getEnumFieldId());
        return ResponseEntity.ok().body(insertedenumField);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateenumField(
        @org.springframework.web.bind.annotation.RequestBody
        EnumField enumField) {
        log.info("Updating enumField with id {}",enumField.getEnumFieldId());
        EnumField updatedenumField=enumFieldService.update(enumField);
        getRightMapping(updatedenumField);
        return ResponseEntity.ok().body(updatedenumField);
    }

    private List<EnumField> getRightMapping(List<EnumField> enumFieldList) {
        for (EnumField enumField: enumFieldList)
        {
        getRightMapping(enumField);
        }
        return enumFieldList;
    }

    private void getRightMapping(EnumField enumField) {
        if (enumField.getEnumValueList()!=null)
        for (it.polimi.model.domain.EnumValue enumValue :enumField.getEnumValueList())

        {

        enumValue.setEnumField(null);
        }
        if (enumField.getEntity()!=null)
        {
        enumField.getEntity().setFieldList(null);
        enumField.getEntity().setRelationshipList(null);
        enumField.getEntity().setEnumFieldList(null);
        enumField.getEntity().setTabList(null);
        enumField.getEntity().setRestrictionList(null);
        }
        if (enumField.getAnnotationList()!=null)
        for (it.polimi.model.domain.Annotation annotation :enumField.getAnnotationList())

        {

        annotation.setAnnotationAttributeList(null);
        annotation.setField(null);
        annotation.setRelationship(null);
        annotation.setEnumField(null);
        }
        if (enumField.getTab()!=null)
        {
        enumField.getTab().setEntity(null);
        enumField.getTab().setFieldList(null);
        enumField.getTab().setRelationshipList(null);
        enumField.getTab().setEnumFieldList(null);
        }
    }

}
