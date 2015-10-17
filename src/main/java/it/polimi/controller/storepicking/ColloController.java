
package it.polimi.controller.storepicking;

import java.util.List;
import it.polimi.model.storepicking.Collo;
import it.polimi.service.storepicking.ColloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/collo")
public class ColloController {

    @Autowired
    public ColloService colloService;

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "collo";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @RequestBody
        Collo collo) {
        List<Collo> colloList;
        colloList=colloService.find(collo);
        getRightMapping(colloList);
        return ResponseEntity.ok().body(colloList);
    }

    @ResponseBody
    @RequestMapping(value = "/{colloId}", method = RequestMethod.GET)
    public ResponseEntity getcolloById(
        @PathVariable
        String colloId) {
        List<Collo> colloList=colloService.findById(java.lang.Integer.valueOf(colloId));
        getRightMapping(colloList);
        return ResponseEntity.ok().body(colloList);
    }

    @ResponseBody
    @RequestMapping(value = "/{colloId}", method = RequestMethod.DELETE)
    public ResponseEntity deletecolloById(
        @PathVariable
        String colloId) {
        colloService.deleteById(java.lang.Integer.valueOf(colloId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertcollo(
        @RequestBody
        Collo collo) {
        Collo insertedcollo=colloService.insert(collo);
        getRightMapping(insertedcollo);
        return ResponseEntity.ok().body(insertedcollo);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updatecollo(
        @RequestBody
        Collo collo) {
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
        if (collo.getOrdine().getColloList()!=null)
        {
        collo.getOrdine().setColloList(null);
        }
        if (collo.getOrdine().getItemOrdineList()!=null)
        {
        for (it.polimi.model.storepicking.ItemOrdine itemOrdine : collo.getOrdine().getItemOrdineList())
        {
        if (itemOrdine.getOrdine()!=null)
        {
        //itemOrdine.getOrdine()
        itemOrdine.setOrdine(null);
        }
        if (itemOrdine.getItemOrdineCodiceList()!=null)
        {
        for (it.polimi.model.storepicking.ItemOrdineCodice itemOrdineCodice : itemOrdine.getItemOrdineCodiceList())
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

}
