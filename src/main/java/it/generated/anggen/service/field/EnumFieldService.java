
package it.generated.anggen.service.field;

import java.util.List;
import it.generated.anggen.searchbean.field.EnumFieldSearchBean;

public interface EnumFieldService {


    public List<it.generated.anggen.model.field.EnumField> findById(Long EnumFieldId);

    public List<it.generated.anggen.model.field.EnumField> find(EnumFieldSearchBean EnumField);

    public void deleteById(Long EnumFieldId);

    public it.generated.anggen.model.field.EnumField insert(it.generated.anggen.model.field.EnumField EnumField);

    public it.generated.anggen.model.field.EnumField update(it.generated.anggen.model.field.EnumField EnumField);

}
