
package it.polimi.repository.ospedale;

import java.util.List;
import it.polimi.model.ospedale.Ambulatorio;
import it.polimi.model.ospedale.Fascicolo;
import it.polimi.model.ospedale.Paziente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FascicoloRepository
    extends CrudRepository<Fascicolo, Long>
{


    public List<Fascicolo> findByFascicoloId(Long fascicoloId);

    public List<Fascicolo> findByCreationDate(String creationDate);

    public List<Fascicolo> findByPaziente(Paziente paziente);

    public List<Fascicolo> findByAmbulatorio(Ambulatorio ambulatorio);

    public List<Fascicolo> findByTipoIntervento(String tipoIntervento);

    @Query("select f from Fascicolo f where  (:fascicoloId is null or cast(:fascicoloId as string)=cast(f.fascicoloId as string)) and (:creationDate is null or cast(:creationDate as string)=cast(date(f.creationDate) as string)) and (:paziente=f.paziente or :paziente is null) and (:ambulatorio=f.ambulatorio or :ambulatorio is null) and (:tipoIntervento is null or :tipoIntervento='' or cast(:tipoIntervento as string)=f.tipoIntervento) ")
    public List<Fascicolo> findByFascicoloIdAndCreationDateAndPazienteAndAmbulatorioAndTipoIntervento(
        @Param("fascicoloId")
        Long fascicoloId,
        @Param("creationDate")
        String creationDate,
        @Param("paziente")
        Paziente paziente,
        @Param("ambulatorio")
        Ambulatorio ambulatorio,
        @Param("tipoIntervento")
        String tipoIntervento);

}
