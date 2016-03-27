package it.anggen.utils;

import it.anggen.reflection.EntityAttributeManager;
import it.anggen.model.field.Annotation;

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
			return EntityAttributeManager.getInstance(this).asRelationship().getEntityTarget().getName();
		return EntityAttributeManager.getInstance(this).asEnumField().getName();
	}
	
	
	
	public List<Annotation> getAnnotationList() {
		if (EntityAttributeManager.getInstance(this).isEnumField())
			return EntityAttributeManager.getInstance(this).getAnnotationList();
		if (EntityAttributeManager.getInstance(this).isRelationship())
			return EntityAttributeManager.getInstance(this).asRelationship().getAnnotationList();
		return EntityAttributeManager.getInstance(this).asField().getAnnotationList();
	}
	
	public Integer getPriority()
	{
		if (EntityAttributeManager.getInstance(this).isField())
			return EntityAttributeManager.getInstance(this).asField().getPriority();
		if (EntityAttributeManager.getInstance(this).isRelationship())
			return EntityAttributeManager.getInstance(this).asRelationship().getPriority();
		return EntityAttributeManager.getInstance(this).asEnumField().getPriority();
	}
	
	public void setPriority(Integer priority)
	{
		if (EntityAttributeManager.getInstance(this).isField())
			 EntityAttributeManager.getInstance(this).asField().setPriority(priority);
		if (EntityAttributeManager.getInstance(this).isRelationship())
			EntityAttributeManager.getInstance(this).asRelationship().setPriority(priority);
		 EntityAttributeManager.getInstance(this).asEnumField().setPriority(priority);
	}

}
