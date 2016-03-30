
package it.anggen.controller.entity;

import java.util.List;
import com.codahale.metrics.annotation.Timed;
import it.anggen.searchbean.entity.ProjectSearchBean;
import it.anggen.security.SecurityService;
import it.anggen.service.entity.ProjectService;
import it.anggen.service.log.LogEntryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @org.springframework.beans.factory.annotation.Autowired
    private ProjectService projectService;
    @org.springframework.beans.factory.annotation.Autowired
    private SecurityService securityService;
    @org.springframework.beans.factory.annotation.Autowired
    private LogEntryService logEntryService;
    private final static Logger log = LoggerFactory.getLogger(it.anggen.model.entity.Project.class);
    @Value("${application.security}")
    private Boolean securityEnabled;

    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Project.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "project";
    }

    @Timed
    @RequestMapping(value = "/pages/{pageNumber}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity findPage(
        @PathVariable
        Integer pageNumber) {
        org.springframework.data.domain.Page<it.anggen.model.entity.Project> page = projectService.findByPage(pageNumber);
        getRightMapping(page.getContent());
        return ResponseEntity.ok().body(page);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        ProjectSearchBean project) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Project.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.anggen.model.entity.Project> projectList;
        if (project.getProjectId()!=null)
         log.info("Searching project like {}", project.getName()+' '+ project.getProjectId());
        logEntryService.addLogEntry( "Searching entity like "+ project.getName()+' '+ project.getProjectId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.SEARCH_ENTITY, it.anggen.model.entity.Project.staticEntityId, securityService.getLoggedUser(),log);
        projectList=projectService.find(project);
        getSecurityMapping(projectList);
        getRightMapping(projectList);
         log.info("Search: returning {} project.",projectList.size());
        return ResponseEntity.ok().body(projectList);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public ResponseEntity getProjectById(
        @PathVariable
        String projectId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Project.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        logEntryService.addLogEntry( "Searching project with id "+projectId,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.SEARCH_ENTITY, it.anggen.model.entity.Project.staticEntityId, securityService.getLoggedUser(),log);
        List<it.anggen.model.entity.Project> projectList=projectService.findById(Integer.valueOf(projectId));
        getSecurityMapping(projectList);
        getRightMapping(projectList);
         log.info("Search: returning {} project.",projectList.size());
        return ResponseEntity.ok().body(projectList);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/{projectId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProjectById(
        @PathVariable
        String projectId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Project.staticEntityId, it.anggen.model.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting project with id "+projectId);
        logEntryService.addLogEntry( "Deleting project with id {}"+projectId,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.DELETE_ENTITY, it.anggen.model.entity.Project.staticEntityId, securityService.getLoggedUser(),log);
        projectService.deleteById(Integer.valueOf(projectId));
        return ResponseEntity.ok().build();
    }

    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertProject(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.entity.Project project) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Project.staticEntityId, it.anggen.model.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (project.getProjectId()!=null)
        log.info("Inserting project like "+ project.getName()+' '+ project.getProjectId());
        it.anggen.model.entity.Project insertedProject=projectService.insert(project);
        getRightMapping(insertedProject);
        logEntryService.addLogEntry( "Inserted project with id "+ insertedProject.getProjectId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.CREATE_ENTITY, it.anggen.model.entity.Project.staticEntityId, securityService.getLoggedUser(),log);
        return ResponseEntity.ok().body(insertedProject);
    }

    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateProject(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.entity.Project project) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Project.staticEntityId, it.anggen.model.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        logEntryService.addLogEntry( "Updating project with id "+project.getProjectId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.UPDATE_ENTITY, it.anggen.model.entity.Project.staticEntityId, securityService.getLoggedUser(),log);
        rebuildSecurityMapping(project);
        it.anggen.model.entity.Project updatedProject=projectService.update(project);
        getSecurityMapping(updatedProject);
        getRightMapping(updatedProject);
        return ResponseEntity.ok().body(updatedProject);
    }

    private List<it.anggen.model.entity.Project> getRightMapping(List<it.anggen.model.entity.Project> projectList) {
        for (it.anggen.model.entity.Project project: projectList)
        {
        getRightMapping(project);
        }
        return projectList;
    }

    private void getRightMapping(it.anggen.model.entity.Project project) {
        if (project.getEntityGroupList()!=null)
        for (it.anggen.model.entity.EntityGroup entityGroup :project.getEntityGroupList())

        {

        entityGroup.setRestrictionEntityGroupList(null);
        entityGroup.setEntityList(null);
        entityGroup.setProject(null);
        }
        if (project.getEnumEntityList()!=null)
        for (it.anggen.model.entity.EnumEntity enumEntity :project.getEnumEntityList())

        {

        enumEntity.setProject(null);
        enumEntity.setEnumValueList(null);
        }
    }

    private void rebuildSecurityMapping(it.anggen.model.entity.Project project) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EntityGroup.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        project.setEntityGroupList(projectService.findById(project.getProjectId()).get(0).getEntityGroupList());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EnumEntity.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        project.setEnumEntityList(projectService.findById(project.getProjectId()).get(0).getEnumEntityList());
    }

    private List<it.anggen.model.entity.Project> getSecurityMapping(List<it.anggen.model.entity.Project> projectList) {
        for (it.anggen.model.entity.Project project: projectList)
        {
        getSecurityMapping(project);
        }
        return projectList;
    }

    private void getSecurityMapping(it.anggen.model.entity.Project project) {
        if (securityEnabled && project.getEntityGroupList()!=null && !securityService.hasPermission(it.anggen.model.entity.EntityGroup.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        project.setEntityGroupList(null);

        if (securityEnabled && project.getEnumEntityList()!=null && !securityService.hasPermission(it.anggen.model.entity.EnumEntity.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        project.setEnumEntityList(null);

    }

}
