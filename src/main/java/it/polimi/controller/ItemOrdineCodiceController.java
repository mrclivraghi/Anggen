
package it.polimi.controller;

import java.util.List;
import it.polimi.model.ItemOrdineCodice;
import it.polimi.service.ItemOrdineCodiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/itemOrdineCodice")
public class ItemOrdineCodiceController {

    @Autowired
    public ItemOrdineCodiceService itemOrdineCodiceService;

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "itemOrdineCodice";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @RequestBody
        ItemOrdineCodice itemOrdineCodice) {
        List<ItemOrdineCodice> itemOrdineCodiceList;
        itemOrdineCodiceList=itemOrdineCodiceService.find(itemOrdineCodice);
        getRightMapping(itemOrdineCodiceList);
        return ResponseEntity.ok().body(itemOrdineCodiceList);
    }

    @ResponseBody
    @RequestMapping(value = "/{itemOrdineCodiceId}", method = RequestMethod.GET)
    public ResponseEntity getitemOrdineCodiceById(
        @PathVariable
        String itemOrdineCodiceId) {
        List<ItemOrdineCodice> itemOrdineCodiceList=itemOrdineCodiceService.findById(java.lang.Integer.valueOf(itemOrdineCodiceId));
        getRightMapping(itemOrdineCodiceList);
        return ResponseEntity.ok().body(itemOrdineCodiceList);
    }

    @ResponseBody
    @RequestMapping(value = "/{itemOrdineCodiceId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteitemOrdineCodiceById(
        @PathVariable
        String itemOrdineCodiceId) {
        itemOrdineCodiceService.deleteById(java.lang.Integer.valueOf(itemOrdineCodiceId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertitemOrdineCodice(
        @RequestBody
        ItemOrdineCodice itemOrdineCodice) {
        ItemOrdineCodice inserteditemOrdineCodice=itemOrdineCodiceService.insert(itemOrdineCodice);
        getRightMapping(inserteditemOrdineCodice);
        return ResponseEntity.ok().body(inserteditemOrdineCodice);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateitemOrdineCodice(
        @RequestBody
        ItemOrdineCodice itemOrdineCodice) {
        ItemOrdineCodice updateditemOrdineCodice=itemOrdineCodiceService.update(itemOrdineCodice);
        getRightMapping(updateditemOrdineCodice);
        return ResponseEntity.ok().body(updateditemOrdineCodice);
    }

    private List<ItemOrdineCodice> getRightMapping(List<ItemOrdineCodice> itemOrdineCodiceList) {
        for (ItemOrdineCodice itemOrdineCodice: itemOrdineCodiceList)
        {
        getRightMapping(itemOrdineCodice);
        }
        return itemOrdineCodiceList;
    }

    private void getRightMapping(ItemOrdineCodice itemOrdineCodice) {
        if (itemOrdineCodice.getItemOrdine()!=null)
        {
        if (itemOrdineCodice.getItemOrdine().getOrdine()!=null)
        {
        if (itemOrdineCodice.getItemOrdine().getOrdine().getColloList()!=null)
        {
        for (it.polimi.model.Collo collo : itemOrdineCodice.getItemOrdine().getOrdine().getColloList())
        {
        if (collo.getOrdine()!=null)
        {
        //collo.getOrdine()
        collo.setOrdine(null);
        }
        }
        }
        if (itemOrdineCodice.getItemOrdine().getOrdine().getItemOrdineList()!=null)
        {
        itemOrdineCodice.getItemOrdine().getOrdine().setItemOrdineList(null);
        }
        }
        if (itemOrdineCodice.getItemOrdine().getItemOrdineCodiceList()!=null)
        {
        itemOrdineCodice.getItemOrdine().setItemOrdineCodiceList(null);
        }
        }
    }

}
