
package it.polimi.service.field;

import java.util.List;

import it.polimi.searchbean.field.FieldSearchBean;

public interface FieldService {


    public List<it.polimi.model.field.Field> findById(Long FieldId);

    public List<it.polimi.model.field.Field> find(FieldSearchBean Field);

    public void deleteById(Long FieldId);

    public it.polimi.model.field.Field insert(it.polimi.model.field.Field Field);

    public it.polimi.model.field.Field update(it.polimi.model.field.Field Field);

}
