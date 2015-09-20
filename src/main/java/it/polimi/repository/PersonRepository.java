
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


    public Person findByPersonId(Long personId);

    public Person findByFirstName(String firstName);

    public Person findByLastName(String lastName);

    public Person findByBirthDate(String birthDate);

    @Query("select p from Person p where  (:personId is null or cast(:personId as string)=cast(p.personId as string)) and (:firstName is null or :firstName='' or cast(:firstName as string)=p.firstName) and (:lastName is null or :lastName='' or cast(:lastName as string)=p.lastName) and (:birthDate is null or cast(:birthDate as string)=cast(p.birthDate as string)) ")
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
