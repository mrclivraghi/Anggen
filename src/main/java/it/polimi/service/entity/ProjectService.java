
package it.polimi.service.entity;

import java.util.List;

import it.polimi.searchbean.entity.ProjectSearchBean;

public interface ProjectService {


    public List<it.polimi.model.entity.Project> findById(Integer ProjectId);

    public List<it.polimi.model.entity.Project> find(ProjectSearchBean Project);

    public void deleteById(Integer ProjectId);

    public it.polimi.model.entity.Project insert(it.polimi.model.entity.Project Project);

    public it.polimi.model.entity.Project update(it.polimi.model.entity.Project Project);

}
