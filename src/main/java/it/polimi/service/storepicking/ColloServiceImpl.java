
package it.polimi.service.storepicking;

import java.util.List;
import it.polimi.model.storepicking.Collo;
import it.polimi.repository.storepicking.ColloRepository;
import it.polimi.searchbean.storepicking.ColloSearchBean;
import it.polimi.service.storepicking.ColloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ColloServiceImpl
    implements ColloService
{

    @Autowired
    public ColloRepository colloRepository;

    @Override
    public List<Collo> findById(Integer colloId) {
        return colloRepository.findByColloId(colloId);
    }

    @Override
    public List<Collo> find(ColloSearchBean collo) {
        return colloRepository.findByColloIdAndCodiceAndCutoffRoadnetAndOrdinamentoReportAndTsGenerazioneAndOrdine(collo.getColloId(),collo.getCodice(),collo.getCutoffRoadnet(),collo.getOrdinamentoReport(),it.polimi.utils.Utility.formatDate(collo.getTsGenerazione()),collo.getOrdine());
    }

    @Override
    public void deleteById(Integer colloId) {
        colloRepository.delete(colloId);
        return;
    }

    @Override
    public Collo insert(Collo collo) {
        return colloRepository.save(collo);
    }

    @Override
    @Transactional
    public Collo update(Collo collo) {
        Collo returnedCollo=colloRepository.save(collo);
        if (collo.getOrdine()!=null)
        {
        List<Collo> colloList = colloRepository.findByOrdine( collo.getOrdine());
        if (!colloList.contains(returnedCollo))
        colloList.add(returnedCollo);
        returnedCollo.getOrdine().setColloList(colloList);
        }
         return returnedCollo;
    }

}
