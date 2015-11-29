
package it.generated.anggen.service.field;

import java.util.List;
import it.generated.anggen.searchbean.field.EnumValueSearchBean;

public interface EnumValueService {


    public List<it.generated.anggen.model.field.EnumValue> findById(Long EnumValueId);

    public List<it.generated.anggen.model.field.EnumValue> find(EnumValueSearchBean EnumValue);

    public void deleteById(Long EnumValueId);

    public it.generated.anggen.model.field.EnumValue insert(it.generated.anggen.model.field.EnumValue EnumValue);

    public it.generated.anggen.model.field.EnumValue update(it.generated.anggen.model.field.EnumValue EnumValue);

}
