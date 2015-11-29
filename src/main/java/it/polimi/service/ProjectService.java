
package it.polimi.service;

import java.util.List;
import it.polimi.searchbean.ProjectSearchBean;

public interface ProjectService {


    public List<it.polimi.model.domain.Project> findById(Integer ProjectId);

    public List<it.polimi.model.domain.Project> find(ProjectSearchBean Project);

    public void deleteById(Integer ProjectId);

    public it.polimi.model.domain.Project insert(it.polimi.model.domain.Project Project);

    public it.polimi.model.domain.Project update(it.polimi.model.domain.Project Project);

}
