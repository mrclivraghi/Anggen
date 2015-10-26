
package it.polimi.service.ospedale;

import java.util.List;
import it.polimi.model.ospedale.Ambulatorio;
import it.polimi.repository.ospedale.AmbulatorioRepository;
import it.polimi.repository.ospedale.PazienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AmbulatorioServiceImpl
    implements AmbulatorioService
{

    @Autowired
    public AmbulatorioRepository ambulatorioRepository;
    @Autowired
    public PazienteRepository pazienteRepository;

    @Override
    public List<Ambulatorio> findById(Long ambulatorioId) {
        return ambulatorioRepository.findByAmbulatorioId(ambulatorioId);
    }

    @Override
    public List<Ambulatorio> find(Ambulatorio ambulatorio) {
        return ambulatorioRepository.findByAmbulatorioIdAndNomeAndIndirizzoAndPaziente(ambulatorio.getAmbulatorioId(),ambulatorio.getNome(),ambulatorio.getIndirizzo(),ambulatorio.getPazienteList()==null? null :ambulatorio.getPazienteList().get(0));
    }

    @Override
    public void deleteById(Long ambulatorioId) {
        ambulatorioRepository.delete(ambulatorioId);
        return;
    }

    @Override
    public Ambulatorio insert(Ambulatorio ambulatorio) {
        return ambulatorioRepository.save(ambulatorio);
    }

    @Override
    @Transactional
    public Ambulatorio update(Ambulatorio ambulatorio) {
        if (ambulatorio.getPazienteList()!=null)
        for (it.polimi.model.ospedale.Paziente paziente: ambulatorio.getPazienteList())
        {
        it.polimi.model.ospedale.Paziente savedPaziente = pazienteRepository.findOne(paziente.getPazienteId());
        Boolean found=false; 
        for (Ambulatorio tempAmbulatorio : savedPaziente.getAmbulatorioList())
        {
        if (tempAmbulatorio.getAmbulatorioId().equals(ambulatorio.getAmbulatorioId()))
        {
        found=true;
        break;
        }
        }
        if (!found)
        savedPaziente.getAmbulatorioList().add(ambulatorio);
        }
        Ambulatorio returnedAmbulatorio=ambulatorioRepository.save(ambulatorio);
         return returnedAmbulatorio;
    }

}
