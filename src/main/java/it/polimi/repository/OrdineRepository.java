
package it.polimi.repository;

import java.math.BigDecimal;
import java.util.List;
import it.polimi.model.Collo;
import it.polimi.model.ItemOrdine;
import it.polimi.model.Ordine;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdineRepository
    extends CrudRepository<Ordine, Integer>
{


    public List<Ordine> findByOrdineId(Integer ordineId);

    public List<Ordine> findByBillFile(String billFile);

    public List<Ordine> findByCittaSpedizione(String cittaSpedizione);

    public List<Ordine> findByCityId(String cityId);

    public List<Ordine> findByCutoffTime(String cutoffTime);

    public List<Ordine> findByEmailSpedizione(String emailSpedizione);

    public List<Ordine> findByFattura(Boolean fattura);

    public List<Ordine> findByInfoFeedback(String infoFeedback);

    public List<Ordine> findByInviatoCassa(Boolean inviatoCassa);

    public List<Ordine> findByNomeSpedizione(String nomeSpedizione);

    public List<Ordine> findByNote(String note);

    public List<Ordine> findByPickupTime(String pickupTime);

    public List<Ordine> findByRiaperto(Boolean riaperto);

    public List<Ordine> findByRimandatoAltraData(Boolean rimandatoAltraData);

    public List<Ordine> findBySentSucceeded(Integer sentSucceeded);

    public List<Ordine> findByStatusPreBill(Integer statusPreBill);

    public List<Ordine> findByStatusPreBillErr(String statusPreBillErr);

    public List<Ordine> findBySuborderStatusId(String suborderStatusId);

    public List<Ordine> findByTelSpedizione(String telSpedizione);

    public List<Ordine> findByTigrosCard(String tigrosCard);

    public List<Ordine> findByTimeslotBegin(String timeslotBegin);

    public List<Ordine> findByTimeslotDate(String timeslotDate);

    public List<Ordine> findByTimeslotEnd(String timeslotEnd);

    public List<Ordine> findByTipoSacchetto(Integer tipoSacchetto);

    public List<Ordine> findByTotale(BigDecimal totale);

    public List<Ordine> findByTsCaricamentoCamion(String tsCaricamentoCamion);

    public List<Ordine> findByTsFinePreparazione(String tsFinePreparazione);

    public List<Ordine> findByTsInizioPreparazione(String tsInizioPreparazione);

    public List<Ordine> findByTsInvioFeedback(String tsInvioFeedback);

    public List<Ordine> findByTsModifica(String tsModifica);

    public List<Ordine> findByTsRicevuto(String tsRicevuto);

    public List<Ordine> findByTsStartPayment(String tsStartPayment);

    public List<Ordine> findByVOrderId(String vOrderId);

    public List<Ordine> findByVSuborderId(String vSuborderId);

    public List<Ordine> findByViaSpedizione(String viaSpedizione);

    public List<Ordine> findByZoneGroupId(String zoneGroupId);

    public List<Ordine> findByZoneId(String zoneId);

    public List<Ordine> findByAddressName(String addressName);

    public List<Ordine> findByDoorBellName(String doorBellName);

    public List<Ordine> findByDeliveryCity(String deliveryCity);

    public List<Ordine> findByAddress1(String address1);

    public List<Ordine> findByPostalCode(String postalCode);

    public List<Ordine> findByProvince(String province);

    public List<Ordine> findByDoorBellNumber(String doorBellNumber);

    public List<Ordine> findByAddress2(String address2);

    public List<Ordine> findByReferencePhone(String referencePhone);

    public List<Ordine> findByLatitude(BigDecimal latitude);

    public List<Ordine> findByLongitude(BigDecimal longitude);

    @Query("select o from Ordine o where  (:ordineId is null or cast(:ordineId as string)=cast(o.ordineId as string)) and (:billFile is null or :billFile='' or cast(:billFile as string)=o.billFile) and (:cittaSpedizione is null or :cittaSpedizione='' or cast(:cittaSpedizione as string)=o.cittaSpedizione) and (:cityId is null or :cityId='' or cast(:cityId as string)=o.cityId) and (:cutoffTime is null or cast(:cutoffTime as string)=cast(date(o.cutoffTime) as string)) and (:emailSpedizione is null or :emailSpedizione='' or cast(:emailSpedizione as string)=o.emailSpedizione) and (:fattura is null or cast(:fattura as string)=cast(o.fattura as string)) and (:infoFeedback is null or :infoFeedback='' or cast(:infoFeedback as string)=o.infoFeedback) and (:inviatoCassa is null or cast(:inviatoCassa as string)=cast(o.inviatoCassa as string)) and (:nomeSpedizione is null or :nomeSpedizione='' or cast(:nomeSpedizione as string)=o.nomeSpedizione) and (:note is null or :note='' or cast(:note as string)=o.note) and (:pickupTime is null or cast(:pickupTime as string)=cast(date(o.pickupTime) as string)) and (:riaperto is null or cast(:riaperto as string)=cast(o.riaperto as string)) and (:rimandatoAltraData is null or cast(:rimandatoAltraData as string)=cast(o.rimandatoAltraData as string)) and (:sentSucceeded is null or cast(:sentSucceeded as string)=cast(o.sentSucceeded as string)) and (:statusPreBill is null or cast(:statusPreBill as string)=cast(o.statusPreBill as string)) and (:statusPreBillErr is null or :statusPreBillErr='' or cast(:statusPreBillErr as string)=o.statusPreBillErr) and (:suborderStatusId is null or :suborderStatusId='' or cast(:suborderStatusId as string)=o.suborderStatusId) and (:telSpedizione is null or :telSpedizione='' or cast(:telSpedizione as string)=o.telSpedizione) and (:tigrosCard is null or :tigrosCard='' or cast(:tigrosCard as string)=o.tigrosCard) and (:timeslotBegin is null or cast(:timeslotBegin as string)=cast(date(o.timeslotBegin) as string)) and (:timeslotDate is null or cast(:timeslotDate as string)=cast(date(o.timeslotDate) as string)) and (:timeslotEnd is null or cast(:timeslotEnd as string)=cast(date(o.timeslotEnd) as string)) and (:tipoSacchetto is null or cast(:tipoSacchetto as string)=cast(o.tipoSacchetto as string)) and (:totale is null or cast(:totale as string)=cast(o.totale as string)) and (:tsCaricamentoCamion is null or cast(:tsCaricamentoCamion as string)=cast(date(o.tsCaricamentoCamion) as string)) and (:tsFinePreparazione is null or cast(:tsFinePreparazione as string)=cast(date(o.tsFinePreparazione) as string)) and (:tsInizioPreparazione is null or cast(:tsInizioPreparazione as string)=cast(date(o.tsInizioPreparazione) as string)) and (:tsInvioFeedback is null or cast(:tsInvioFeedback as string)=cast(date(o.tsInvioFeedback) as string)) and (:tsModifica is null or cast(:tsModifica as string)=cast(date(o.tsModifica) as string)) and (:tsRicevuto is null or cast(:tsRicevuto as string)=cast(date(o.tsRicevuto) as string)) and (:tsStartPayment is null or cast(:tsStartPayment as string)=cast(date(o.tsStartPayment) as string)) and (:vOrderId is null or :vOrderId='' or cast(:vOrderId as string)=o.vOrderId) and (:vSuborderId is null or :vSuborderId='' or cast(:vSuborderId as string)=o.vSuborderId) and (:viaSpedizione is null or :viaSpedizione='' or cast(:viaSpedizione as string)=o.viaSpedizione) and (:zoneGroupId is null or :zoneGroupId='' or cast(:zoneGroupId as string)=o.zoneGroupId) and (:zoneId is null or :zoneId='' or cast(:zoneId as string)=o.zoneId) and (:addressName is null or :addressName='' or cast(:addressName as string)=o.addressName) and (:doorBellName is null or :doorBellName='' or cast(:doorBellName as string)=o.doorBellName) and (:deliveryCity is null or :deliveryCity='' or cast(:deliveryCity as string)=o.deliveryCity) and (:address1 is null or :address1='' or cast(:address1 as string)=o.address1) and (:postalCode is null or :postalCode='' or cast(:postalCode as string)=o.postalCode) and (:province is null or :province='' or cast(:province as string)=o.province) and (:doorBellNumber is null or :doorBellNumber='' or cast(:doorBellNumber as string)=o.doorBellNumber) and (:address2 is null or :address2='' or cast(:address2 as string)=o.address2) and (:referencePhone is null or :referencePhone='' or cast(:referencePhone as string)=o.referencePhone) and (:latitude is null or cast(:latitude as string)=cast(o.latitude as string)) and (:longitude is null or cast(:longitude as string)=cast(o.longitude as string)) and (:collo in elements(o.colloList)  or :collo is null) and (:itemOrdine in elements(o.itemOrdineList)  or :itemOrdine is null) ")
    public List<Ordine> findByOrdineIdAndBillFileAndCittaSpedizioneAndCityIdAndCutoffTimeAndEmailSpedizioneAndFatturaAndInfoFeedbackAndInviatoCassaAndNomeSpedizioneAndNoteAndPickupTimeAndRiapertoAndRimandatoAltraDataAndSentSucceededAndStatusPreBillAndStatusPreBillErrAndSuborderStatusIdAndTelSpedizioneAndTigrosCardAndTimeslotBeginAndTimeslotDateAndTimeslotEndAndTipoSacchettoAndTotaleAndTsCaricamentoCamionAndTsFinePreparazioneAndTsInizioPreparazioneAndTsInvioFeedbackAndTsModificaAndTsRicevutoAndTsStartPaymentAndVOrderIdAndVSuborderIdAndViaSpedizioneAndZoneGroupIdAndZoneIdAndAddressNameAndDoorBellNameAndDeliveryCityAndAddress1AndPostalCodeAndProvinceAndDoorBellNumberAndAddress2AndReferencePhoneAndLatitudeAndLongitudeAndColloAndItemOrdine(
        @Param("ordineId")
        Integer ordineId,
        @Param("billFile")
        String billFile,
        @Param("cittaSpedizione")
        String cittaSpedizione,
        @Param("cityId")
        String cityId,
        @Param("cutoffTime")
        String cutoffTime,
        @Param("emailSpedizione")
        String emailSpedizione,
        @Param("fattura")
        Boolean fattura,
        @Param("infoFeedback")
        String infoFeedback,
        @Param("inviatoCassa")
        Boolean inviatoCassa,
        @Param("nomeSpedizione")
        String nomeSpedizione,
        @Param("note")
        String note,
        @Param("pickupTime")
        String pickupTime,
        @Param("riaperto")
        Boolean riaperto,
        @Param("rimandatoAltraData")
        Boolean rimandatoAltraData,
        @Param("sentSucceeded")
        Integer sentSucceeded,
        @Param("statusPreBill")
        Integer statusPreBill,
        @Param("statusPreBillErr")
        String statusPreBillErr,
        @Param("suborderStatusId")
        String suborderStatusId,
        @Param("telSpedizione")
        String telSpedizione,
        @Param("tigrosCard")
        String tigrosCard,
        @Param("timeslotBegin")
        String timeslotBegin,
        @Param("timeslotDate")
        String timeslotDate,
        @Param("timeslotEnd")
        String timeslotEnd,
        @Param("tipoSacchetto")
        Integer tipoSacchetto,
        @Param("totale")
        BigDecimal totale,
        @Param("tsCaricamentoCamion")
        String tsCaricamentoCamion,
        @Param("tsFinePreparazione")
        String tsFinePreparazione,
        @Param("tsInizioPreparazione")
        String tsInizioPreparazione,
        @Param("tsInvioFeedback")
        String tsInvioFeedback,
        @Param("tsModifica")
        String tsModifica,
        @Param("tsRicevuto")
        String tsRicevuto,
        @Param("tsStartPayment")
        String tsStartPayment,
        @Param("vOrderId")
        String vOrderId,
        @Param("vSuborderId")
        String vSuborderId,
        @Param("viaSpedizione")
        String viaSpedizione,
        @Param("zoneGroupId")
        String zoneGroupId,
        @Param("zoneId")
        String zoneId,
        @Param("addressName")
        String addressName,
        @Param("doorBellName")
        String doorBellName,
        @Param("deliveryCity")
        String deliveryCity,
        @Param("address1")
        String address1,
        @Param("postalCode")
        String postalCode,
        @Param("province")
        String province,
        @Param("doorBellNumber")
        String doorBellNumber,
        @Param("address2")
        String address2,
        @Param("referencePhone")
        String referencePhone,
        @Param("latitude")
        BigDecimal latitude,
        @Param("longitude")
        BigDecimal longitude,
        @Param("collo")
        Collo collo,
        @Param("itemOrdine")
        ItemOrdine itemOrdine);

}
