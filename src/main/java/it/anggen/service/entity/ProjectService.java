
package it.anggen.service.entity;

import java.util.List;
import it.anggen.searchbean.entity.ProjectSearchBean;

public interface ProjectService {


    public List<it.anggen.model.entity.Project> findById(Integer ProjectId);

    public List<it.anggen.model.entity.Project> find(ProjectSearchBean Project);

    public void deleteById(Integer ProjectId);

    public it.anggen.model.entity.Project insert(it.anggen.model.entity.Project Project);

    public it.anggen.model.entity.Project update(it.anggen.model.entity.Project Project);

}
