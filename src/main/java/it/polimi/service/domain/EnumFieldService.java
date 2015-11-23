
package it.polimi.service.domain;

import java.util.List;
import it.polimi.model.domain.EnumField;
import it.polimi.searchbean.domain.EnumFieldSearchBean;

public interface EnumFieldService {


    public List<EnumField> findById(Long enumFieldId);

    public List<EnumField> find(EnumFieldSearchBean enumField);

    public void deleteById(Long enumFieldId);

    public EnumField insert(EnumField enumField);

    public EnumField update(EnumField enumField);

}
