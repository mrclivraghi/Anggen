
package it.polimi.repository;

import java.util.List;
import it.polimi.model.ItemOrdine;
import it.polimi.model.ItemOrdineCodice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemOrdineCodiceRepository
    extends CrudRepository<ItemOrdineCodice, Integer>
{


    public List<ItemOrdineCodice> findByItemOrdineCodiceId(Integer itemOrdineCodiceId);

    public List<ItemOrdineCodice> findByBarcode(String barcode);

    public List<ItemOrdineCodice> findByBarcodeRead(String barcodeRead);

    public List<ItemOrdineCodice> findByDaCancellare(Integer daCancellare);

    public List<ItemOrdineCodice> findByIdStato(Integer idStato);

    public List<ItemOrdineCodice> findByName(String name);

    public List<ItemOrdineCodice> findByNelCarrello(Integer nelCarrello);

    public List<ItemOrdineCodice> findByQuantityFinale(Integer quantityFinale);

    public List<ItemOrdineCodice> findByTsModifica(String tsModifica);

    public List<ItemOrdineCodice> findByVPrice(Double vPrice);

    public List<ItemOrdineCodice> findByVProductId(String vProductId);

    public List<ItemOrdineCodice> findByWeightFinale(Double weightFinale);

    public List<ItemOrdineCodice> findByItemOrdine(ItemOrdine itemOrdine);

    @Query("select i from ItemOrdineCodice i where  (:itemOrdineCodiceId is null or cast(:itemOrdineCodiceId as string)=cast(i.itemOrdineCodiceId as string)) and (:barcode is null or :barcode='' or cast(:barcode as string)=i.barcode) and (:barcodeRead is null or :barcodeRead='' or cast(:barcodeRead as string)=i.barcodeRead) and (:daCancellare is null or cast(:daCancellare as string)=cast(i.daCancellare as string)) and (:idStato is null or cast(:idStato as string)=cast(i.idStato as string)) and (:name is null or :name='' or cast(:name as string)=i.name) and (:nelCarrello is null or cast(:nelCarrello as string)=cast(i.nelCarrello as string)) and (:quantityFinale is null or cast(:quantityFinale as string)=cast(i.quantityFinale as string)) and (:tsModifica is null or cast(:tsModifica as string)=cast(date(i.tsModifica) as string)) and (:vPrice is null or cast(:vPrice as string)=cast(i.vPrice as string)) and (:vProductId is null or :vProductId='' or cast(:vProductId as string)=i.vProductId) and (:weightFinale is null or cast(:weightFinale as string)=cast(i.weightFinale as string)) and (:itemOrdine=i.itemOrdine or :itemOrdine is null) ")
    public List<ItemOrdineCodice> findByItemOrdineCodiceIdAndBarcodeAndBarcodeReadAndDaCancellareAndIdStatoAndNameAndNelCarrelloAndQuantityFinaleAndTsModificaAndVPriceAndVProductIdAndWeightFinaleAndItemOrdine(
        @Param("itemOrdineCodiceId")
        Integer itemOrdineCodiceId,
        @Param("barcode")
        String barcode,
        @Param("barcodeRead")
        String barcodeRead,
        @Param("daCancellare")
        Integer daCancellare,
        @Param("idStato")
        Integer idStato,
        @Param("name")
        String name,
        @Param("nelCarrello")
        Integer nelCarrello,
        @Param("quantityFinale")
        Integer quantityFinale,
        @Param("tsModifica")
        String tsModifica,
        @Param("vPrice")
        Double vPrice,
        @Param("vProductId")
        String vProductId,
        @Param("weightFinale")
        Double weightFinale,
        @Param("itemOrdine")
        ItemOrdine itemOrdine);

}
