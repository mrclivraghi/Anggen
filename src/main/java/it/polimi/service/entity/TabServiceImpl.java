
package it.polimi.service.entity;

import java.util.List;

import it.polimi.repository.entity.TabRepository;
import it.polimi.repository.field.EnumFieldRepository;
import it.polimi.repository.field.FieldRepository;
import it.polimi.repository.relationship.RelationshipRepository;
import it.polimi.searchbean.entity.TabSearchBean;
import it.polimi.service.entity.TabService;

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
    public List<it.polimi.model.entity.Tab> findById(Long tabId) {
        return tabRepository.findByTabId(tabId);
    }

    @Override
    public List<it.polimi.model.entity.Tab> find(TabSearchBean tab) {
        return tabRepository.findByTabIdAndNameAndEntityAndFieldAndRelationshipAndEnumField(tab.getTabId(),tab.getName(),tab.getEntity(),tab.getFieldList()==null? null :tab.getFieldList().get(0),tab.getRelationshipList()==null? null :tab.getRelationshipList().get(0),tab.getEnumFieldList()==null? null :tab.getEnumFieldList().get(0));
    }

    @Override
    public void deleteById(Long tabId) {
        tabRepository.delete(tabId);
        return;
    }

    @Override
    public it.polimi.model.entity.Tab insert(it.polimi.model.entity.Tab tab) {
        return tabRepository.save(tab);
    }

    @Override
    @Transactional
    public it.polimi.model.entity.Tab update(it.polimi.model.entity.Tab tab) {
        if (tab.getFieldList()!=null)
        for (it.polimi.model.field.Field field: tab.getFieldList())
        {
        field.setTab(tab);
        }
        if (tab.getRelationshipList()!=null)
        for (it.polimi.model.relationship.Relationship relationship: tab.getRelationshipList())
        {
        relationship.setTab(tab);
        }
        if (tab.getEnumFieldList()!=null)
        for (it.polimi.model.field.EnumField enumField: tab.getEnumFieldList())
        {
        enumField.setTab(tab);
        }
        it.polimi.model.entity.Tab returnedTab=tabRepository.save(tab);
        if (tab.getEntity()!=null)
        {
        List<it.polimi.model.entity.Tab> tabList = tabRepository.findByEntity( tab.getEntity());
        if (!tabList.contains(returnedTab))
        tabList.add(returnedTab);
        returnedTab.getEntity().setTabList(tabList);
        }
         return returnedTab;
    }

}
