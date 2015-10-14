
package it.polimi.service;

import java.util.List;
import it.polimi.model.ItemOrdine;
import it.polimi.repository.ItemOrdineCodiceRepository;
import it.polimi.repository.ItemOrdineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemOrdineServiceImpl
    implements ItemOrdineService
{

    @Autowired
    public ItemOrdineRepository itemOrdineRepository;
    @Autowired
    public ItemOrdineCodiceRepository itemOrdineCodiceRepository;

    @Override
    public List<ItemOrdine> findById(Integer itemOrdineId) {
        return itemOrdineRepository.findByItemOrdineId(itemOrdineId);
    }

    @Override
    public List<ItemOrdine> find(ItemOrdine itemOrdine) {
        return itemOrdineRepository.findByItemOrdineIdAndBarcodeAndCarfCanaleLocaleAndDaCancellareAndFormatAndIdStatoFeedbackAndIdStatoItemOrdineAndIdStatoPreparazioneAndInfoFeedbackAndNameAndNoteAndPrioritaAndProductStatusAndPromoPuntiAndQuantityFinaleAndQuantityInizAndSingleUnitWeightAndSostituitoAndTsModificaAndTsPreparazioneAndVOrderItemIdAndVPriceAndVProductIdAndWeightFinaleAndWeightInizAndOrdineAndItemOrdineCodice(itemOrdine.getItemOrdineId(),itemOrdine.getBarcode(),itemOrdine.getCarfCanaleLocale(),itemOrdine.getDaCancellare(),itemOrdine.getFormat(),itemOrdine.getIdStatoFeedback(),itemOrdine.getIdStatoItemOrdine(),itemOrdine.getIdStatoPreparazione(),itemOrdine.getInfoFeedback(),itemOrdine.getName(),itemOrdine.getNote(),itemOrdine.getPriorita(),itemOrdine.getProductStatus(),itemOrdine.getPromoPunti(),itemOrdine.getQuantityFinale(),itemOrdine.getQuantityIniz(),itemOrdine.getSingleUnitWeight(),itemOrdine.getSostituito(),it.polimi.utils.Utility.formatDate(itemOrdine.getTsModifica()),it.polimi.utils.Utility.formatDate(itemOrdine.getTsPreparazione()),itemOrdine.getVOrderItemId(),itemOrdine.getVPrice(),itemOrdine.getVProductId(),itemOrdine.getWeightFinale(),itemOrdine.getWeightIniz(),itemOrdine.getOrdine(),itemOrdine.getItemOrdineCodiceList()==null? null :itemOrdine.getItemOrdineCodiceList().get(0));
    }

    @Override
    public void deleteById(Integer itemOrdineId) {
        itemOrdineRepository.delete(itemOrdineId);
        return;
    }

    @Override
    public ItemOrdine insert(ItemOrdine itemOrdine) {
        return itemOrdineRepository.save(itemOrdine);
    }

    @Override
    @Transactional
    public ItemOrdine update(ItemOrdine itemOrdine) {
        if (itemOrdine.getItemOrdineCodiceList()!=null)
        for (it.polimi.model.ItemOrdineCodice itemOrdineCodice: itemOrdine.getItemOrdineCodiceList())
        {
        itemOrdineCodice.setItemOrdine(itemOrdine);
        }
        ItemOrdine returnedItemOrdine=itemOrdineRepository.save(itemOrdine);
        if (itemOrdine.getOrdine()!=null)
        {
        List<ItemOrdine> itemOrdineList = itemOrdineRepository.findByOrdine( itemOrdine.getOrdine());
        if (!itemOrdineList.contains(returnedItemOrdine))
        itemOrdineList.add(returnedItemOrdine);
        returnedItemOrdine.getOrdine().setItemOrdineList(itemOrdineList);
        }
         return returnedItemOrdine;
    }

}
