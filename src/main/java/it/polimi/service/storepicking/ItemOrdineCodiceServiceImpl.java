
package it.polimi.service.storepicking;

import java.util.List;
import it.polimi.model.storepicking.ItemOrdineCodice;
import it.polimi.repository.storepicking.ItemOrdineCodiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemOrdineCodiceServiceImpl
    implements ItemOrdineCodiceService
{

    @Autowired
    public ItemOrdineCodiceRepository itemOrdineCodiceRepository;

    @Override
    public List<ItemOrdineCodice> findById(Integer itemOrdineCodiceId) {
        return itemOrdineCodiceRepository.findByItemOrdineCodiceId(itemOrdineCodiceId);
    }

    @Override
    public List<ItemOrdineCodice> find(ItemOrdineCodice itemOrdineCodice) {
        return itemOrdineCodiceRepository.findByItemOrdineCodiceIdAndBarcodeAndBarcodeReadAndDaCancellareAndIdStatoAndNameAndNelCarrelloAndQuantityFinaleAndTsModificaAndVPriceAndVProductIdAndWeightFinaleAndItemOrdine(itemOrdineCodice.getItemOrdineCodiceId(),itemOrdineCodice.getBarcode(),itemOrdineCodice.getBarcodeRead(),itemOrdineCodice.getDaCancellare(),itemOrdineCodice.getIdStato(),itemOrdineCodice.getName(),itemOrdineCodice.getNelCarrello(),itemOrdineCodice.getQuantityFinale(),it.polimi.utils.Utility.formatDate(itemOrdineCodice.getTsModifica()),itemOrdineCodice.getVPrice(),itemOrdineCodice.getVProductId(),itemOrdineCodice.getWeightFinale(),itemOrdineCodice.getItemOrdine());
    }

    @Override
    public void deleteById(Integer itemOrdineCodiceId) {
        itemOrdineCodiceRepository.delete(itemOrdineCodiceId);
        return;
    }

    @Override
    public ItemOrdineCodice insert(ItemOrdineCodice itemOrdineCodice) {
        return itemOrdineCodiceRepository.save(itemOrdineCodice);
    }

    @Override
    @Transactional
    public ItemOrdineCodice update(ItemOrdineCodice itemOrdineCodice) {
        ItemOrdineCodice returnedItemOrdineCodice=itemOrdineCodiceRepository.save(itemOrdineCodice);
        if (itemOrdineCodice.getItemOrdine()!=null)
        {
        List<ItemOrdineCodice> itemOrdineCodiceList = itemOrdineCodiceRepository.findByItemOrdine( itemOrdineCodice.getItemOrdine());
        if (!itemOrdineCodiceList.contains(returnedItemOrdineCodice))
        itemOrdineCodiceList.add(returnedItemOrdineCodice);
        returnedItemOrdineCodice.getItemOrdine().setItemOrdineCodiceList(itemOrdineCodiceList);
        }
         return returnedItemOrdineCodice;
    }

}
