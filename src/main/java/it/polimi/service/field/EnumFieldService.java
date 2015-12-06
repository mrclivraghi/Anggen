
package it.polimi.service.field;

import java.util.List;

import it.polimi.searchbean.field.EnumFieldSearchBean;

public interface EnumFieldService {


    public List<it.polimi.model.field.EnumField> findById(Long EnumFieldId);

    public List<it.polimi.model.field.EnumField> find(EnumFieldSearchBean EnumField);

    public void deleteById(Long EnumFieldId);

    public it.polimi.model.field.EnumField insert(it.polimi.model.field.EnumField EnumField);

    public it.polimi.model.field.EnumField update(it.polimi.model.field.EnumField EnumField);

}
