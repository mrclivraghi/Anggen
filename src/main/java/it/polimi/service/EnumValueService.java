
package it.polimi.service;

import java.util.List;

import it.polimi.model.field.EnumValue;
import it.polimi.searchbean.EnumValueSearchBean;

public interface EnumValueService {


    public List<EnumValue> findById(Long enumValueId);

    public List<EnumValue> find(EnumValueSearchBean enumValue);

    public void deleteById(Long enumValueId);

    public EnumValue insert(EnumValue enumValue);

    public EnumValue update(EnumValue enumValue);

}
