
package it.polimi.service.ospedale;

import java.util.List;
import it.polimi.model.ospedale.Paziente;
import it.polimi.repository.ospedale.FascicoloRepository;
import it.polimi.repository.ospedale.PazienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PazienteServiceImpl
    implements PazienteService
{

    @Autowired
    public PazienteRepository pazienteRepository;
    @Autowired
    public FascicoloRepository fascicoloRepository;

    @Override
    public List<Paziente> findById(Long pazienteId) {
        return pazienteRepository.findByPazienteId(pazienteId);
    }

    @Override
    public List<Paziente> find(Paziente paziente) {
        return pazienteRepository.findByPazienteIdAndNomeAndCognomeAndBirthDateAndFascicolo(paziente.getPazienteId(),paziente.getNome(),paziente.getCognome(),it.polimi.utils.Utility.formatDate(paziente.getBirthDate()),paziente.getFascicoloList()==null? null :paziente.getFascicoloList().get(0));
    }

    @Override
    public void deleteById(Long pazienteId) {
        pazienteRepository.delete(pazienteId);
        return;
    }

    @Override
    public Paziente insert(Paziente paziente) {
        return pazienteRepository.save(paziente);
    }

    @Override
    @Transactional
    public Paziente update(Paziente paziente) {
        if (paziente.getFascicoloList()!=null)
        for (it.polimi.model.ospedale.Fascicolo fascicolo: paziente.getFascicoloList())
        {
        fascicolo.setPaziente(paziente);
        }
        Paziente returnedPaziente=pazienteRepository.save(paziente);
         return returnedPaziente;
    }

}
