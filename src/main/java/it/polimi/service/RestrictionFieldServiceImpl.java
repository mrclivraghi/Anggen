
package it.polimi.service;

import java.util.List;

import it.polimi.model.security.RestrictionField;
import it.polimi.repository.RestrictionFieldRepository;
import it.polimi.searchbean.RestrictionFieldSearchBean;
import it.polimi.service.RestrictionFieldService;

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
    public List<RestrictionField> findById(Long restrictionFieldId) {
        return restrictionFieldRepository.findByRestrictionFieldId(restrictionFieldId);
    }

    @Override
    public List<RestrictionField> find(RestrictionFieldSearchBean restrictionField) {
        return restrictionFieldRepository.findByRestrictionFieldIdAndFieldAndRole(restrictionField.getRestrictionFieldId(),restrictionField.getField(),restrictionField.getRole());
    }

    @Override
    public void deleteById(Long restrictionFieldId) {
        restrictionFieldRepository.delete(restrictionFieldId);
        return;
    }

    @Override
    public RestrictionField insert(RestrictionField restrictionField) {
        return restrictionFieldRepository.save(restrictionField);
    }

    @Override
    @Transactional
    public RestrictionField update(RestrictionField restrictionField) {
        RestrictionField returnedRestrictionField=restrictionFieldRepository.save(restrictionField);
        if (restrictionField.getField()!=null)
        {
        List<RestrictionField> restrictionFieldList = restrictionFieldRepository.findByField( restrictionField.getField());
        if (!restrictionFieldList.contains(returnedRestrictionField))
        restrictionFieldList.add(returnedRestrictionField);
        returnedRestrictionField.getField().setRestrictionFieldList(restrictionFieldList);
        }
        if (restrictionField.getRole()!=null)
        {
        List<RestrictionField> restrictionFieldList = restrictionFieldRepository.findByRole( restrictionField.getRole());
        if (!restrictionFieldList.contains(returnedRestrictionField))
        restrictionFieldList.add(returnedRestrictionField);
        returnedRestrictionField.getRole().setRestrictionFieldList(restrictionFieldList);
        }
         return returnedRestrictionField;
    }

}
