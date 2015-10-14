
package it.polimi.repository;

import java.math.BigDecimal;
import java.util.List;
import it.polimi.model.ItemOrdine;
import it.polimi.model.ItemOrdineCodice;
import it.polimi.model.Ordine;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemOrdineRepository
    extends CrudRepository<ItemOrdine, Integer>
{


    public List<ItemOrdine> findByItemOrdineId(Integer itemOrdineId);

    public List<ItemOrdine> findByBarcode(String barcode);

    public List<ItemOrdine> findByCarfCanaleLocale(String carfCanaleLocale);

    public List<ItemOrdine> findByDaCancellare(int daCancellare);

    public List<ItemOrdine> findByFormat(String format);

    public List<ItemOrdine> findByIdStatoFeedback(int idStatoFeedback);

    public List<ItemOrdine> findByIdStatoItemOrdine(int idStatoItemOrdine);

    public List<ItemOrdine> findByIdStatoPreparazione(int idStatoPreparazione);

    public List<ItemOrdine> findByInfoFeedback(String infoFeedback);

    public List<ItemOrdine> findByName(String name);

    public List<ItemOrdine> findByNote(String note);

    public List<ItemOrdine> findByPriorita(int priorita);

    public List<ItemOrdine> findByProductStatus(int productStatus);

    public List<ItemOrdine> findByPromoPunti(int promoPunti);

    public List<ItemOrdine> findByQuantityFinale(BigDecimal quantityFinale);

    public List<ItemOrdine> findByQuantityIniz(BigDecimal quantityIniz);

    public List<ItemOrdine> findBySingleUnitWeight(int singleUnitWeight);

    public List<ItemOrdine> findBySostituito(int sostituito);

    public List<ItemOrdine> findByTsModifica(String tsModifica);

    public List<ItemOrdine> findByTsPreparazione(String tsPreparazione);

    public List<ItemOrdine> findByVOrderItemId(String vOrderItemId);

    public List<ItemOrdine> findByVPrice(BigDecimal vPrice);

    public List<ItemOrdine> findByVProductId(String vProductId);

    public List<ItemOrdine> findByWeightFinale(BigDecimal weightFinale);

    public List<ItemOrdine> findByWeightIniz(BigDecimal weightIniz);

    public List<ItemOrdine> findByOrdine(Ordine ordine);

    @Query("select i from ItemOrdine i where  (:itemOrdineId is null or cast(:itemOrdineId as string)=cast(i.itemOrdineId as string)) and (:barcode is null or :barcode='' or cast(:barcode as string)=i.barcode) and (:carfCanaleLocale is null or :carfCanaleLocale='' or cast(:carfCanaleLocale as string)=i.carfCanaleLocale) and (:daCancellare is null or cast(:daCancellare as string)=cast(i.daCancellare as string)) and (:format is null or :format='' or cast(:format as string)=i.format) and (:idStatoFeedback is null or cast(:idStatoFeedback as string)=cast(i.idStatoFeedback as string)) and (:idStatoItemOrdine is null or cast(:idStatoItemOrdine as string)=cast(i.idStatoItemOrdine as string)) and (:idStatoPreparazione is null or cast(:idStatoPreparazione as string)=cast(i.idStatoPreparazione as string)) and (:infoFeedback is null or :infoFeedback='' or cast(:infoFeedback as string)=i.infoFeedback) and (:name is null or :name='' or cast(:name as string)=i.name) and (:note is null or :note='' or cast(:note as string)=i.note) and (:priorita is null or cast(:priorita as string)=cast(i.priorita as string)) and (:productStatus is null or cast(:productStatus as string)=cast(i.productStatus as string)) and (:promoPunti is null or cast(:promoPunti as string)=cast(i.promoPunti as string)) and (:quantityFinale is null or cast(:quantityFinale as string)=cast(i.quantityFinale as string)) and (:quantityIniz is null or cast(:quantityIniz as string)=cast(i.quantityIniz as string)) and (:singleUnitWeight is null or cast(:singleUnitWeight as string)=cast(i.singleUnitWeight as string)) and (:sostituito is null or cast(:sostituito as string)=cast(i.sostituito as string)) and (:tsModifica is null or cast(:tsModifica as string)=cast(date(i.tsModifica) as string)) and (:tsPreparazione is null or cast(:tsPreparazione as string)=cast(date(i.tsPreparazione) as string)) and (:vOrderItemId is null or :vOrderItemId='' or cast(:vOrderItemId as string)=i.vOrderItemId) and (:vPrice is null or cast(:vPrice as string)=cast(i.vPrice as string)) and (:vProductId is null or :vProductId='' or cast(:vProductId as string)=i.vProductId) and (:weightFinale is null or cast(:weightFinale as string)=cast(i.weightFinale as string)) and (:weightIniz is null or cast(:weightIniz as string)=cast(i.weightIniz as string)) and (:ordine=i.ordine or :ordine is null) and (:itemOrdineCodice in elements(i.itemOrdineCodiceList)  or :itemOrdineCodice is null) ")
    public List<ItemOrdine> findByItemOrdineIdAndBarcodeAndCarfCanaleLocaleAndDaCancellareAndFormatAndIdStatoFeedbackAndIdStatoItemOrdineAndIdStatoPreparazioneAndInfoFeedbackAndNameAndNoteAndPrioritaAndProductStatusAndPromoPuntiAndQuantityFinaleAndQuantityInizAndSingleUnitWeightAndSostituitoAndTsModificaAndTsPreparazioneAndVOrderItemIdAndVPriceAndVProductIdAndWeightFinaleAndWeightInizAndOrdineAndItemOrdineCodice(
        @Param("itemOrdineId")
        Integer itemOrdineId,
        @Param("barcode")
        String barcode,
        @Param("carfCanaleLocale")
        String carfCanaleLocale,
        @Param("daCancellare")
        int daCancellare,
        @Param("format")
        String format,
        @Param("idStatoFeedback")
        int idStatoFeedback,
        @Param("idStatoItemOrdine")
        int idStatoItemOrdine,
        @Param("idStatoPreparazione")
        int idStatoPreparazione,
        @Param("infoFeedback")
        String infoFeedback,
        @Param("name")
        String name,
        @Param("note")
        String note,
        @Param("priorita")
        int priorita,
        @Param("productStatus")
        int productStatus,
        @Param("promoPunti")
        int promoPunti,
        @Param("quantityFinale")
        BigDecimal quantityFinale,
        @Param("quantityIniz")
        BigDecimal quantityIniz,
        @Param("singleUnitWeight")
        int singleUnitWeight,
        @Param("sostituito")
        int sostituito,
        @Param("tsModifica")
        String tsModifica,
        @Param("tsPreparazione")
        String tsPreparazione,
        @Param("vOrderItemId")
        String vOrderItemId,
        @Param("vPrice")
        BigDecimal vPrice,
        @Param("vProductId")
        String vProductId,
        @Param("weightFinale")
        BigDecimal weightFinale,
        @Param("weightIniz")
        BigDecimal weightIniz,
        @Param("ordine")
        Ordine ordine,
        @Param("itemOrdineCodice")
        ItemOrdineCodice itemOrdineCodice);

}
