
package it.polimi.repository;

import java.sql.Timestamp;
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

    public List<Example> findByBirthTime(Timestamp birthTime);

    @Query("select e from Example e where  (:exampleId is null or cast(:exampleId as string)=cast(e.exampleId as string)) and (:name is null or :name='' or cast(:name as string)=e.name) and (:eta is null or cast(:eta as string)=cast(e.eta as string)) and (:male is null or cast(:male as string)=cast(e.male as string)) and (:birthDate is null or cast(:birthDate as string)=cast(date(e.birthDate) as string)) and (:birthTime is null or cast(:birthTime as string)=cast(e.birthTime as string)) ")
    public List<Example> findByExampleIdAndNameAndEtaAndMaleAndBirthDateAndBirthTime(
        @Param("exampleId")
        Integer exampleId,
        @Param("name")
        String name,
        @Param("eta")
        Long eta,
        @Param("male")
        Boolean male,
        @Param("birthDate")
        String birthDate,
        @Param("birthTime")
        Timestamp birthTime);

}
