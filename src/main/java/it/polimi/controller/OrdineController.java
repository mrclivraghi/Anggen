
package it.polimi.controller;

import java.util.List;
import it.polimi.model.Ordine;
import it.polimi.service.OrdineService;
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
        ordineList=ordineService.find(ordine);
        getRightMapping(ordineList);
        return ResponseEntity.ok().body(ordineList);
    }

    @ResponseBody
    @RequestMapping(value = "/{ordineId}", method = RequestMethod.GET)
    public ResponseEntity getordineById(
        @PathVariable
        String ordineId) {
        List<Ordine> ordineList=ordineService.findById(java.lang.Integer.valueOf(ordineId));
        getRightMapping(ordineList);
        return ResponseEntity.ok().body(ordineList);
    }

    @ResponseBody
    @RequestMapping(value = "/{ordineId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteordineById(
        @PathVariable
        String ordineId) {
        ordineService.deleteById(java.lang.Integer.valueOf(ordineId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertordine(
        @RequestBody
        Ordine ordine) {
        Ordine insertedordine=ordineService.insert(ordine);
        getRightMapping(insertedordine);
        return ResponseEntity.ok().body(insertedordine);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateordine(
        @RequestBody
        Ordine ordine) {
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
        {
        for (it.polimi.model.Collo collo : ordine.getColloList())
        {
        if (collo.getOrdine()!=null)
        {
        //collo.getOrdine()
        collo.setOrdine(null);
        }
        }
        }
        if (ordine.getItemOrdineList()!=null)
        {
        for (it.polimi.model.ItemOrdine itemOrdine : ordine.getItemOrdineList())
        {
        if (itemOrdine.getOrdine()!=null)
        {
        //itemOrdine.getOrdine()
        itemOrdine.setOrdine(null);
        }
        if (itemOrdine.getItemOrdineCodiceList()!=null)
        {
        for (it.polimi.model.ItemOrdineCodice itemOrdineCodice : itemOrdine.getItemOrdineCodiceList())
        {
        if (itemOrdineCodice.getItemOrdine()!=null)
        {
        //itemOrdineCodice.getItemOrdine()
        itemOrdineCodice.setItemOrdine(null);
        }
        }
        }
        }
        }
    }

}
