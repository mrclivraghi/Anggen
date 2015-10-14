
package it.polimi.repository;

import java.util.List;
import it.polimi.model.Collo;
import it.polimi.model.Ordine;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ColloRepository
    extends CrudRepository<Collo, Integer>
{


    public List<Collo> findByColloId(Integer colloId);

    public List<Collo> findByCodice(String codice);

    public List<Collo> findByCutoffRoadnet(Integer cutoffRoadnet);

    public List<Collo> findByOrdinamentoReport(String ordinamentoReport);

    public List<Collo> findByTsGenerazione(String tsGenerazione);

    public List<Collo> findByOrdine(Ordine ordine);

    @Query("select c from Collo c where  (:colloId is null or cast(:colloId as string)=cast(c.colloId as string)) and (:codice is null or :codice='' or cast(:codice as string)=c.codice) and (:cutoffRoadnet is null or cast(:cutoffRoadnet as string)=cast(c.cutoffRoadnet as string)) and (:ordinamentoReport is null or :ordinamentoReport='' or cast(:ordinamentoReport as string)=c.ordinamentoReport) and (:tsGenerazione is null or cast(:tsGenerazione as string)=cast(date(c.tsGenerazione) as string)) and (:ordine=c.ordine or :ordine is null) ")
    public List<Collo> findByColloIdAndCodiceAndCutoffRoadnetAndOrdinamentoReportAndTsGenerazioneAndOrdine(
        @Param("colloId")
        Integer colloId,
        @Param("codice")
        String codice,
        @Param("cutoffRoadnet")
        Integer cutoffRoadnet,
        @Param("ordinamentoReport")
        String ordinamentoReport,
        @Param("tsGenerazione")
        String tsGenerazione,
        @Param("ordine")
        Ordine ordine);

}
