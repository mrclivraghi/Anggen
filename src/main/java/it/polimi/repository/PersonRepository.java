
package it.polimi.repository;

import java.util.List;
import it.polimi.model.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository
    extends CrudRepository<Person, Long>
{


    public List<Person> findByPersonId(Long personId);

    public List<Person> findByFirstName(String firstName);

    public List<Person> findByLastName(String lastName);

    public List<Person> findByBirthDate(String birthDate);

    @Query("select i from it.polimi.model.Person i where  (:personId is null or cast(:personId as string)=cast(i.personId as string)) and (:firstName is null or :firstName='' or cast(:firstName as string)=i.firstName) and (:lastName is null or :lastName='' or cast(:lastName as string)=i.lastName) and (:birthDate is null or cast(:birthDate as string)=cast(i.birthDate as string)) ")
    public List<Person> findByPersonIdAndFirstNameAndLastNameAndBirthDate(
        @Param("personId")
        Long personId,
        @Param("firstName")
        String firstName,
        @Param("lastName")
        String lastName,
        @Param("birthDate")
        String birthDate);

}
