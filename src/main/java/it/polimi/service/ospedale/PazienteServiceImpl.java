
package it.polimi.service.ospedale;

import java.util.List;
import it.polimi.model.ospedale.Paziente;
import it.polimi.repository.ospedale.AmbulatorioRepository;
import it.polimi.repository.ospedale.FascicoloRepository;
import it.polimi.repository.ospedale.PazienteRepository;
import it.polimi.searchbean.ospedale.PazienteSearchBean;
import it.polimi.service.ospedale.PazienteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PazienteServiceImpl
    implements PazienteService
{

    @org.springframework.beans.factory.annotation.Autowired
    public PazienteRepository pazienteRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public FascicoloRepository fascicoloRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public AmbulatorioRepository ambulatorioRepository;

    @Override
    public List<Paziente> findById(Long pazienteId) {
        return pazienteRepository.findByPazienteId(pazienteId);
    }

    @Override
    public List<Paziente> find(PazienteSearchBean paziente) {
        return pazienteRepository.findByPazienteIdAndNomeAndCognomeAndBirthDateAndFascicoloAndAmbulatorio(paziente.getPazienteId(),paziente.getNome(),paziente.getCognome(),it.polimi.utils.Utility.formatDate(paziente.getBirthDate()),paziente.getFascicoloList()==null? null :paziente.getFascicoloList().get(0),paziente.getAmbulatorioList()==null? null :paziente.getAmbulatorioList().get(0));
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
        if (paziente.getAmbulatorioList()!=null)
        for (it.polimi.model.ospedale.Ambulatorio ambulatorio: paziente.getAmbulatorioList())
        {
        it.polimi.model.ospedale.Ambulatorio savedAmbulatorio = ambulatorioRepository.findOne(ambulatorio.getAmbulatorioId());
        Boolean found=false; 
        for (Paziente tempPaziente : savedAmbulatorio.getPazienteList())
        {
        if (tempPaziente.getPazienteId().equals(paziente.getPazienteId()))
        {
        found=true;
        break;
        }
        }
        if (!found)
        savedAmbulatorio.getPazienteList().add(paziente);
        }
        Paziente returnedPaziente=pazienteRepository.save(paziente);
         return returnedPaziente;
    }

}
