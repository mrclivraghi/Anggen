
package it.anggen.service.field;

import java.util.List;
import it.anggen.searchbean.field.FieldSearchBean;

public interface FieldService {


    public List<it.anggen.model.field.Field> findById(Long FieldId);

    public List<it.anggen.model.field.Field> find(FieldSearchBean Field);

    public void deleteById(Long FieldId);

    public it.anggen.model.field.Field insert(it.anggen.model.field.Field Field);

    public it.anggen.model.field.Field update(it.anggen.model.field.Field Field);

}
