
package it.polimi.repository.ospedale;

import java.util.List;
import it.polimi.model.ospedale.Ambulatorio;
import it.polimi.model.ospedale.Paziente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AmbulatorioRepository
    extends CrudRepository<Ambulatorio, Long>
{


    public List<Ambulatorio> findByAmbulatorioId(Long ambulatorioId);

    public List<Ambulatorio> findByNome(String nome);

    public List<Ambulatorio> findByIndirizzo(String indirizzo);

    @Query("select a from Ambulatorio a where  (:ambulatorioId is null or cast(:ambulatorioId as string)=cast(a.ambulatorioId as string)) and (:nome is null or :nome='' or cast(:nome as string)=a.nome) and (:indirizzo is null or :indirizzo='' or cast(:indirizzo as string)=a.indirizzo) and (:paziente in elements(a.pazienteList)  or :paziente is null) ")
    public List<Ambulatorio> findByAmbulatorioIdAndNomeAndIndirizzoAndPaziente(
        @Param("ambulatorioId")
        Long ambulatorioId,
        @Param("nome")
        String nome,
        @Param("indirizzo")
        String indirizzo,
        @Param("paziente")
        Paziente paziente);

}
