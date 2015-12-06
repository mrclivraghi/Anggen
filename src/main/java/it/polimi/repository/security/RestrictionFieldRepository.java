
package it.polimi.repository.security;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestrictionFieldRepository
    extends CrudRepository<it.polimi.model.security.RestrictionField, java.lang.Long>
{


    public List<it.polimi.model.security.RestrictionField> findByRestrictionFieldId(java.lang.Long restrictionFieldId);

    public List<it.polimi.model.security.RestrictionField> findByField(it.polimi.model.field.Field field);

    public List<it.polimi.model.security.RestrictionField> findByRole(it.polimi.model.security.Role role);

    @Query("select r from RestrictionField r where  (:restrictionFieldId is null or cast(:restrictionFieldId as string)=cast(r.restrictionFieldId as string)) and (:field=r.field or :field is null) and (:role=r.role or :role is null) ")
    public List<it.polimi.model.security.RestrictionField> findByRestrictionFieldIdAndFieldAndRole(
        @org.springframework.data.repository.query.Param("restrictionFieldId")
        java.lang.Long restrictionFieldId,
        @org.springframework.data.repository.query.Param("field")
        it.polimi.model.field.Field field,
        @org.springframework.data.repository.query.Param("role")
        it.polimi.model.security.Role role);

}
