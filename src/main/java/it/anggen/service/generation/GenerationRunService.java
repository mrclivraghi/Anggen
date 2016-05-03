
package it.anggen.service.generation;

import java.util.List;
import it.anggen.searchbean.generation.GenerationRunSearchBean;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

public interface GenerationRunService {


    public List<it.anggen.model.generation.GenerationRun> findById(Integer GenerationRunId);

    public List<it.anggen.model.generation.GenerationRun> find(GenerationRunSearchBean GenerationRun);

    public void deleteById(Integer GenerationRunId);

    public it.anggen.model.generation.GenerationRun insert(it.anggen.model.generation.GenerationRun GenerationRun);

    public it.anggen.model.generation.GenerationRun update(it.anggen.model.generation.GenerationRun GenerationRun);

    public Page<it.anggen.model.generation.GenerationRun> findByPage(
        @PathVariable
        Integer pageNumber);

}
