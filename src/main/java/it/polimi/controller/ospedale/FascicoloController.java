
package it.polimi.controller.ospedale;

import java.util.List;
import it.polimi.model.ospedale.Fascicolo;
import it.polimi.searchbean.ospedale.FascicoloSearchBean;
import it.polimi.service.ospedale.FascicoloService;
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
@RequestMapping("/fascicolo")
public class FascicoloController {

    @Autowired
    public FascicoloService fascicoloService;
    private final static Logger log = LoggerFactory.getLogger(Fascicolo.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "fascicolo";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        FascicoloSearchBean fascicolo) {
        List<Fascicolo> fascicoloList;
        if (fascicolo.getFascicoloId()!=null)
         log.info("Searching fascicolo like {}", fascicolo.getFascicoloId());
        fascicoloList=fascicoloService.find(fascicolo);
        getRightMapping(fascicoloList);
         log.info("Search: returning {} fascicolo.",fascicoloList.size());
        return ResponseEntity.ok().body(fascicoloList);
    }

    @ResponseBody
    @RequestMapping(value = "/{fascicoloId}", method = RequestMethod.GET)
    public ResponseEntity getfascicoloById(
        @PathVariable
        String fascicoloId) {
        log.info("Searching fascicolo with id {}",fascicoloId);
        List<Fascicolo> fascicoloList=fascicoloService.findById(java.lang.Long.valueOf(fascicoloId));
        getRightMapping(fascicoloList);
         log.info("Search: returning {} fascicolo.",fascicoloList.size());
        return ResponseEntity.ok().body(fascicoloList);
    }

    @ResponseBody
    @RequestMapping(value = "/{fascicoloId}", method = RequestMethod.DELETE)
    public ResponseEntity deletefascicoloById(
        @PathVariable
        String fascicoloId) {
        log.info("Deleting fascicolo with id {}",fascicoloId);
        fascicoloService.deleteById(java.lang.Long.valueOf(fascicoloId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertfascicolo(
        @org.springframework.web.bind.annotation.RequestBody
        Fascicolo fascicolo) {
        if (fascicolo.getFascicoloId()!=null)
        log.info("Inserting fascicolo like {}", fascicolo.getFascicoloId());
        Fascicolo insertedfascicolo=fascicoloService.insert(fascicolo);
        getRightMapping(insertedfascicolo);
        log.info("Inserted fascicolo with id {}",insertedfascicolo.getFascicoloId());
        return ResponseEntity.ok().body(insertedfascicolo);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updatefascicolo(
        @org.springframework.web.bind.annotation.RequestBody
        Fascicolo fascicolo) {
        log.info("Updating fascicolo with id {}",fascicolo.getFascicoloId());
        Fascicolo updatedfascicolo=fascicoloService.update(fascicolo);
        getRightMapping(updatedfascicolo);
        return ResponseEntity.ok().body(updatedfascicolo);
    }

    private List<Fascicolo> getRightMapping(List<Fascicolo> fascicoloList) {
        for (Fascicolo fascicolo: fascicoloList)
        {
        getRightMapping(fascicolo);
        }
        return fascicoloList;
    }

    private void getRightMapping(Fascicolo fascicolo) {
        if (fascicolo.getPaziente()!=null)
        {
        fascicolo.getPaziente().setFascicoloList(null);
        fascicolo.getPaziente().setAmbulatorioList(null);
        }
        if (fascicolo.getAmbulatorio()!=null)
        {
        fascicolo.getAmbulatorio().setPazienteList(null);
        }
    }

}
