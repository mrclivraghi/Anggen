
package it.polimi.controller.ospedale;

import java.util.List;
import it.polimi.model.ospedale.Ambulatorio;
import it.polimi.service.ospedale.AmbulatorioService;
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
@RequestMapping("/ambulatorio")
public class AmbulatorioController {

    @Autowired
    public AmbulatorioService ambulatorioService;
    private final static Logger log = LoggerFactory.getLogger(Ambulatorio.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "ambulatorio";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @RequestBody
        Ambulatorio ambulatorio) {
        List<Ambulatorio> ambulatorioList;
        if (ambulatorio.getAmbulatorioId()!=null)
         log.info("Searching ambulatorio like {}", ambulatorio.getAmbulatorioId());
        ambulatorioList=ambulatorioService.find(ambulatorio);
        getRightMapping(ambulatorioList);
         log.info("Search: returning {} ambulatorio.",ambulatorioList.size());
        return ResponseEntity.ok().body(ambulatorioList);
    }

    @ResponseBody
    @RequestMapping(value = "/{ambulatorioId}", method = RequestMethod.GET)
    public ResponseEntity getambulatorioById(
        @PathVariable
        String ambulatorioId) {
        log.info("Searching ambulatorio with id {}",ambulatorioId);
        List<Ambulatorio> ambulatorioList=ambulatorioService.findById(java.lang.Long.valueOf(ambulatorioId));
        getRightMapping(ambulatorioList);
         log.info("Search: returning {} ambulatorio.",ambulatorioList.size());
        return ResponseEntity.ok().body(ambulatorioList);
    }

    @ResponseBody
    @RequestMapping(value = "/{ambulatorioId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteambulatorioById(
        @PathVariable
        String ambulatorioId) {
        log.info("Deleting ambulatorio with id {}",ambulatorioId);
        ambulatorioService.deleteById(java.lang.Long.valueOf(ambulatorioId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertambulatorio(
        @RequestBody
        Ambulatorio ambulatorio) {
        log.info("Inserting ambulatorio like {}", ambulatorio.getAmbulatorioId());
        Ambulatorio insertedambulatorio=ambulatorioService.insert(ambulatorio);
        getRightMapping(insertedambulatorio);
        log.info("Inserted ambulatorio with id {}",insertedambulatorio.getAmbulatorioId());
        return ResponseEntity.ok().body(insertedambulatorio);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateambulatorio(
        @RequestBody
        Ambulatorio ambulatorio) {
        log.info("Updating ambulatorio with id {}",ambulatorio.getAmbulatorioId());
        Ambulatorio updatedambulatorio=ambulatorioService.update(ambulatorio);
        getRightMapping(updatedambulatorio);
        return ResponseEntity.ok().body(updatedambulatorio);
    }

    private List<Ambulatorio> getRightMapping(List<Ambulatorio> ambulatorioList) {
        for (Ambulatorio ambulatorio: ambulatorioList)
        {
        getRightMapping(ambulatorio);
        }
        return ambulatorioList;
    }

    private void getRightMapping(Ambulatorio ambulatorio) {
    }

}
