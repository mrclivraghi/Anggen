
package it.polimi.service.domain;

import java.util.List;
import it.polimi.model.domain.EnumValue;
import it.polimi.searchbean.domain.EnumValueSearchBean;

public interface EnumValueService {


    public List<EnumValue> findById(Long enumValueId);

    public List<EnumValue> find(EnumValueSearchBean enumValue);

    public void deleteById(Long enumValueId);

    public EnumValue insert(EnumValue enumValue);

    public EnumValue update(EnumValue enumValue);

}
