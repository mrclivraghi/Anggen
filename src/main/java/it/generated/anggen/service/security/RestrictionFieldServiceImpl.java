
package it.generated.anggen.service.security;

import java.util.List;
import it.generated.anggen.repository.security.RestrictionFieldRepository;
import it.generated.anggen.searchbean.security.RestrictionFieldSearchBean;
import it.generated.anggen.service.security.RestrictionFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestrictionFieldServiceImpl
    implements RestrictionFieldService
{

    @Autowired
    public RestrictionFieldRepository restrictionFieldRepository;

    @Override
    public List<it.generated.anggen.model.security.RestrictionField> findById(Long restrictionFieldId) {
        return restrictionFieldRepository.findByRestrictionFieldId(restrictionFieldId);
    }

    @Override
    public List<it.generated.anggen.model.security.RestrictionField> find(RestrictionFieldSearchBean restrictionField) {
        return restrictionFieldRepository.findByRestrictionFieldIdAndRoleAndField(restrictionField.getRestrictionFieldId(),restrictionField.getRole(),restrictionField.getField());
    }

    @Override
    public void deleteById(Long restrictionFieldId) {
        restrictionFieldRepository.delete(restrictionFieldId);
        return;
    }

    @Override
    public it.generated.anggen.model.security.RestrictionField insert(it.generated.anggen.model.security.RestrictionField restrictionField) {
        return restrictionFieldRepository.save(restrictionField);
    }

    @Override
    @Transactional
    public it.generated.anggen.model.security.RestrictionField update(it.generated.anggen.model.security.RestrictionField restrictionField) {
        it.generated.anggen.model.security.RestrictionField returnedRestrictionField=restrictionFieldRepository.save(restrictionField);
        if (restrictionField.getRole()!=null)
        {
        List<it.generated.anggen.model.security.RestrictionField> restrictionFieldList = restrictionFieldRepository.findByRole( restrictionField.getRole());
        if (!restrictionFieldList.contains(returnedRestrictionField))
        restrictionFieldList.add(returnedRestrictionField);
        returnedRestrictionField.getRole().setRestrictionFieldList(restrictionFieldList);
        }
        if (restrictionField.getField()!=null)
        {
        List<it.generated.anggen.model.security.RestrictionField> restrictionFieldList = restrictionFieldRepository.findByField( restrictionField.getField());
        if (!restrictionFieldList.contains(returnedRestrictionField))
        restrictionFieldList.add(returnedRestrictionField);
        returnedRestrictionField.getField().setRestrictionFieldList(restrictionFieldList);
        }
         return returnedRestrictionField;
    }

}
