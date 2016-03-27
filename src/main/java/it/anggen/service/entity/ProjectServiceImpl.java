
package it.anggen.service.entity;

import java.util.List;
import it.anggen.repository.entity.EntityGroupRepository;
import it.anggen.repository.entity.EnumEntityRepository;
import it.anggen.repository.entity.ProjectRepository;
import it.anggen.searchbean.entity.ProjectSearchBean;
import it.anggen.service.entity.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectServiceImpl
    implements ProjectService
{

    @org.springframework.beans.factory.annotation.Autowired
    public ProjectRepository projectRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public EnumEntityRepository enumEntityRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public EntityGroupRepository entityGroupRepository;
    private static Integer PAGE_SIZE = (5);

    @Override
    public List<it.anggen.model.entity.Project> findById(Integer projectId) {
        return projectRepository.findByProjectId(projectId);
    }

    @Override
    public Page<it.anggen.model.entity.Project> findByPage(Integer pageNumber) {
        org.springframework.data.domain.PageRequest pageRequest = new org.springframework.data.domain.PageRequest(pageNumber - 1, PAGE_SIZE, org.springframework.data.domain.Sort.Direction.DESC, "projectId");
        return projectRepository.findAll(pageRequest);
    }

    @Override
    public List<it.anggen.model.entity.Project> find(ProjectSearchBean project) {
        return projectRepository.findByProjectIdAndNameAndEnumEntityAndEntityGroup(project.getProjectId(),project.getName(),project.getEnumEntityList()==null? null :project.getEnumEntityList().get(0),project.getEntityGroupList()==null? null :project.getEntityGroupList().get(0));
    }

    @Override
    public void deleteById(Integer projectId) {
        projectRepository.delete(projectId);
        return;
    }

    @Override
    public it.anggen.model.entity.Project insert(it.anggen.model.entity.Project project) {
        return projectRepository.save(project);
    }

    @Override
    @Transactional
    public it.anggen.model.entity.Project update(it.anggen.model.entity.Project project) {
        if (project.getEnumEntityList()!=null)
        for (it.anggen.model.entity.EnumEntity enumEntity: project.getEnumEntityList())
        {
        enumEntity.setProject(project);
        }
        if (project.getEntityGroupList()!=null)
        for (it.anggen.model.entity.EntityGroup entityGroup: project.getEntityGroupList())
        {
        entityGroup.setProject(project);
        }
        it.anggen.model.entity.Project returnedProject=projectRepository.save(project);
         return returnedProject;
    }

}
