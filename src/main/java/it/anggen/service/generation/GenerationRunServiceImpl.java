
package it.anggen.service.generation;

import java.util.List;
import it.anggen.repository.generation.GenerationRunRepository;
import it.anggen.searchbean.generation.GenerationRunSearchBean;
import it.anggen.service.generation.GenerationRunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenerationRunServiceImpl
    implements GenerationRunService
{

    @Autowired
    public GenerationRunRepository generationRunRepository;
    private static Integer PAGE_SIZE = (5);

    @Override
    public List<it.anggen.model.generation.GenerationRun> findById(Integer generationRunId) {
        return generationRunRepository.findByGenerationRunId(generationRunId);
    }

    @Override
    public Page<it.anggen.model.generation.GenerationRun> findByPage(Integer pageNumber) {
        org.springframework.data.domain.PageRequest pageRequest = new org.springframework.data.domain.PageRequest(pageNumber - 1, PAGE_SIZE, org.springframework.data.domain.Sort.Direction.DESC, "generationRunId");
        return generationRunRepository.findAll(pageRequest);
    }

    @Override
    public List<it.anggen.model.generation.GenerationRun> find(GenerationRunSearchBean generationRun) {
        return generationRunRepository.findByGenerationRunIdAndStartDateAndEndDateAndStatusAndProject(generationRun.getGenerationRunId(),it.anggen.utils.Utility.formatDate(generationRun.getStartDate()),it.anggen.utils.Utility.formatDate(generationRun.getEndDate()),generationRun.getStatus(),generationRun.getProject());
    }

    @Override
    public void deleteById(Integer generationRunId) {
        generationRunRepository.delete(generationRunId);
        return;
    }

    @Override
    public it.anggen.model.generation.GenerationRun insert(it.anggen.model.generation.GenerationRun generationRun) {
        return generationRunRepository.save(generationRun);
    }

    @Override
    @Transactional
    public it.anggen.model.generation.GenerationRun update(it.anggen.model.generation.GenerationRun generationRun) {
        it.anggen.model.generation.GenerationRun returnedGenerationRun=generationRunRepository.save(generationRun);
        if (generationRun.getProject()!=null)
        {
        List<it.anggen.model.generation.GenerationRun> generationRunList = generationRunRepository.findByProject( generationRun.getProject());
        if (!generationRunList.contains(returnedGenerationRun))
        generationRunList.add(returnedGenerationRun);
        returnedGenerationRun.getProject().setGenerationRunList(generationRunList);
        }
         return returnedGenerationRun;
    }

}
