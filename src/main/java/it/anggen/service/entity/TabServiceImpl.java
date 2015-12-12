
package it.anggen.service.entity;

import java.util.List;
import it.anggen.repository.entity.TabRepository;
import it.anggen.repository.field.EnumFieldRepository;
import it.anggen.repository.field.FieldRepository;
import it.anggen.repository.relationship.RelationshipRepository;
import it.anggen.searchbean.entity.TabSearchBean;
import it.anggen.service.entity.TabService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TabServiceImpl
    implements TabService
{

    @org.springframework.beans.factory.annotation.Autowired
    public TabRepository tabRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public FieldRepository fieldRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public RelationshipRepository relationshipRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public EnumFieldRepository enumFieldRepository;

    @Override
    public List<it.anggen.model.entity.Tab> findById(Long tabId) {
        return tabRepository.findByTabId(tabId);
    }

    @Override
    public List<it.anggen.model.entity.Tab> find(TabSearchBean tab) {
        return tabRepository.findByTabIdAndNameAndEntityAndFieldAndRelationshipAndEnumField(tab.getTabId(),tab.getName(),tab.getEntity(),tab.getFieldList()==null? null :tab.getFieldList().get(0),tab.getRelationshipList()==null? null :tab.getRelationshipList().get(0),tab.getEnumFieldList()==null? null :tab.getEnumFieldList().get(0));
    }

    @Override
    public void deleteById(Long tabId) {
        tabRepository.delete(tabId);
        return;
    }

    @Override
    public it.anggen.model.entity.Tab insert(it.anggen.model.entity.Tab tab) {
        return tabRepository.save(tab);
    }

    @Override
    @Transactional
    public it.anggen.model.entity.Tab update(it.anggen.model.entity.Tab tab) {
        if (tab.getFieldList()!=null)
        for (it.anggen.model.field.Field field: tab.getFieldList())
        {
        field.setTab(tab);
        }
        if (tab.getRelationshipList()!=null)
        for (it.anggen.model.relationship.Relationship relationship: tab.getRelationshipList())
        {
        relationship.setTab(tab);
        }
        if (tab.getEnumFieldList()!=null)
        for (it.anggen.model.field.EnumField enumField: tab.getEnumFieldList())
        {
        enumField.setTab(tab);
        }
        it.anggen.model.entity.Tab returnedTab=tabRepository.save(tab);
        if (tab.getEntity()!=null)
        {
        List<it.anggen.model.entity.Tab> tabList = tabRepository.findByEntity( tab.getEntity());
        if (!tabList.contains(returnedTab))
        tabList.add(returnedTab);
        returnedTab.getEntity().setTabList(tabList);
        }
         return returnedTab;
    }

}
