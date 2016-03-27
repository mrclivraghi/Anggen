
package it.anggen.service.entity;

import java.util.List;
import it.anggen.searchbean.entity.EnumEntitySearchBean;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

public interface EnumEntityService {


    public List<it.anggen.model.entity.EnumEntity> findById(Long EnumEntityId);

    public List<it.anggen.model.entity.EnumEntity> find(EnumEntitySearchBean EnumEntity);

    public void deleteById(Long EnumEntityId);

    public it.anggen.model.entity.EnumEntity insert(it.anggen.model.entity.EnumEntity EnumEntity);

    public it.anggen.model.entity.EnumEntity update(it.anggen.model.entity.EnumEntity EnumEntity);

    public Page<it.anggen.model.entity.EnumEntity> findByPage(
        @PathVariable
        Integer pageNumber);

}
