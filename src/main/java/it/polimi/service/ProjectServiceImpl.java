
package it.polimi.service;

import java.util.List;

import it.polimi.repository.EntityGroupRepository;
import it.polimi.repository.ProjectRepository;
import it.polimi.searchbean.ProjectSearchBean;
import it.polimi.service.ProjectService;

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
    public List<it.polimi.model.domain.Project> findById(Integer projectId) {
        return projectRepository.findByProjectId(projectId);
    }

    @Override
    public List<it.polimi.model.domain.Project> find(ProjectSearchBean project) {
        return projectRepository.findByNameAndProjectIdAndEntityGroupTest(project.getName(),project.getProjectId(),project.getEntityGroupTestList()==null? null :project.getEntityGroupTestList().get(0));
    }

    @Override
    public void deleteById(Integer projectId) {
        projectRepository.delete(projectId);
        return;
    }

    @Override
    public it.polimi.model.domain.Project insert(it.polimi.model.domain.Project project) {
        return projectRepository.save(project);
    }

    @Override
    @Transactional
    public it.polimi.model.domain.Project update(it.polimi.model.domain.Project project) {
        if (project.getEntityGroupList()!=null)
        for (it.polimi.model.domain.EntityGroup entityGroup: project.getEntityGroupList())
        {
        entityGroup.setProject(project);
        }
        it.polimi.model.domain.Project returnedProject=projectRepository.save(project);
         return returnedProject;
    }

}
