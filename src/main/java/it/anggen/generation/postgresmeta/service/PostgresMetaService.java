package it.anggen.generation.postgresmeta.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.CaseFormat;

import it.anggen.generation.EntityGenerator;
import it.anggen.generation.postgresmeta.model.MetaConstraint;
import it.anggen.generation.postgresmeta.model.MetaField;
import it.anggen.generation.postgresmeta.model.MetaTable;
import it.anggen.generation.postgresmeta.repository.MetaConstraintRepository;
import it.anggen.generation.postgresmeta.repository.MetaFieldRepository;
import it.anggen.generation.postgresmeta.repository.MetaTableRepository;
import it.anggen.model.AnnotationType;
import it.anggen.model.FieldType;
import it.anggen.model.GenerationType;
import it.anggen.model.RelationshipType;
import it.anggen.model.entity.Entity;
import it.anggen.model.entity.EntityGroup;
import it.anggen.model.entity.Project;
import it.anggen.model.field.Annotation;
import it.anggen.model.field.Field;
import it.anggen.model.relationship.Relationship;
import it.anggen.repository.entity.EntityGroupRepository;
import it.anggen.repository.entity.EntityRepository;
import it.anggen.repository.entity.ProjectRepository;
import it.anggen.repository.field.AnnotationAttributeRepository;
import it.anggen.repository.field.AnnotationRepository;
import it.anggen.repository.field.FieldRepository;
import it.anggen.repository.relationship.RelationshipRepository;
import it.anggen.utils.OracleNamingStrategy;

@Service
public class PostgresMetaService {
	
	@Autowired
	private MetaConstraintRepository metaConstraintRepository;
	
	@Autowired
	private MetaFieldRepository metaFieldRepository;
	
	@Autowired
	private MetaTableRepository metaTableRepository;
	
	
	/*  angGen repository */
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private EntityGroupRepository entityGroupRepository;
	
	@Autowired
	private EntityRepository entityRepository;
	
	@Autowired
	private FieldRepository fieldRepository;
	
	@Autowired
	private RelationshipRepository relationshipRepository;
	
	@Autowired
	private AnnotationRepository annotationRepository;
	
	@Autowired
	private AnnotationAttributeRepository annotationAttributeRepository;
	
	
	@Autowired
	private EntityGenerator entityGenerator;
	
	
	public void generateFromSchema(String schemaName)
	{
		Project project = new Project();
		project.setName("Project-"+schemaName+new Date());
		project.setGenerationType(GenerationType.HIDE_IGNORE);
		projectRepository.save(project);
		
		EntityGroup entityGroup = new EntityGroup();
		entityGroup.setName(schemaName);
		entityGroup.setProject(project);
		entityGroupRepository.save(entityGroup);
		
		
		List<MetaTable> metaTableList = metaTableRepository.findByTableSchema(schemaName);
		Map<String, Entity> entityMap = new HashMap<>();
		List<Entity> entityList = new ArrayList<>();
		
		for (MetaTable metaTable : metaTableList)
		{
			Entity entity = new Entity();
			entity.setEntityGroup(entityGroup);
			entity.setName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, metaTable.getTableName()));
			entity.setDescendantMaxLevel(99);
			entityRepository.save(entity);
			entityMap.put(metaTable.getTableName(), entity);
		}
		
		
		Map<String,List<MetaConstraint>> oneToManyList= new HashMap<>();
		for (MetaTable metaTable : metaTableList)
		{
			Entity entity = entityMap.get(metaTable.getTableName());
			entityList.add(entity);
			
			
			List<MetaField> metaFieldList = metaFieldRepository.findByTableName(metaTable.getTableName(),schemaName);
			List<MetaConstraint> metaConstraintList = metaConstraintRepository.findByTableNameAndSchemaName(metaTable.getTableName(),schemaName);
			
			List<Field> fieldList= new ArrayList<>();
			List<Relationship> relationshipList = new ArrayList<>();
			
			List<String> relationshipNameList = new ArrayList<String>();
			String primaryKey= null;
			
			
			
			for (MetaConstraint metaConstraint: metaConstraintList)
			{
				if (metaConstraint.getConstraintType().equals("PRIMARY KEY"))
				{
					primaryKey=metaConstraint.getColumnName();
				} else
					if (metaConstraint.getConstraintType().equals("FOREIGN KEY"))
					{
						relationshipNameList.add(metaConstraint.getColumnName());
						//create relationship
						Relationship relationship = new Relationship();
						relationship.setEntity(entity);
						Entity targetEntity= entityMap.get(metaConstraint.getReferencesTable());
						relationship.setEntityTarget(targetEntity);
						relationship.setName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, metaConstraint.getReferencesTable()));
						relationship.setRelationshipType(RelationshipType.MANY_TO_ONE);
						relationship.setReferencedField(metaConstraint.getReferencesField());
						relationshipRepository.save(relationship);
						relationshipList.add(relationship);
						
						if (oneToManyList.get(relationship.getName())==null)
							oneToManyList.put(relationship.getName(), new ArrayList<>());
						oneToManyList.get(relationship.getName()).add(metaConstraint);
						
					}
			}
			
			
			for (MetaField metaField: metaFieldList)
			{
				if (relationshipNameList.contains(metaField.getColumnName()))
					continue; // already managed as relationship
				Field field= new Field();
				field.setName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, metaField.getColumnName()));
				field.setEntity(entity);
				field.setPriority(metaField.getOrdinalPosition());
				if (metaField.getDataType().equals("bigint"))
					field.setFieldType(FieldType.LONG);
				if (metaField.getDataType().equals("integer") || metaField.getDataType().equals("smallint"))
					field.setFieldType(FieldType.INTEGER);
				if (metaField.getDataType().equals("character varying"))
					field.setFieldType(FieldType.STRING);
				if (metaField.getDataType().equals("timestamp without time zone") 
						|| metaField.getDataType().equals("date")
						)
					field.setFieldType(FieldType.DATE);
				if (metaField.getDataType().equals("time without time zone"))
					field.setFieldType(FieldType.TIME);
				if (metaField.getDataType().equals("boolean"))
					field.setFieldType(FieldType.BOOLEAN);
				if (metaField.getDataType().equals("double precision") || metaField.getDataType().equals("numeric"))
					field.setFieldType(FieldType.DOUBLE);
				if (field.getFieldType()==null)
				{
					System.out.println("ERORE");
					
				}
				fieldRepository.save(field);
				List<Annotation> annotationList= new ArrayList<>();
				if (metaField.getColumnName().equals(primaryKey))
				{
					Annotation annotation = new Annotation();
					annotation.setAnnotationType(AnnotationType.PRIMARY_KEY);
					annotation.setField(field);
					annotationRepository.save(annotation);
					annotationList.add(annotation);
				}
				
				field.setAnnotationList(annotationList);
				fieldRepository.save(field);
				fieldList.add(field);
				

			}
			
			
			
			entity.setRelationshipList(relationshipList);
			entity.setFieldList(fieldList);
			
			entityRepository.save(entity);
			
			
		}
		
		entityGroup.setEntityList(entityList);
		
		List<EntityGroup> entityGroupList = new ArrayList<>();
		entityGroupList.add(entityGroup);
		entityGroupRepository.save(entityGroup);
		
		
		project.setEntityGroupList(entityGroupList);
		
		projectRepository.save(project);
		
		
		for (Entity entity : entityList)
		{
			
			if (oneToManyList.get(entity.getName())!=null)
				for (MetaConstraint metaConstraint : oneToManyList.get(entity.getName()))
				{
					Relationship relationship = new Relationship();
					relationship.setEntity(entity);
					Entity targetEntity= entityMap.get(metaConstraint.getTableName());
					relationship.setEntityTarget(targetEntity);
					relationship.setName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,metaConstraint.getTableName()));
					relationship.setPriority(99);
					relationship.setRelationshipType(RelationshipType.ONE_TO_MANY);
					relationshipRepository.save(relationship);
					entity.getRelationshipList().add(relationship);
					
				}
			
			
			if (entity.getName().equals("annotation_attribute"))
			{
				System.out.print("CX");
			}
			entityGenerator.init(entity);
			entityGenerator.getModelClass();
		}
	}
	
	
}
