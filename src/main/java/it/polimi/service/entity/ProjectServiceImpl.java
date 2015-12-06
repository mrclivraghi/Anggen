
package it.polimi.service.entity;

import java.util.List;

import it.polimi.repository.entity.EntityGroupRepository;
import it.polimi.repository.entity.ProjectRepository;
import it.polimi.searchbean.entity.ProjectSearchBean;
import it.polimi.service.entity.ProjectService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectServiceImpl
    implements ProjectService
{

    @org.springframework.beans.factory.annotation.Autowired
    public ProjectRepository projectRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public EntityGroupRepository entityGroupRepository;

    @Override
    public List<it.polimi.model.entity.Project> findById(Integer projectId) {
        return projectRepository.findByProjectId(projectId);
    }

    @Override
    public List<it.polimi.model.entity.Project> find(ProjectSearchBean project) {
        return projectRepository.findByProjectIdAndNameAndEntityGroup(project.getProjectId(),project.getName(),project.getEntityGroupList()==null? null :project.getEntityGroupList().get(0));
    }

    @Override
    public void deleteById(Integer projectId) {
        projectRepository.delete(projectId);
        return;
    }

    @Override
    public it.polimi.model.entity.Project insert(it.polimi.model.entity.Project project) {
        return projectRepository.save(project);
    }

    @Override
    @Transactional
    public it.polimi.model.entity.Project update(it.polimi.model.entity.Project project) {
        if (project.getEntityGroupList()!=null)
        for (it.polimi.model.entity.EntityGroup entityGroup: project.getEntityGroupList())
        {
        entityGroup.setProject(project);
        }
        it.polimi.model.entity.Project returnedProject=projectRepository.save(project);
         return returnedProject;
    }

}
