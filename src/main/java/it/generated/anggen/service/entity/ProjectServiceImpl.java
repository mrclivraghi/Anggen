
package it.generated.anggen.service.entity;

import java.util.List;
import it.generated.anggen.repository.entity.EntityGroupRepository;
import it.generated.anggen.repository.entity.ProjectRepository;
import it.generated.anggen.searchbean.entity.ProjectSearchBean;
import it.generated.anggen.service.entity.ProjectService;
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
    public List<it.generated.anggen.model.entity.Project> findById(Integer projectId) {
        return projectRepository.findByProjectId(projectId);
    }

    @Override
    public List<it.generated.anggen.model.entity.Project> find(ProjectSearchBean project) {
        return projectRepository.findByNameAndProjectIdAndEntityIdAndEntityGroup(project.getName(),project.getProjectId(),project.getEntityId(),project.getEntityGroupList()==null? null :project.getEntityGroupList().get(0));
    }

    @Override
    public void deleteById(Integer projectId) {
        projectRepository.delete(projectId);
        return;
    }

    @Override
    public it.generated.anggen.model.entity.Project insert(it.generated.anggen.model.entity.Project project) {
        return projectRepository.save(project);
    }

    @Override
    @Transactional
    public it.generated.anggen.model.entity.Project update(it.generated.anggen.model.entity.Project project) {
        if (project.getEntityGroupList()!=null)
        for (it.generated.anggen.model.entity.EntityGroup entityGroup: project.getEntityGroupList())
        {
        entityGroup.setProject(project);
        }
        it.generated.anggen.model.entity.Project returnedProject=projectRepository.save(project);
         return returnedProject;
    }

}
