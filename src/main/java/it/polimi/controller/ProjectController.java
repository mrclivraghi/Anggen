
package it.polimi.controller;

import java.util.List;
import it.polimi.searchbean.ProjectSearchBean;
import it.polimi.service.ProjectService;
import it.polimi.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final static Logger log = LoggerFactory.getLogger(it.polimi.model.entity.Project.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (!securityService.isAllowed(it.polimi.model.entity.Project.entityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "project";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        ProjectSearchBean project) {
        if (!securityService.isAllowed(it.polimi.model.entity.Project.entityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.polimi.model.entity.Project> projectList;
        if (project.getProjectId()!=null)
         log.info("Searching project like {}",project.toString());
        projectList=projectService.find(project);
        getRightMapping(projectList);
        getSecurityMapping(projectList);
         log.info("Search: returning {} project.",projectList.size());
        return ResponseEntity.ok().body(projectList);
    }

    @ResponseBody
    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public ResponseEntity getProjectById(
        @PathVariable
        String projectId) {
        if (!securityService.isAllowed(it.polimi.model.entity.Project.entityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Searching project with id {}",projectId);
        List<it.polimi.model.entity.Project> projectList=projectService.findById(Integer.valueOf(projectId));
        getRightMapping(projectList);
         log.info("Search: returning {} project.",projectList.size());
        return ResponseEntity.ok().body(projectList);
    }

    @ResponseBody
    @RequestMapping(value = "/{projectId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProjectById(
        @PathVariable
        String projectId) {
        if (!securityService.isAllowed(it.polimi.model.entity.Project.entityId, it.polimi.model.security.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting project with id {}",projectId);
        projectService.deleteById(Integer.valueOf(projectId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertProject(
        @org.springframework.web.bind.annotation.RequestBody
        it.polimi.model.entity.Project project) {
        if (!securityService.isAllowed(it.polimi.model.entity.Project.entityId, it.polimi.model.security.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (project.getProjectId()!=null)
        log.info("Inserting project like {}",project.toString());
        it.polimi.model.entity.Project insertedProject=projectService.insert(project);
        getRightMapping(insertedProject);
        log.info("Inserted project with id {}",insertedProject.getProjectId());
        return ResponseEntity.ok().body(insertedProject);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateProject(
        @org.springframework.web.bind.annotation.RequestBody
        it.polimi.model.entity.Project project) {
        if (!securityService.isAllowed(it.polimi.model.entity.Project.entityId, it.polimi.model.security.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Updating project with id {}",project.getProjectId());
        rebuildSecurityMapping(project);
        it.polimi.model.entity.Project updatedProject=projectService.update(project);
        getRightMapping(updatedProject);
        getSecurityMapping(updatedProject);
        return ResponseEntity.ok().body(updatedProject);
    }

    private List<it.polimi.model.entity.Project> getRightMapping(List<it.polimi.model.entity.Project> projectList) {
        for (it.polimi.model.entity.Project project: projectList)
        {
        getRightMapping(project);
        }
        return projectList;
    }

    private void getRightMapping(it.polimi.model.entity.Project project) {
        if (project.getEntityGroupList()!=null)
        for (it.polimi.model.entity.EntityGroup entityGroup :project.getEntityGroupList())
        {
        	entityGroup.setProject(null);
        }
    }

    private void rebuildSecurityMapping(it.polimi.model.entity.Project project) {
        if (!securityService.isAllowed(it.polimi.model.entity.EntityGroup.entityId, it.polimi.model.security.RestrictionType.SEARCH))
        project.setEntityGroupList(projectService.findById(project.getProjectId()).get(0).getEntityGroupList());
    }

    private List<it.polimi.model.entity.Project> getSecurityMapping(List<it.polimi.model.entity.Project> projectList) {
        for (it.polimi.model.entity.Project project: projectList)
        {
        getSecurityMapping(project);
        }
        return projectList;
    }

    private void getSecurityMapping(it.polimi.model.entity.Project project) {
        if (project.getEntityGroupList()!=null && !securityService.isAllowed(it.polimi.model.entity.EntityGroup.entityId, it.polimi.model.security.RestrictionType.SEARCH) )
        project.setEntityGroupList(null);

    }

}
