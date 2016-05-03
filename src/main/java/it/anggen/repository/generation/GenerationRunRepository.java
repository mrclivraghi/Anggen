
package it.anggen.repository.generation;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GenerationRunRepository
    extends JpaRepository<it.anggen.model.generation.GenerationRun, java.lang.Integer>
{


    @Query("select g from GenerationRun g")
    public List<it.anggen.model.generation.GenerationRun> findAll();

    public List<it.anggen.model.generation.GenerationRun> findByGenerationRunId(java.lang.Integer generationRunId);

    public List<it.anggen.model.generation.GenerationRun> findByStartDate(java.lang.String startDate);

    public List<it.anggen.model.generation.GenerationRun> findByEndDate(java.lang.String endDate);

    public List<it.anggen.model.generation.GenerationRun> findByStatus(java.lang.Integer status);

    public List<it.anggen.model.generation.GenerationRun> findByProject(it.anggen.model.entity.Project project);

    @Query("select g from GenerationRun g where  (:generationRunId is null or cast(:generationRunId as string)=cast(g.generationRunId as string)) and (:startDate is null or cast(:startDate as string)=cast(date(g.startDate) as string)) and (:endDate is null or cast(:endDate as string)=cast(date(g.endDate) as string)) and (:status is null or cast(:status as string)=cast(g.status as string)) and (:project=g.project or :project is null) ")
    public List<it.anggen.model.generation.GenerationRun> findByGenerationRunIdAndStartDateAndEndDateAndStatusAndProject(
        @org.springframework.data.repository.query.Param("generationRunId")
        java.lang.Integer generationRunId,
        @org.springframework.data.repository.query.Param("startDate")
        java.lang.String startDate,
        @org.springframework.data.repository.query.Param("endDate")
        java.lang.String endDate,
        @org.springframework.data.repository.query.Param("status")
        java.lang.Integer status,
        @org.springframework.data.repository.query.Param("project")
        it.anggen.model.entity.Project project);

}
