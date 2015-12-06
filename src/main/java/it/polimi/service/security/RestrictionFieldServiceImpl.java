
package it.polimi.service.security;

import java.util.List;

import it.polimi.repository.security.RestrictionFieldRepository;
import it.polimi.searchbean.security.RestrictionFieldSearchBean;
import it.polimi.service.security.RestrictionFieldService;

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
    public List<it.polimi.model.security.RestrictionField> findById(Long restrictionFieldId) {
        return restrictionFieldRepository.findByRestrictionFieldId(restrictionFieldId);
    }

    @Override
    public List<it.polimi.model.security.RestrictionField> find(RestrictionFieldSearchBean restrictionField) {
        return restrictionFieldRepository.findByRestrictionFieldIdAndFieldAndRole(restrictionField.getRestrictionFieldId(),restrictionField.getField(),restrictionField.getRole());
    }

    @Override
    public void deleteById(Long restrictionFieldId) {
        restrictionFieldRepository.delete(restrictionFieldId);
        return;
    }

    @Override
    public it.polimi.model.security.RestrictionField insert(it.polimi.model.security.RestrictionField restrictionField) {
        return restrictionFieldRepository.save(restrictionField);
    }

    @Override
    @Transactional
    public it.polimi.model.security.RestrictionField update(it.polimi.model.security.RestrictionField restrictionField) {
        it.polimi.model.security.RestrictionField returnedRestrictionField=restrictionFieldRepository.save(restrictionField);
        if (restrictionField.getField()!=null)
        {
        List<it.polimi.model.security.RestrictionField> restrictionFieldList = restrictionFieldRepository.findByField( restrictionField.getField());
        if (!restrictionFieldList.contains(returnedRestrictionField))
        restrictionFieldList.add(returnedRestrictionField);
        returnedRestrictionField.getField().setRestrictionFieldList(restrictionFieldList);
        }
        if (restrictionField.getRole()!=null)
        {
        List<it.polimi.model.security.RestrictionField> restrictionFieldList = restrictionFieldRepository.findByRole( restrictionField.getRole());
        if (!restrictionFieldList.contains(returnedRestrictionField))
        restrictionFieldList.add(returnedRestrictionField);
        returnedRestrictionField.getRole().setRestrictionFieldList(restrictionFieldList);
        }
         return returnedRestrictionField;
    }

}
