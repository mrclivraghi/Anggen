
package it.anggen.service.entity;

import java.util.List;
import it.anggen.searchbean.entity.ProjectSearchBean;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

public interface ProjectService {


    public List<it.anggen.model.entity.Project> findById(Integer ProjectId);

    public List<it.anggen.model.entity.Project> find(ProjectSearchBean Project);

    public void deleteById(Integer ProjectId);

    public it.anggen.model.entity.Project insert(it.anggen.model.entity.Project Project);

    public it.anggen.model.entity.Project update(it.anggen.model.entity.Project Project);

    public Page<it.anggen.model.entity.Project> findByPage(
        @PathVariable
        Integer pageNumber);

}
