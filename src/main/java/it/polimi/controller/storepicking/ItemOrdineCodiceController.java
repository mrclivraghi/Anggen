
package it.polimi.controller.storepicking;

import java.util.List;
import it.polimi.model.storepicking.ItemOrdineCodice;
import it.polimi.service.storepicking.ItemOrdineCodiceService;
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
@RequestMapping("/itemOrdineCodice")
public class ItemOrdineCodiceController {

    @Autowired
    public ItemOrdineCodiceService itemOrdineCodiceService;
    private final static Logger log = LoggerFactory.getLogger(ItemOrdineCodice.class);

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
        if (itemOrdineCodice.getItemOrdineCodiceId()!=null)
         log.info("Searching itemOrdineCodice like {}",itemOrdineCodice.toString());
        itemOrdineCodiceList=itemOrdineCodiceService.find(itemOrdineCodice);
        getRightMapping(itemOrdineCodiceList);
         log.info("Search: returning {} itemOrdineCodice.",itemOrdineCodiceList.size());
        return ResponseEntity.ok().body(itemOrdineCodiceList);
    }

    @ResponseBody
    @RequestMapping(value = "/{itemOrdineCodiceId}", method = RequestMethod.GET)
    public ResponseEntity getitemOrdineCodiceById(
        @PathVariable
        String itemOrdineCodiceId) {
        log.info("Searching itemOrdineCodice with id {}",itemOrdineCodiceId);
        List<ItemOrdineCodice> itemOrdineCodiceList=itemOrdineCodiceService.findById(java.lang.Integer.valueOf(itemOrdineCodiceId));
        getRightMapping(itemOrdineCodiceList);
         log.info("Search: returning {} itemOrdineCodice.",itemOrdineCodiceList.size());
        return ResponseEntity.ok().body(itemOrdineCodiceList);
    }

    @ResponseBody
    @RequestMapping(value = "/{itemOrdineCodiceId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteitemOrdineCodiceById(
        @PathVariable
        String itemOrdineCodiceId) {
        log.info("Deleting itemOrdineCodice with id {}",itemOrdineCodiceId);
        itemOrdineCodiceService.deleteById(java.lang.Integer.valueOf(itemOrdineCodiceId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertitemOrdineCodice(
        @RequestBody
        ItemOrdineCodice itemOrdineCodice) {
        log.info("Inserting itemOrdineCodice like {}",itemOrdineCodice.toString());
        ItemOrdineCodice inserteditemOrdineCodice=itemOrdineCodiceService.insert(itemOrdineCodice);
        getRightMapping(inserteditemOrdineCodice);
        log.info("Inserted itemOrdineCodice with id {}",inserteditemOrdineCodice.getItemOrdineCodiceId());
        return ResponseEntity.ok().body(inserteditemOrdineCodice);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateitemOrdineCodice(
        @RequestBody
        ItemOrdineCodice itemOrdineCodice) {
        log.info("Updating itemOrdineCodice with id {}",itemOrdineCodice.getItemOrdineCodiceId());
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
        itemOrdineCodice.getItemOrdine().setOrdine(null);
        itemOrdineCodice.getItemOrdine().setItemOrdineCodiceList(null);
        }
    }

}
