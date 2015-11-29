
package it.generated.anggen.service.entity;

import java.util.List;
import it.generated.anggen.searchbean.entity.ProjectSearchBean;

public interface ProjectService {


    public List<it.generated.anggen.model.entity.Project> findById(Integer ProjectId);

    public List<it.generated.anggen.model.entity.Project> find(ProjectSearchBean Project);

    public void deleteById(Integer ProjectId);

    public it.generated.anggen.model.entity.Project insert(it.generated.anggen.model.entity.Project Project);

    public it.generated.anggen.model.entity.Project update(it.generated.anggen.model.entity.Project Project);

}
