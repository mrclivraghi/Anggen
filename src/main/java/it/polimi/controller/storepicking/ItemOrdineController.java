
package it.polimi.controller.storepicking;

import java.util.List;
import it.polimi.model.storepicking.ItemOrdine;
import it.polimi.service.storepicking.ItemOrdineService;
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
@RequestMapping("/itemOrdine")
public class ItemOrdineController {

    @Autowired
    public ItemOrdineService itemOrdineService;
    private final static Logger log = LoggerFactory.getLogger(ItemOrdine.class);

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
        if (itemOrdine.getItemOrdineId()!=null)
         log.info("Searching itemOrdine like {}",itemOrdine.toString());
        itemOrdineList=itemOrdineService.find(itemOrdine);
        getRightMapping(itemOrdineList);
         log.info("Search: returning {} itemOrdine.",itemOrdineList.size());
        return ResponseEntity.ok().body(itemOrdineList);
    }

    @ResponseBody
    @RequestMapping(value = "/{itemOrdineId}", method = RequestMethod.GET)
    public ResponseEntity getitemOrdineById(
        @PathVariable
        String itemOrdineId) {
        log.info("Searching itemOrdine with id {}",itemOrdineId);
        List<ItemOrdine> itemOrdineList=itemOrdineService.findById(java.lang.Integer.valueOf(itemOrdineId));
        getRightMapping(itemOrdineList);
         log.info("Search: returning {} itemOrdine.",itemOrdineList.size());
        return ResponseEntity.ok().body(itemOrdineList);
    }

    @ResponseBody
    @RequestMapping(value = "/{itemOrdineId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteitemOrdineById(
        @PathVariable
        String itemOrdineId) {
        log.info("Deleting itemOrdine with id {}",itemOrdineId);
        itemOrdineService.deleteById(java.lang.Integer.valueOf(itemOrdineId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertitemOrdine(
        @RequestBody
        ItemOrdine itemOrdine) {
        log.info("Inserting itemOrdine like {}",itemOrdine.toString());
        ItemOrdine inserteditemOrdine=itemOrdineService.insert(itemOrdine);
        getRightMapping(inserteditemOrdine);
        log.info("Inserted itemOrdine with id {}",inserteditemOrdine.getItemOrdineId());
        return ResponseEntity.ok().body(inserteditemOrdine);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateitemOrdine(
        @RequestBody
        ItemOrdine itemOrdine) {
        log.info("Updating itemOrdine with id {}",itemOrdine.getItemOrdineId());
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
