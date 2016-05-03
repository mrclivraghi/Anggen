
package it.anggen.controller.generation;

import java.util.List;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import it.anggen.searchbean.generation.GenerationRunSearchBean;
import it.anggen.security.SecurityService;
import it.anggen.service.generation.GenerationRunService;
import it.anggen.service.log.LogEntryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/generationRun")
public class GenerationRunController {

    @org.springframework.beans.factory.annotation.Autowired
    private GenerationRunService generationRunService;
    @org.springframework.beans.factory.annotation.Autowired
    private SecurityService securityService;
    @org.springframework.beans.factory.annotation.Autowired
    private LogEntryService logEntryService;
    private final static Logger log = LoggerFactory.getLogger(it.anggen.model.generation.GenerationRun.class);
    @Value("${application.security}")
    private Boolean securityEnabled;

    @ApiOperation(value = "Return a page of generationRun", notes = "Return a single page of generationRun", response = it.anggen.model.generation.GenerationRun.class, responseContainer = "List")
    @Timed
    @RequestMapping(value = "/pages/{pageNumber}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity findPage(
        @PathVariable
        Integer pageNumber) {
        org.springframework.data.domain.Page<it.anggen.model.generation.GenerationRun> page = generationRunService.findByPage(pageNumber);
        getRightMapping(page.getContent());
        return ResponseEntity.ok().body(page);
    }

    @ApiOperation(value = "Return a list of generationRun", notes = "Return a list of generationRun based on the search bean requested", response = it.anggen.model.generation.GenerationRun.class, responseContainer = "List")
    @Timed
    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        GenerationRunSearchBean generationRun) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.generation.GenerationRun.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.anggen.model.generation.GenerationRun> generationRunList;
        if (generationRun.getGenerationRunId()!=null)
         log.info("Searching generationRun like {}",generationRun.toString());
        logEntryService.addLogEntry( "Searching entity like "+generationRun.toString(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.SEARCH_ENTITY, it.anggen.model.generation.GenerationRun.staticEntityId, securityService.getLoggedUser(),log);
        generationRunList=generationRunService.find(generationRun);
        getSecurityMapping(generationRunList);
        getRightMapping(generationRunList);
         log.info("Search: returning {} generationRun.",generationRunList.size());
        return ResponseEntity.ok().body(generationRunList);
    }

    @ApiOperation(value = "Return a the generationRun identified by the given id", notes = "Return a the generationRun identified by the given id", response = it.anggen.model.generation.GenerationRun.class, responseContainer = "List")
    @Timed
    @ResponseBody
    @RequestMapping(value = "/{generationRunId}", method = RequestMethod.GET)
    public ResponseEntity getGenerationRunById(
        @PathVariable
        String generationRunId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.generation.GenerationRun.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        logEntryService.addLogEntry( "Searching generationRun with id "+generationRunId,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.SEARCH_ENTITY, it.anggen.model.generation.GenerationRun.staticEntityId, securityService.getLoggedUser(),log);
        List<it.anggen.model.generation.GenerationRun> generationRunList=generationRunService.findById(Integer.valueOf(generationRunId));
        getSecurityMapping(generationRunList);
        getRightMapping(generationRunList);
         log.info("Search: returning {} generationRun.",generationRunList.size());
        return ResponseEntity.ok().body(generationRunList);
    }

    @ApiOperation(value = "Delete the generationRun identified by the given id", notes = "Delete the generationRun identified by the given id")
    @Timed
    @ResponseBody
    @RequestMapping(value = "/{generationRunId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteGenerationRunById(
        @PathVariable
        String generationRunId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.generation.GenerationRun.staticEntityId, it.anggen.model.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting generationRun with id "+generationRunId);
        logEntryService.addLogEntry( "Deleting generationRun with id {}"+generationRunId,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.DELETE_ENTITY, it.anggen.model.generation.GenerationRun.staticEntityId, securityService.getLoggedUser(),log);
        generationRunService.deleteById(Integer.valueOf(generationRunId));
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Insert the generationRun given", notes = "Insert the generationRun given ", response = it.anggen.model.generation.GenerationRun.class)
    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertGenerationRun(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.generation.GenerationRun generationRun) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.generation.GenerationRun.staticEntityId, it.anggen.model.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (generationRun.getGenerationRunId()!=null)
        log.info("Inserting generationRun like "+generationRun.toString());
        it.anggen.model.generation.GenerationRun insertedGenerationRun=generationRunService.insert(generationRun);
        getRightMapping(insertedGenerationRun);
        logEntryService.addLogEntry( "Inserted generationRun with id "+ insertedGenerationRun.getGenerationRunId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.CREATE_ENTITY, it.anggen.model.generation.GenerationRun.staticEntityId, securityService.getLoggedUser(),log);
        return ResponseEntity.ok().body(insertedGenerationRun);
    }

    @ApiOperation(value = "Update the generationRun given", notes = "Update the generationRun given ", response = it.anggen.model.generation.GenerationRun.class)
    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateGenerationRun(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.generation.GenerationRun generationRun) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.generation.GenerationRun.staticEntityId, it.anggen.model.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        logEntryService.addLogEntry( "Updating generationRun with id "+generationRun.getGenerationRunId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.UPDATE_ENTITY, it.anggen.model.generation.GenerationRun.staticEntityId, securityService.getLoggedUser(),log);
        rebuildSecurityMapping(generationRun);
        it.anggen.model.generation.GenerationRun updatedGenerationRun=generationRunService.update(generationRun);
        getSecurityMapping(updatedGenerationRun);
        getRightMapping(updatedGenerationRun);
        return ResponseEntity.ok().body(updatedGenerationRun);
    }

    private List<it.anggen.model.generation.GenerationRun> getRightMapping(List<it.anggen.model.generation.GenerationRun> generationRunList) {
        for (it.anggen.model.generation.GenerationRun generationRun: generationRunList)
        {
        getRightMapping(generationRun);
        }
        return generationRunList;
    }

    private void getRightMapping(it.anggen.model.generation.GenerationRun generationRun) {
        if (generationRun.getProject()!=null)
        {
        generationRun.getProject().setEntityGroupList(null);
        generationRun.getProject().setEnumEntityList(null);
        generationRun.getProject().setGenerationRunList(null);
        }
    }

    private void rebuildSecurityMapping(it.anggen.model.generation.GenerationRun generationRun) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Project.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        generationRun.setProject(generationRunService.findById(generationRun.getGenerationRunId()).get(0).getProject());
    }

    private List<it.anggen.model.generation.GenerationRun> getSecurityMapping(List<it.anggen.model.generation.GenerationRun> generationRunList) {
        for (it.anggen.model.generation.GenerationRun generationRun: generationRunList)
        {
        getSecurityMapping(generationRun);
        }
        return generationRunList;
    }

    private void getSecurityMapping(it.anggen.model.generation.GenerationRun generationRun) {
        if (securityEnabled && generationRun.getProject()!=null  && !securityService.hasPermission(it.anggen.model.entity.Project.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        generationRun.setProject(null);

    }

}
