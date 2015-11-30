
package it.generated.anggen.service.entity;

import java.util.List;
import it.generated.anggen.repository.entity.TabRepository;
import it.generated.anggen.repository.field.EnumFieldRepository;
import it.generated.anggen.repository.field.FieldRepository;
import it.generated.anggen.repository.relationship.RelationshipRepository;
import it.generated.anggen.searchbean.entity.TabSearchBean;
import it.generated.anggen.service.entity.TabService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TabServiceImpl
    implements TabService
{

    @org.springframework.beans.factory.annotation.Autowired
    public TabRepository tabRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public EnumFieldRepository enumFieldRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public RelationshipRepository relationshipRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public FieldRepository fieldRepository;

    @Override
    public List<it.generated.anggen.model.entity.Tab> findById(Long tabId) {
        return tabRepository.findByTabId(tabId);
    }

    @Override
    public List<it.generated.anggen.model.entity.Tab> find(TabSearchBean tab) {
        return tabRepository.findByNameAndTabIdAndEnumFieldAndRelationshipAndFieldAndEntity(tab.getName(),tab.getTabId(),tab.getEnumFieldList()==null? null :tab.getEnumFieldList().get(0),tab.getRelationshipList()==null? null :tab.getRelationshipList().get(0),tab.getFieldList()==null? null :tab.getFieldList().get(0),tab.getEntity());
    }

    @Override
    public void deleteById(Long tabId) {
        tabRepository.delete(tabId);
        return;
    }

    @Override
    public it.generated.anggen.model.entity.Tab insert(it.generated.anggen.model.entity.Tab tab) {
        return tabRepository.save(tab);
    }

    @Override
    @Transactional
    public it.generated.anggen.model.entity.Tab update(it.generated.anggen.model.entity.Tab tab) {
        if (tab.getEnumFieldList()!=null)
        for (it.generated.anggen.model.field.EnumField enumField: tab.getEnumFieldList())
        {
        enumField.setTab(tab);
        }
        if (tab.getRelationshipList()!=null)
        for (it.generated.anggen.model.relationship.Relationship relationship: tab.getRelationshipList())
        {
        relationship.setTab(tab);
        }
        if (tab.getFieldList()!=null)
        for (it.generated.anggen.model.field.Field field: tab.getFieldList())
        {
        field.setTab(tab);
        }
        it.generated.anggen.model.entity.Tab returnedTab=tabRepository.save(tab);
        if (tab.getEntity()!=null)
        {
        List<it.generated.anggen.model.entity.Tab> tabList = tabRepository.findByEntity( tab.getEntity());
        if (!tabList.contains(returnedTab))
        tabList.add(returnedTab);
        returnedTab.getEntity().setTabList(tabList);
        }
         return returnedTab;
    }

}
