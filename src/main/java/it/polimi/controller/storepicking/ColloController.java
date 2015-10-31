
package it.polimi.controller.storepicking;

import java.util.List;
import it.polimi.model.storepicking.Collo;
import it.polimi.searchbean.storepicking.ColloSearchBean;
import it.polimi.service.storepicking.ColloService;
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
@RequestMapping("/collo")
public class ColloController {

    @Autowired
    public ColloService colloService;
    private final static Logger log = LoggerFactory.getLogger(Collo.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "collo";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        ColloSearchBean collo) {
        List<Collo> colloList;
        if (collo.getColloId()!=null)
         log.info("Searching collo like {}",collo.toString());
        colloList=colloService.find(collo);
        getRightMapping(colloList);
         log.info("Search: returning {} collo.",colloList.size());
        return ResponseEntity.ok().body(colloList);
    }

    @ResponseBody
    @RequestMapping(value = "/{colloId}", method = RequestMethod.GET)
    public ResponseEntity getcolloById(
        @PathVariable
        String colloId) {
        log.info("Searching collo with id {}",colloId);
        List<Collo> colloList=colloService.findById(java.lang.Integer.valueOf(colloId));
        getRightMapping(colloList);
         log.info("Search: returning {} collo.",colloList.size());
        return ResponseEntity.ok().body(colloList);
    }

    @ResponseBody
    @RequestMapping(value = "/{colloId}", method = RequestMethod.DELETE)
    public ResponseEntity deletecolloById(
        @PathVariable
        String colloId) {
        log.info("Deleting collo with id {}",colloId);
        colloService.deleteById(java.lang.Integer.valueOf(colloId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertcollo(
        @org.springframework.web.bind.annotation.RequestBody
        Collo collo) {
        log.info("Inserting collo like {}",collo.toString());
        Collo insertedcollo=colloService.insert(collo);
        getRightMapping(insertedcollo);
        log.info("Inserted collo with id {}",insertedcollo.getColloId());
        return ResponseEntity.ok().body(insertedcollo);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updatecollo(
        @org.springframework.web.bind.annotation.RequestBody
        Collo collo) {
        log.info("Updating collo with id {}",collo.getColloId());
        Collo updatedcollo=colloService.update(collo);
        getRightMapping(updatedcollo);
        return ResponseEntity.ok().body(updatedcollo);
    }

    private List<Collo> getRightMapping(List<Collo> colloList) {
        for (Collo collo: colloList)
        {
        getRightMapping(collo);
        }
        return colloList;
    }

    private void getRightMapping(Collo collo) {
        if (collo.getOrdine()!=null)
        {
        collo.getOrdine().setColloList(null);
        collo.getOrdine().setItemOrdineList(null);
        }
    }

}
