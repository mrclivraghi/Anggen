
package it.polimi.service;

import java.util.List;
import it.polimi.model.Ordine;
import it.polimi.repository.ColloRepository;
import it.polimi.repository.ItemOrdineRepository;
import it.polimi.repository.OrdineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrdineServiceImpl
    implements OrdineService
{

    @Autowired
    public OrdineRepository ordineRepository;
    @Autowired
    public ColloRepository colloRepository;
    @Autowired
    public ItemOrdineRepository itemOrdineRepository;

    @Override
    public List<Ordine> findById(Integer ordineId) {
        return ordineRepository.findByOrdineId(ordineId);
    }

    @Override
    public List<Ordine> find(Ordine ordine) {
        return ordineRepository.findByOrdineIdAndBillFileAndCittaSpedizioneAndCityIdAndCutoffTimeAndEmailSpedizioneAndFatturaAndInfoFeedbackAndInviatoCassaAndNomeSpedizioneAndNoteAndPickupTimeAndRiapertoAndRimandatoAltraDataAndSentSucceededAndStatusPreBillAndStatusPreBillErrAndSuborderStatusIdAndTelSpedizioneAndTigrosCardAndTimeslotBeginAndTimeslotDateAndTimeslotEndAndTipoSacchettoAndTotaleAndTsCaricamentoCamionAndTsFinePreparazioneAndTsInizioPreparazioneAndTsInvioFeedbackAndTsModificaAndTsRicevutoAndTsStartPaymentAndVOrderIdAndVSuborderIdAndViaSpedizioneAndZoneGroupIdAndZoneIdAndAddressNameAndDoorBellNameAndDeliveryCityAndAddress1AndPostalCodeAndProvinceAndDoorBellNumberAndAddress2AndReferencePhoneAndLatitudeAndLongitudeAndColloAndItemOrdine(ordine.getOrdineId(),ordine.getBillFile(),ordine.getCittaSpedizione(),ordine.getCityId(),it.polimi.utils.Utility.formatDate(ordine.getCutoffTime()),ordine.getEmailSpedizione(),ordine.getFattura(),ordine.getInfoFeedback(),ordine.getInviatoCassa(),ordine.getNomeSpedizione(),ordine.getNote(),it.polimi.utils.Utility.formatDate(ordine.getPickupTime()),ordine.getRiaperto(),ordine.getRimandatoAltraData(),ordine.getSentSucceeded(),ordine.getStatusPreBill(),ordine.getStatusPreBillErr(),ordine.getSuborderStatusId(),ordine.getTelSpedizione(),ordine.getTigrosCard(),it.polimi.utils.Utility.formatDate(ordine.getTimeslotBegin()),it.polimi.utils.Utility.formatDate(ordine.getTimeslotDate()),it.polimi.utils.Utility.formatDate(ordine.getTimeslotEnd()),ordine.getTipoSacchetto(),ordine.getTotale(),it.polimi.utils.Utility.formatDate(ordine.getTsCaricamentoCamion()),it.polimi.utils.Utility.formatDate(ordine.getTsFinePreparazione()),it.polimi.utils.Utility.formatDate(ordine.getTsInizioPreparazione()),it.polimi.utils.Utility.formatDate(ordine.getTsInvioFeedback()),it.polimi.utils.Utility.formatDate(ordine.getTsModifica()),it.polimi.utils.Utility.formatDate(ordine.getTsRicevuto()),it.polimi.utils.Utility.formatDate(ordine.getTsStartPayment()),ordine.getVOrderId(),ordine.getVSuborderId(),ordine.getViaSpedizione(),ordine.getZoneGroupId(),ordine.getZoneId(),ordine.getAddressName(),ordine.getDoorBellName(),ordine.getDeliveryCity(),ordine.getAddress1(),ordine.getPostalCode(),ordine.getProvince(),ordine.getDoorBellNumber(),ordine.getAddress2(),ordine.getReferencePhone(),ordine.getLatitude(),ordine.getLongitude(),ordine.getColloList()==null? null :ordine.getColloList().get(0),ordine.getItemOrdineList()==null? null :ordine.getItemOrdineList().get(0));
    }

    @Override
    public void deleteById(Integer ordineId) {
        ordineRepository.delete(ordineId);
        return;
    }

    @Override
    public Ordine insert(Ordine ordine) {
        return ordineRepository.save(ordine);
    }

    @Override
    @Transactional
    public Ordine update(Ordine ordine) {
        if (ordine.getColloList()!=null)
        for (it.polimi.model.Collo collo: ordine.getColloList())
        {
        collo.setOrdine(ordine);
        }
        if (ordine.getItemOrdineList()!=null)
        for (it.polimi.model.ItemOrdine itemOrdine: ordine.getItemOrdineList())
        {
        itemOrdine.setOrdine(ordine);
        }
        Ordine returnedOrdine=ordineRepository.save(ordine);
         return returnedOrdine;
    }

}