
package it.anggen.service.field;

import java.util.List;
import it.anggen.searchbean.field.EnumFieldSearchBean;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

public interface EnumFieldService {


    public List<it.anggen.model.field.EnumField> findById(Long EnumFieldId);

    public List<it.anggen.model.field.EnumField> find(EnumFieldSearchBean EnumField);

    public void deleteById(Long EnumFieldId);

    public it.anggen.model.field.EnumField insert(it.anggen.model.field.EnumField EnumField);

    public it.anggen.model.field.EnumField update(it.anggen.model.field.EnumField EnumField);

    public Page<it.anggen.model.field.EnumField> findByPage(
        @PathVariable
        Integer pageNumber);

}
