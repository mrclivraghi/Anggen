
package it.generated.anggen.service.field;

import java.util.List;
import it.generated.anggen.searchbean.field.FieldSearchBean;

public interface FieldService {


    public List<it.generated.anggen.model.field.Field> findById(Long FieldId);

    public List<it.generated.anggen.model.field.Field> find(FieldSearchBean Field);

    public void deleteById(Long FieldId);

    public it.generated.anggen.model.field.Field insert(it.generated.anggen.model.field.Field Field);

    public it.generated.anggen.model.field.Field update(it.generated.anggen.model.field.Field Field);

}
