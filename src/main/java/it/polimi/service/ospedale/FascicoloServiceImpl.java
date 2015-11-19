
package it.polimi.service.ospedale;

import java.util.List;
import it.polimi.model.ospedale.Fascicolo;
import it.polimi.repository.ospedale.FascicoloRepository;
import it.polimi.searchbean.ospedale.FascicoloSearchBean;
import it.polimi.service.ospedale.FascicoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FascicoloServiceImpl
    implements FascicoloService
{

    @Autowired
    public FascicoloRepository fascicoloRepository;

    @Override
    public List<Fascicolo> findById(Long fascicoloId) {
        return fascicoloRepository.findByFascicoloId(fascicoloId);
    }

    @Override
    public List<Fascicolo> find(FascicoloSearchBean fascicolo) {
        return fascicoloRepository.findByFascicoloIdAndCreationDateAndPazienteAndAmbulatorioAndTipoIntervento(fascicolo.getFascicoloId(),it.polimi.utils.Utility.formatDate(fascicolo.getCreationDate()),fascicolo.getPaziente(),fascicolo.getAmbulatorio(),fascicolo.getTipoIntervento());
    }

    @Override
    public void deleteById(Long fascicoloId) {
        fascicoloRepository.delete(fascicoloId);
        return;
    }

    @Override
    public Fascicolo insert(Fascicolo fascicolo) {
        return fascicoloRepository.save(fascicolo);
    }

    @Override
    @Transactional
    public Fascicolo update(Fascicolo fascicolo) {
        Fascicolo returnedFascicolo=fascicoloRepository.save(fascicolo);
        if (fascicolo.getPaziente()!=null)
        {
        List<Fascicolo> fascicoloList = fascicoloRepository.findByPaziente( fascicolo.getPaziente());
        if (!fascicoloList.contains(returnedFascicolo))
        fascicoloList.add(returnedFascicolo);
        returnedFascicolo.getPaziente().setFascicoloList(fascicoloList);
        }
         return returnedFascicolo;
    }

}
