
package it.polimi.service.field;

import java.util.List;

import it.polimi.searchbean.field.EnumValueSearchBean;

public interface EnumValueService {


    public List<it.polimi.model.field.EnumValue> findById(Long EnumValueId);

    public List<it.polimi.model.field.EnumValue> find(EnumValueSearchBean EnumValue);

    public void deleteById(Long EnumValueId);

    public it.polimi.model.field.EnumValue insert(it.polimi.model.field.EnumValue EnumValue);

    public it.polimi.model.field.EnumValue update(it.polimi.model.field.EnumValue EnumValue);

}
