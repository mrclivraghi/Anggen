
package it.anggen.repository.security;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestrictionFieldRepository
    extends CrudRepository<it.anggen.model.security.RestrictionField, java.lang.Long>
{


    public List<it.anggen.model.security.RestrictionField> findByRestrictionFieldId(java.lang.Long restrictionFieldId);

    public List<it.anggen.model.security.RestrictionField> findByRole(it.anggen.model.security.Role role);

    public List<it.anggen.model.security.RestrictionField> findByField(it.anggen.model.field.Field field);

    @Query("select r from RestrictionField r where  (:restrictionFieldId is null or cast(:restrictionFieldId as string)=cast(r.restrictionFieldId as string)) and (:role=r.role or :role is null) and (:field=r.field or :field is null) ")
    public List<it.anggen.model.security.RestrictionField> findByRestrictionFieldIdAndRoleAndField(
        @org.springframework.data.repository.query.Param("restrictionFieldId")
        java.lang.Long restrictionFieldId,
        @org.springframework.data.repository.query.Param("role")
        it.anggen.model.security.Role role,
        @org.springframework.data.repository.query.Param("field")
        it.anggen.model.field.Field field);

}
