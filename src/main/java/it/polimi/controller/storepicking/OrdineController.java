
package it.polimi.controller.storepicking;

import java.util.List;
import it.polimi.model.storepicking.Ordine;
import it.polimi.service.storepicking.OrdineService;
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
@RequestMapping("/ordine")
public class OrdineController {

    @Autowired
    public OrdineService ordineService;
    private final static Logger log = LoggerFactory.getLogger(Ordine.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "ordine";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @RequestBody
        Ordine ordine) {
        List<Ordine> ordineList;
         log.info("Searching ordine like {}",ordine.toString());
        ordineList=ordineService.find(ordine);
        getRightMapping(ordineList);
         log.info("Search: returning {} ordine.",ordineList.size());
        return ResponseEntity.ok().body(ordineList);
    }

    @ResponseBody
    @RequestMapping(value = "/{ordineId}", method = RequestMethod.GET)
    public ResponseEntity getordineById(
        @PathVariable
        String ordineId) {
        log.info("Searching ordine with id {}",ordineId);
        List<Ordine> ordineList=ordineService.findById(java.lang.Integer.valueOf(ordineId));
        getRightMapping(ordineList);
         log.info("Search: returning {} ordine.",ordineList.size());
        return ResponseEntity.ok().body(ordineList);
    }

    @ResponseBody
    @RequestMapping(value = "/{ordineId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteordineById(
        @PathVariable
        String ordineId) {
        log.info("Deleting ordine with id {}",ordineId);
        ordineService.deleteById(java.lang.Integer.valueOf(ordineId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertordine(
        @RequestBody
        Ordine ordine) {
        log.info("Inserting ordine like {}",ordine.toString());
        Ordine insertedordine=ordineService.insert(ordine);
        getRightMapping(insertedordine);
        log.info("Inserted ordine with id {}",insertedordine.getOrdineId());
        return ResponseEntity.ok().body(insertedordine);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateordine(
        @RequestBody
        Ordine ordine) {
        log.info("Updating ordine with id {}",ordine.getOrdineId());
        Ordine updatedordine=ordineService.update(ordine);
        getRightMapping(updatedordine);
        return ResponseEntity.ok().body(updatedordine);
    }

    private List<Ordine> getRightMapping(List<Ordine> ordineList) {
        for (Ordine ordine: ordineList)
        {
        getRightMapping(ordine);
        }
        return ordineList;
    }

    private void getRightMapping(Ordine ordine) {
        if (ordine.getColloList()!=null)
        for (it.polimi.model.storepicking.Collo collo :ordine.getColloList())

        {

        collo.setOrdine(null);
        }
        if (ordine.getItemOrdineList()!=null)
        for (it.polimi.model.storepicking.ItemOrdine itemOrdine :ordine.getItemOrdineList())

        {

        itemOrdine.setOrdine(null);
        itemOrdine.setItemOrdineCodiceList(null);
        }
    }

}
