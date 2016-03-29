
package it.anggen.repository.security;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestrictionFieldRepository
    extends JpaRepository<it.anggen.model.security.RestrictionField, java.lang.Long>
{


    @Query("select r from RestrictionField r")
    public List<it.anggen.model.security.RestrictionField> findAll();

    public List<it.anggen.model.security.RestrictionField> findByRestrictionFieldId(java.lang.Long restrictionFieldId);

    public List<it.anggen.model.security.RestrictionField> findByField(it.anggen.model.field.Field field);

    public List<it.anggen.model.security.RestrictionField> findByRole(it.anggen.model.security.Role role);

    @Query("select r from RestrictionField r where  (:restrictionFieldId is null or cast(:restrictionFieldId as string)=cast(r.restrictionFieldId as string)) and (:field=r.field or :field is null) and (:role=r.role or :role is null) ")
    public List<it.anggen.model.security.RestrictionField> findByRestrictionFieldIdAndFieldAndRole(
        @org.springframework.data.repository.query.Param("restrictionFieldId")
        java.lang.Long restrictionFieldId,
        @org.springframework.data.repository.query.Param("field")
        it.anggen.model.field.Field field,
        @org.springframework.data.repository.query.Param("role")
        it.anggen.model.security.Role role);

}
