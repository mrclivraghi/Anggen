
package it.polimi.service.domain;

import java.util.List;
import it.polimi.model.domain.Field;
import it.polimi.searchbean.domain.FieldSearchBean;

public interface FieldService {


    public List<Field> findById(Long fieldId);

    public List<Field> find(FieldSearchBean field);

    public void deleteById(Long fieldId);

    public Field insert(Field field);

    public Field update(Field field);

}
