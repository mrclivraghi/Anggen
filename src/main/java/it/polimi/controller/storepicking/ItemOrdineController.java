
package it.polimi.controller.storepicking;

import java.util.List;
import it.polimi.model.storepicking.ItemOrdine;
import it.polimi.service.storepicking.ItemOrdineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/itemOrdine")
public class ItemOrdineController {

    @Autowired
    public ItemOrdineService itemOrdineService;

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "itemOrdine";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @RequestBody
        ItemOrdine itemOrdine) {
        List<ItemOrdine> itemOrdineList;
        itemOrdineList=itemOrdineService.find(itemOrdine);
        getRightMapping(itemOrdineList);
        return ResponseEntity.ok().body(itemOrdineList);
    }

    @ResponseBody
    @RequestMapping(value = "/{itemOrdineId}", method = RequestMethod.GET)
    public ResponseEntity getitemOrdineById(
        @PathVariable
        String itemOrdineId) {
        List<ItemOrdine> itemOrdineList=itemOrdineService.findById(java.lang.Integer.valueOf(itemOrdineId));
        getRightMapping(itemOrdineList);
        return ResponseEntity.ok().body(itemOrdineList);
    }

    @ResponseBody
    @RequestMapping(value = "/{itemOrdineId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteitemOrdineById(
        @PathVariable
        String itemOrdineId) {
        itemOrdineService.deleteById(java.lang.Integer.valueOf(itemOrdineId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertitemOrdine(
        @RequestBody
        ItemOrdine itemOrdine) {
        ItemOrdine inserteditemOrdine=itemOrdineService.insert(itemOrdine);
        getRightMapping(inserteditemOrdine);
        return ResponseEntity.ok().body(inserteditemOrdine);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateitemOrdine(
        @RequestBody
        ItemOrdine itemOrdine) {
        ItemOrdine updateditemOrdine=itemOrdineService.update(itemOrdine);
        getRightMapping(updateditemOrdine);
        return ResponseEntity.ok().body(updateditemOrdine);
    }

    private List<ItemOrdine> getRightMapping(List<ItemOrdine> itemOrdineList) {
        for (ItemOrdine itemOrdine: itemOrdineList)
        {
        getRightMapping(itemOrdine);
        }
        return itemOrdineList;
    }

    private void getRightMapping(ItemOrdine itemOrdine) {
        if (itemOrdine.getOrdine()!=null)
        {
        itemOrdine.getOrdine().setColloList(null);
        itemOrdine.getOrdine().setItemOrdineList(null);
        }
        if (itemOrdine.getItemOrdineCodiceList()!=null)
        for (it.polimi.model.storepicking.ItemOrdineCodice itemOrdineCodice :itemOrdine.getItemOrdineCodiceList())

        {

        itemOrdineCodice.setItemOrdine(null);
        }
    }

}
