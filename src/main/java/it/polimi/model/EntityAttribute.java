package it.polimi.model;

import it.polimi.model.field.Annotation;
import it.polimi.reflection.EntityAttributeManager;

import java.util.List;

public class EntityAttribute {

	
	public void setName(String name)
	{
		if (EntityAttributeManager.getInstance(this).isField())
			EntityAttributeManager.getInstance(this).asField().setName(name);
		else
		{
			if (EntityAttributeManager.getInstance(this).isRelationship())
				EntityAttributeManager.getInstance(this).asRelationship().setName(name);
			else
				EntityAttributeManager.getInstance(this).asEnumField().setName(name);
		}
	}
	public String getName()
	{
		if (EntityAttributeManager.getInstance(this).isField())
			return EntityAttributeManager.getInstance(this).asField().getName();
		if (EntityAttributeManager.getInstance(this).isRelationship())
			EntityAttributeManager.getInstance(this).asRelationship().getEntityTarget().getName();
		return EntityAttributeManager.getInstance(this).asEnumField().getName();
	}
	
	
	
	public List<Annotation> getAnnotationList() {
		if (EntityAttributeManager.getInstance(this).isEnumField())
			return EntityAttributeManager.getInstance(this).getAnnotationList();
		if (EntityAttributeManager.getInstance(this).isRelationship())
			return EntityAttributeManager.getInstance(this).asRelationship().getAnnotationList();
		return EntityAttributeManager.getInstance(this).asField().getAnnotationList();
	}

}
