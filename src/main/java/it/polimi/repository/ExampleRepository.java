
package it.polimi.repository;

import java.util.List;
import it.polimi.model.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleRepository
    extends CrudRepository<Example, Integer>
{


    public List<Example> findByExampleId(Integer exampleId);

    public List<Example> findByName(String name);

    public List<Example> findByEta(Long eta);

    public List<Example> findByMale(Boolean male);

    public List<Example> findByBirthDate(String birthDate);

    public List<Example> findByBirthTime(String birthTime);

    public List<Example> findBySex(Integer sex);

    @Query("select e from Example e where  (:exampleId is null or cast(:exampleId as string)=cast(e.exampleId as string)) and (:name is null or :name='' or cast(:name as string)=e.name) and (:etaFrom is null or cast(:etaFrom as string)<=cast(e.eta as string)) and (:etaTo is null or cast(:etaTo as string)>=cast(e.eta as string)) and (:male is null or cast(:male as string)=cast(e.male as string)) and (:birthDateFrom is null or cast(:birthDateFrom as string)<=cast(date(e.birthDate) as string)) and (:birthDateTo is null or cast(:birthDateTo as string)>=cast(date(e.birthDate) as string)) and (:birthTime is null or cast(:birthTime as string)=cast(date_trunc('seconds',e.birthTime) as string)) and (:sex is null or cast(:sex as string)=cast(e.sex as string)) ")
    public List<Example> findByExampleIdAndNameAndGreaterThanEtaFromAndLessThanEtaToAndMaleAndGreaterThanBirthDateFromAndLessThanBirthDateToAndBirthTimeAndSex(
        @Param("exampleId")
        Integer exampleId,
        @Param("name")
        String name,
        @Param("etaFrom")
        Long etaFrom,
        @Param("etaTo")
        Long etaTo,
        @Param("male")
        Boolean male,
        @Param("birthDateFrom")
        String birthDateFrom,
        @Param("birthDateTo")
        String birthDateTo,
        @Param("birthTime")
        String birthTime,
        @Param("sex")
        Integer sex);

}
