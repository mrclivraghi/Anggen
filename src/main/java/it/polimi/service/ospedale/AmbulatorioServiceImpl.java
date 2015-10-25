
package it.polimi.service.ospedale;

import java.util.List;
import it.polimi.model.ospedale.Ambulatorio;
import it.polimi.repository.ospedale.AmbulatorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AmbulatorioServiceImpl
    implements AmbulatorioService
{

    @Autowired
    public AmbulatorioRepository ambulatorioRepository;

    @Override
    public List<Ambulatorio> findById(Long ambulatorioId) {
        return ambulatorioRepository.findByAmbulatorioId(ambulatorioId);
    }

    @Override
    public List<Ambulatorio> find(Ambulatorio ambulatorio) {
        return ambulatorioRepository.findByAmbulatorioIdAndNomeAndIndirizzo(ambulatorio.getAmbulatorioId(),ambulatorio.getNome(),ambulatorio.getIndirizzo());
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
        Ambulatorio returnedAmbulatorio=ambulatorioRepository.save(ambulatorio);
         return returnedAmbulatorio;
    }

}
