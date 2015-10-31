
package it.polimi.controller.ospedale;

import java.util.List;
import it.polimi.model.ospedale.Paziente;
import it.polimi.searchbean.ospedale.PazienteSearchBean;
import it.polimi.service.ospedale.PazienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/paziente")
public class PazienteController {

    @Autowired
    public PazienteService pazienteService;
    private final static Logger log = LoggerFactory.getLogger(Paziente.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "paziente";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @RequestBody
        PazienteSearchBean paziente) {
        List<Paziente> pazienteList;
        if (paziente.getPazienteId()!=null)
         log.info("Searching paziente like {}", paziente.getPazienteId());
        pazienteList=pazienteService.find(paziente);
        getRightMapping(pazienteList);
         log.info("Search: returning {} paziente.",pazienteList.size());
        return ResponseEntity.ok().body(pazienteList);
    }

    @ResponseBody
    @RequestMapping(value = "/{pazienteId}", method = RequestMethod.GET)
    public ResponseEntity getpazienteById(
        @PathVariable
        String pazienteId) {
        log.info("Searching paziente with id {}",pazienteId);
        List<Paziente> pazienteList=pazienteService.findById(java.lang.Long.valueOf(pazienteId));
        getRightMapping(pazienteList);
         log.info("Search: returning {} paziente.",pazienteList.size());
        return ResponseEntity.ok().body(pazienteList);
    }

    @ResponseBody
    @RequestMapping(value = "/{pazienteId}", method = RequestMethod.DELETE)
    public ResponseEntity deletepazienteById(
        @PathVariable
        String pazienteId) {
        log.info("Deleting paziente with id {}",pazienteId);
        pazienteService.deleteById(java.lang.Long.valueOf(pazienteId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertpaziente(
        @RequestBody
        Paziente paziente) {
        log.info("Inserting paziente like {}", paziente.getPazienteId());
        Paziente insertedpaziente=pazienteService.insert(paziente);
        getRightMapping(insertedpaziente);
        log.info("Inserted paziente with id {}",insertedpaziente.getPazienteId());
        return ResponseEntity.ok().body(insertedpaziente);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updatepaziente(
        @RequestBody
        Paziente paziente) {
        log.info("Updating paziente with id {}",paziente.getPazienteId());
        Paziente updatedpaziente=pazienteService.update(paziente);
        getRightMapping(updatedpaziente);
        return ResponseEntity.ok().body(updatedpaziente);
    }

    private List<Paziente> getRightMapping(List<Paziente> pazienteList) {
        for (Paziente paziente: pazienteList)
        {
        getRightMapping(paziente);
        }
        return pazienteList;
    }

    private void getRightMapping(Paziente paziente) {
        if (paziente.getFascicoloList()!=null)
        for (it.polimi.model.ospedale.Fascicolo fascicolo :paziente.getFascicoloList())

        {

        fascicolo.setPaziente(null);
        fascicolo.setAmbulatorio(null);
        }
        if (paziente.getAmbulatorioList()!=null)
        for (it.polimi.model.ospedale.Ambulatorio ambulatorio :paziente.getAmbulatorioList())

        {

        ambulatorio.setPazienteList(null);
        }
    }

}
