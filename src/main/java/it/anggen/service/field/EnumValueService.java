
package it.anggen.service.field;

import java.util.List;
import it.anggen.searchbean.field.EnumValueSearchBean;

public interface EnumValueService {


    public List<it.anggen.model.field.EnumValue> findById(Long EnumValueId);

    public List<it.anggen.model.field.EnumValue> find(EnumValueSearchBean EnumValue);

    public void deleteById(Long EnumValueId);

    public it.anggen.model.field.EnumValue insert(it.anggen.model.field.EnumValue EnumValue);

    public it.anggen.model.field.EnumValue update(it.anggen.model.field.EnumValue EnumValue);

}
