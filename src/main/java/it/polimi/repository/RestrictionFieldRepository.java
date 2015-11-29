
package it.polimi.repository;

import java.util.List;

import it.polimi.model.field.Field;
import it.polimi.model.security.RestrictionField;
import it.polimi.model.security.Role;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RestrictionFieldRepository
    extends CrudRepository<RestrictionField, Long>
{


    public List<RestrictionField> findByRestrictionFieldId(Long restrictionFieldId);

    public List<RestrictionField> findByField(Field field);

    public List<RestrictionField> findByRole(Role role);

    @Query("select r from RestrictionField r where  (:restrictionFieldId is null or cast(:restrictionFieldId as string)=cast(r.restrictionFieldId as string)) and (:field=r.field or :field is null) and (:role=r.role or :role is null) ")
    public List<RestrictionField> findByRestrictionFieldIdAndFieldAndRole(
        @Param("restrictionFieldId")
        Long restrictionFieldId,
        @Param("field")
        Field field,
        @Param("role")
        Role role);

}
