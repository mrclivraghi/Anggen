
package it.polimi.repository.ospedale;

import java.util.List;
import it.polimi.model.ospedale.Fascicolo;
import it.polimi.model.ospedale.Paziente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PazienteRepository
    extends CrudRepository<Paziente, Long>
{


    public List<Paziente> findByPazienteId(Long pazienteId);

    public List<Paziente> findByNome(String nome);

    public List<Paziente> findByCognome(String cognome);

    public List<Paziente> findByBirthDate(String birthDate);

    @Query("select p from Paziente p where  (:pazienteId is null or cast(:pazienteId as string)=cast(p.pazienteId as string)) and (:nome is null or :nome='' or cast(:nome as string)=p.nome) and (:cognome is null or :cognome='' or cast(:cognome as string)=p.cognome) and (:birthDate is null or cast(:birthDate as string)=cast(date(p.birthDate) as string)) and (:fascicolo in elements(p.fascicoloList)  or :fascicolo is null) ")
    public List<Paziente> findByPazienteIdAndNomeAndCognomeAndBirthDateAndFascicolo(
        @Param("pazienteId")
        Long pazienteId,
        @Param("nome")
        String nome,
        @Param("cognome")
        String cognome,
        @Param("birthDate")
        String birthDate,
        @Param("fascicolo")
        Fascicolo fascicolo);

}
