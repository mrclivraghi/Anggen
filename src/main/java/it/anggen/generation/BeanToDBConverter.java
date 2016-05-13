package it.anggen.generation;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import it.anggen.reflection.EntityAttributeManager;
import it.anggen.reflection.EntityManager;
import it.anggen.reflection.EntityManagerImpl;
import it.anggen.utils.EntityAttribute;
import it.anggen.utils.ReflectionManager;
import it.anggen.utils.Utility;
import it.anggen.utils.annotation.Between;
import it.anggen.utils.annotation.Cache;
import it.anggen.utils.annotation.DescriptionField;
import it.anggen.utils.annotation.DisableViewGeneration;
import it.anggen.utils.annotation.Embedded;
import it.anggen.utils.annotation.EnableRestrictionData;
import it.anggen.utils.annotation.ExcelExport;
import it.anggen.utils.annotation.Filter;
import it.anggen.utils.annotation.GenerateFrontEnd;
import it.anggen.utils.annotation.IgnoreMenu;
import it.anggen.utils.annotation.IgnoreSearch;
import it.anggen.utils.annotation.IgnoreTableList;
import it.anggen.utils.annotation.IgnoreUpdate;
import it.anggen.utils.annotation.IncludeMenu;
import it.anggen.utils.annotation.IncludeSearch;
import it.anggen.utils.annotation.IncludeTableList;
import it.anggen.utils.annotation.IncludeUpdate;
import it.anggen.utils.annotation.MaxDescendantLevel;
import it.anggen.utils.annotation.Password;
import it.anggen.utils.annotation.Priority;
import it.anggen.utils.annotation.SecurityType;
import it.anggen.utils.annotation.Tab;
import it.anggen.model.AnnotationType;
import it.anggen.model.FieldType;
import it.anggen.model.GenerationType;
import it.anggen.model.RelationshipType;
import it.anggen.model.entity.Entity;
import it.anggen.model.entity.EntityGroup;
import it.anggen.model.entity.EnumEntity;
import it.anggen.model.entity.Project;
import it.anggen.model.field.AnnotationAttribute;
import it.anggen.model.field.EnumField;
import it.anggen.model.field.EnumValue;
import it.anggen.model.field.Field;
import it.anggen.model.relationship.Relationship;
import it.anggen.model.security.RestrictionEntity;
import it.anggen.model.security.RestrictionEntityGroup;
import it.anggen.model.security.RestrictionField;
import it.anggen.model.security.Role;
import it.anggen.model.security.User;
import it.anggen.repository.entity.EntityGroupRepository;
import it.anggen.repository.entity.EntityRepository;
import it.anggen.repository.entity.EnumEntityRepository;
import it.anggen.repository.entity.ProjectRepository;
import it.anggen.repository.entity.TabRepository;
import it.anggen.repository.field.AnnotationAttributeRepository;
import it.anggen.repository.field.AnnotationRepository;
import it.anggen.repository.field.EnumFieldRepository;
import it.anggen.repository.field.EnumValueRepository;
import it.anggen.repository.field.FieldRepository;
import it.anggen.repository.relationship.RelationshipRepository;
import it.anggen.repository.security.RestrictionEntityGroupRepository;
import it.anggen.repository.security.RestrictionEntityRepository;
import it.anggen.repository.security.RoleRepository;
import it.anggen.repository.security.UserRepository;

import org.hibernate.type.MetaType;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BeanToDBConverter {

	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	EntityGroupRepository entityGroupRepository;
	
	@Autowired
	EntityRepository entityRepository;
	
	@Autowired
	TabRepository tabRepository;
	
	@Autowired
	FieldRepository fieldRepository;
	
	@Autowired
	AnnotationRepository annotationRepository;
	
	@Autowired
	AnnotationAttributeRepository annotationAttributeRepository;
	
	@Autowired
	RelationshipRepository relationshipRepository;
	
	@Autowired
	EnumEntityRepository enumEntityRepository;
	
	@Autowired
	EnumFieldRepository enumFieldRepository;
	
	@Autowired
	EnumValueRepository enumValueRepository;
	
	@Autowired
	RestrictionEntityRepository restrictionEntityRepository;
	
	@Autowired
	RestrictionEntityGroupRepository restrictionEntityGroupRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Value("${application.admin.name}")
	private String adminName;
	
	@Value("${application.admin.password}")
	private String adminPassword;
	
	User admin;
	
	Role adminRole;
	
	@Value(value="${application.model.package}")
	private String modelPackage;
	
	@Value("${application.name}")
	private String projectName;
	
	@Value("${application.restriction.data.enable}")
	private Boolean enableRestrictionData;
	
	@Value("${application.generation_type}")
	private Integer generationType;
	
	private String restrictionDataGroupName="restrictiondata";
	
	
	private Long firstEntityId;
	
	
	public BeanToDBConverter() {
		
		
	}
	private void init(){
		
		Project project = new Project();
		project.setName(projectName);
		admin = new User();
		admin.setEnabled(true);
		admin.setUsername(adminName);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		admin.setPassword(encoder.encode(adminPassword));
		userRepository.save(admin);
		adminRole = new Role();
		if (projectName.equals("anggen"))
			adminRole.setRole("META-ADMIN");
		else
			adminRole.setRole("ADMIN");
		roleRepository.save(adminRole);
		List<Role> roleList = new ArrayList<Role>();
		roleList.add(adminRole);
		admin.setRoleList(roleList);
		userRepository.save(admin);
	}
	public void convert()
	{
		init();
		List<String> packageList= ReflectionManager.getSubPackages(modelPackage);
		Map<String,Entity> entityMap = new HashMap<String,Entity>();
		Map<String,EnumEntity> enumEntityMap = new HashMap<String,EnumEntity>();
		Set<Class<?>> mainPackageClassSet = ReflectionManager.getClassInPackage(modelPackage);
		Set<Class<?>> securityPackageClassSet= null;
		if (!projectName.equals("serverTest") && !packageList.contains("it.anggen.model.security"))
		{
			List<String> securityPackageList = ReflectionManager.getSubPackages("it.anggen.model.security");
			packageList.addAll(securityPackageList);
			securityPackageClassSet = ReflectionManager.getClassInPackage("it.anggen.model.security");
			//mainPackageClassSet.addAll(securityPackageClassSet);
			securityPackageClassSet.add(Field.class);
			securityPackageClassSet.add(EntityGroup.class);
			securityPackageClassSet.add(Entity.class);
			
		}
		
		
		//List<Entity> oldEntityList = entityRepository.findByEntityIdAndEnableRestrictionDataAndDescendantMaxLevelAndNameAndSecurityTypeAndRelationshipAndEntityGroupAndTabAndFieldAndEnumFieldAndRestrictionEntity(null, null, null, null, null, null, null, null, null, null, null);
		if (securityPackageClassSet!=null)
		for (Class securityClass: securityPackageClassSet)
		{
			initEntity(securityClass,entityMap);
		}

		List<Entity> entityList = new ArrayList<Entity>(entityMap.values());
		firstEntityId=Utility.getFirstEntityId(entityList);
		// init entities
		for (Class myClass: mainPackageClassSet)
		{
			initEntity(myClass,entityMap);
		}
		
		
		
		Project project = new Project();
		ReflectionManager temp = new ReflectionManager(Object.class);
		project.setName(temp.parseName(projectName));
		projectRepository.save(project);
		EntityGroup restrictionDataGroup = null;
		if (enableRestrictionData)
		{
			restrictionDataGroup= new EntityGroup();
			restrictionDataGroup.setName(this.restrictionDataGroupName);
			restrictionDataGroup.setProject(project);
			entityGroupRepository.save(restrictionDataGroup);
			RestrictionEntityGroup restrictionEntityGroup = new RestrictionEntityGroup();
			restrictionEntityGroup.setCanCreate(true);
			restrictionEntityGroup.setCanDelete(true);
			restrictionEntityGroup.setCanSearch(true);
			restrictionEntityGroup.setCanUpdate(true);
			restrictionEntityGroup.setEntityGroup(restrictionDataGroup);
			restrictionEntityGroup.setRole(adminRole);
			restrictionEntityGroupRepository.save(restrictionEntityGroup);
		}
		
		List<EntityGroup> entityGroupList = new ArrayList<EntityGroup>();
		List<EnumEntity> enumEntityList= new ArrayList<EnumEntity>();
		for (String myPackage: packageList)
		{
				EntityGroup entityGroup= new EntityGroup();
				entityGroup.setName(temp.parseName(myPackage));
				//entityGroup.setProject(project);
				entityGroupRepository.save(entityGroup);
				RestrictionEntityGroup restrictionEntityGroup = new RestrictionEntityGroup();
				restrictionEntityGroup.setCanCreate(true);
				restrictionEntityGroup.setCanDelete(true);
				restrictionEntityGroup.setCanSearch(true);
				restrictionEntityGroup.setCanUpdate(true);
				//restrictionEntityGroup.setEntityGroup(entityGroup);
				restrictionEntityGroup.setRole(adminRole);
				restrictionEntityGroupRepository.save(restrictionEntityGroup);
				List<RestrictionEntityGroup> restrictionEntityGroupList = new ArrayList<RestrictionEntityGroup>();
				
				restrictionEntityGroupList.add(restrictionEntityGroup);
				entityGroup.setRestrictionEntityGroupList(restrictionEntityGroupList);
				
				Set<Class<?>> packageClassSet = ReflectionManager.getClassInPackage(myPackage);
				List<Entity> packageEntityList = new ArrayList<Entity>();
				for (Class myClass: packageClassSet)
				{
					if (myClass.isEnum()) continue;
					
					ReflectionManager reflectionManager = new ReflectionManager(myClass);
					
					Entity entity = entityMap.get(reflectionManager.parseName());
					entity.setName(reflectionManager.parseName());
					//entity.setEntityGroup(entityGroup);
					entityRepository.save(entity);
					RestrictionEntity restrictionEntity = new RestrictionEntity();
					restrictionEntity.setCanCreate(true);
					restrictionEntity.setCanDelete(true);
					restrictionEntity.setCanSearch(true);
					restrictionEntity.setCanUpdate(true);
					//restrictionEntity.setEntity(entity);
					restrictionEntity.setRole(adminRole);
					restrictionEntityRepository.save(restrictionEntity);
					List<RestrictionEntity> restrictionEntitieList= new ArrayList<RestrictionEntity>();
					restrictionEntitieList.add(restrictionEntity);
					entity.setRestrictionEntityList(restrictionEntitieList);
					
					if (entity.getEnableRestrictionData() && enableRestrictionData && entity.getEntityGroup()!=null && !entity.getEntityGroup().getName().equals("security"))
					{
						Entity restrictionData = new Entity();
						String restrictionDataName="restriction"+Utility.getFirstUpper(reflectionManager.parseName());
						restrictionData.setName(restrictionDataName);
						restrictionData.setEntityId(getEntityId(restrictionData));
						restrictionData.setEntityGroup(restrictionDataGroup);
						restrictionData.setDescendantMaxLevel(0);
						entityRepository.save(restrictionData);
						/* pk */
						Field fieldPk = new Field();
						fieldPk.setEntity(restrictionData);
						fieldPk.setName(restrictionDataName+"Id");
						fieldPk.setFieldType(FieldType.LONG);
						fieldPk.setPriority(1);
						fieldRepository.save(fieldPk);
						it.anggen.model.field.Annotation annotationPk = new it.anggen.model.field.Annotation();
						annotationPk.setAnnotationType(AnnotationType.PRIMARY_KEY);
						annotationPk.setField(fieldPk);
						annotationRepository.save(annotationPk);
						Relationship role= new Relationship();
						role.setName("role");
						role.setEntity(restrictionData);
						role.setEntityTarget(entityMap.get("role"));
						role.setPriority(4);
						role.setRelationshipType(RelationshipType.MANY_TO_ONE);
						relationshipRepository.save(role);
						Relationship mainEntity= new Relationship();
						mainEntity.setName(entity.getName());
						mainEntity.setEntity(restrictionData);
						mainEntity.setEntityTarget(entityMap.get(entity.getName()));
						mainEntity.setPriority(4);
						mainEntity.setRelationshipType(RelationshipType.MANY_TO_ONE);
						relationshipRepository.save(mainEntity);
						// add restriction entity
						RestrictionEntity restrictionDataEntity = new RestrictionEntity();
						restrictionDataEntity.setCanCreate(true);
						restrictionDataEntity.setCanDelete(true);
						restrictionDataEntity.setCanSearch(true);
						restrictionDataEntity.setCanUpdate(true);
						restrictionDataEntity.setEntity(restrictionData);
						restrictionDataEntity.setRole(adminRole);
						restrictionEntityRepository.save(restrictionDataEntity);
						List<RestrictionEntity> restrictionDataEntitieList= new ArrayList<RestrictionEntity>();
						restrictionDataEntitieList.add(restrictionDataEntity);
						restrictionData.setRestrictionEntityList(restrictionDataEntitieList);
						
					}
					
					List<String> tabNameList=reflectionManager.getTabsName();
					List<it.anggen.model.entity.Tab> tabList = new ArrayList<it.anggen.model.entity.Tab>();
					
					
					List<Field> fieldList= new ArrayList<Field>();
					List<Relationship>	relationshipList = new ArrayList<Relationship>();
					List<EnumField> enumFieldList = new ArrayList<EnumField>();
					
					
					for (String tabName: tabNameList)
					{
						it.anggen.model.entity.Tab metaTab = new it.anggen.model.entity.Tab();
						metaTab.setName(tabName);
						//metaTab.setEntity(entity);
						tabRepository.save(metaTab);
						
						
						List<Field> tabFieldList = new ArrayList<Field>();
						List<EnumField> tabEnumFieldList= new ArrayList<EnumField>();
						List<Relationship> tabRelationshipList = new ArrayList<Relationship>();

						for (it.anggen.utils.Field field: reflectionManager.getFieldByTabName(tabName))
						{
							if (reflectionManager.isKnownClass(field.getFieldClass()))
							{
								Field metaField= new Field();
								if (field.getName().equals("staticEntityId"))
									continue;
								metaField.setName(field.getName());
								//metaField.setTab(metaTab);
								
								FieldType fieldType=null;
								if (field.getFieldClass()==Integer.class) 
									fieldType=FieldType.INTEGER;
								if (field.getFieldClass()==String.class) 
									fieldType=FieldType.STRING;
								if (field.getFieldClass()==Date.class || field.getFieldClass()==java.sql.Date.class || field.getFieldClass()==Timestamp.class)
									fieldType=FieldType.DATE;
								if (field.getFieldClass()==Double.class) 
									fieldType=FieldType.DOUBLE;
								if (field.getFieldClass()==BigDecimal.class || field.getFieldClass()==Long.class) 
									fieldType=FieldType.LONG;
								if (field.getFieldClass()==Boolean.class)
									fieldType=FieldType.BOOLEAN;
								if (field.getFieldClass()==Time.class)
									fieldType=FieldType.TIME;
								if (field.getFieldClass()==File.class)
									fieldType=FieldType.FILE;
								metaField.setFieldType(fieldType);
								metaField.setEntity(entity);
								if (ReflectionManager.hasId(field))
								{
									metaField.setPriority(1);
								}else
								{
									metaField.setPriority(2);
								}
								setRightPriority(field, metaField);

								//fieldRepository.save(metaField);
								//List<it.anggen.model.field.Annotation> annotationList = new ArrayList<it.anggen.model.field.Annotation>();
								convertAnnotation(field, metaField);
								//if (metaField.getPriority()==null)
								
								fieldRepository.save(metaField);
								fieldList.add(metaField);
								tabFieldList.add(metaField);
								
								continue;
							} // fine is known class
							if(field.getIsEnum())
							{
								EnumEntity enumEntity= null;
								ReflectionManager enumReflection = new ReflectionManager(field.getFieldClass());
								String enumEntityName=enumReflection.parseName();
								if (enumEntityMap.get(enumEntityName)!=null)
								{
									enumEntity=enumEntityMap.get(enumEntityName);
								} else
								{ //create enumEntity
									enumEntity= new EnumEntity();
									enumEntity.setName(enumEntityName);
									enumEntity.setProject(project);
									enumEntityRepository.save(enumEntity);
									List<String> enumValueList = field.getEnumValuesList();
									List<EnumValue> metaEnumValueList = new ArrayList<EnumValue>();
									for (int i=0; i<enumValueList.size(); i++)
									{
										EnumValue metaEnumValue= new EnumValue();
										metaEnumValue.setName(enumValueList.get(i));
										metaEnumValue.setEnumEntity(enumEntity);
										metaEnumValue.setValue(i);
										enumValueRepository.save(metaEnumValue);
										metaEnumValueList.add(metaEnumValue);
									}
									enumEntity.setEnumValueList( metaEnumValueList);
									enumEntityRepository.save(enumEntity);
									enumEntityList.add(enumEntity);
								}
								
								
								EnumField enumField = new EnumField();
								enumField.setName(field.getName());
								enumField.setEntity(entity);
								enumField.setEnumEntity(enumEntity);
								enumField.setTab(metaTab);
								enumField.setPriority(3);
								setRightPriority(field, enumField);
								
								enumFieldRepository.save(enumField);
								List<it.anggen.model.field.Annotation> annotationList = new ArrayList<it.anggen.model.field.Annotation>();
								convertAnnotation(field, enumField);
								if (enumField.getPriority()==null)
								{/*
									it.anggen.model.field.Annotation priority = new it.anggen.model.field.Annotation();
									priority.setAnnotationType(AnnotationType.PRIORITY);
									priority.setEnumField(enumField);
									annotationRepository.save(priority);
									//List<AnnotationAttribute> annotationAttributeList = new ArrayList<AnnotationAttribute>();
									AnnotationAttribute annotationAttribute = new AnnotationAttribute();
									annotationAttribute.setProperty("value");
									annotationAttribute.setValue("3");
									enumField.setPriority(3);
									annotationAttribute.setAnnotation(priority);
									annotationAttributeRepository.save(annotationAttribute);
									//annotationAttributeList.add(annotationAttribute);
									//priority.setAnnotationAttributeList(annotationAttributeList);
									//annotationRepository.save(priority);
									//enumField.getAnnotationList().add(priority);
								*/}
								enumFieldRepository.save(enumField);
								enumFieldList.add(enumField);
								tabEnumFieldList.add(enumField);
								continue;
								
								//end enum Field
							} else
							{ //relationship
								Relationship relationship = new Relationship();
								//relationship.setEntity(entity);
								//relationship.setTab(metaTab);
								relationship.setName(reflectionManager.parseName(field.getName()));
								relationshipRepository.save(relationship);
								RelationshipType relationshipType = null;
								if (ReflectionManager.hasOneToOne(field))
								{
									relationshipType=RelationshipType.ONE_TO_ONE;
								}
								if (ReflectionManager.hasOneToMany(field))
								{
									relationshipType=RelationshipType.ONE_TO_MANY;
								}
								if (ReflectionManager.hasManyToOne(field))
								{
									relationshipType=RelationshipType.MANY_TO_ONE;
								}
								if (ReflectionManager.hasManyToMany(field))
								{
									relationshipType=RelationshipType.MANY_TO_MANY;
								}
								if (ReflectionManager.hasBackManyToMany(field))
								{
									relationshipType=RelationshipType.MANY_TO_MANY_BACK;
								}
								String entityTargetName=reflectionManager.parseName(field.getCompositeClass().fullName());
								
								// heuristics if not mapped
								if (relationshipType==null)
								{
									
									if (field.getCompositeClass().fullName().contains("java.util.List") || field.getCompositeClass().fullName().contains("java.util.Set"))
										relationshipType=RelationshipType.ONE_TO_MANY;
									else
										relationshipType=RelationshipType.MANY_TO_ONE;
								}
								
								relationship.setRelationshipType(relationshipType);
								
								if (entityMap.get(entityTargetName)==null) //check s
									relationship.setEntityTarget(entityMap.get(field.getName().substring(0, field.getName().length()-1)));
								else
									relationship.setEntityTarget(entityMap.get(entityTargetName));
								if (relationship.getEntityTarget()==null)
								{
									String fullName=field.getCompositeClass().fullName();
									//String test=field.getCompositeClass();
									String parsedFullName=reflectionManager.parseName(fullName);
									System.out.println("test");
								}
								
								relationship.setPriority(4);
								setRightPriority(field, relationship);
								List<it.anggen.model.field.Annotation> annotationList = new ArrayList<it.anggen.model.field.Annotation>();
								convertAnnotation(field, relationship);
								relationshipRepository.save(relationship);
								if (relationship.getPriority()==null)
								{/*
									it.anggen.model.field.Annotation priority = new it.anggen.model.field.Annotation();
									priority.setAnnotationType(AnnotationType.PRIORITY);
									priority.setRelationship(relationship);
									annotationRepository.save(priority);
									//List<AnnotationAttribute> annotationAttributeList = new ArrayList<AnnotationAttribute>();
									AnnotationAttribute annotationAttribute = new AnnotationAttribute();
									annotationAttribute.setProperty("value");
									annotationAttribute.setValue("4"); // by default
									relationship.setPriority(4);
									annotationAttribute.setAnnotation(priority);
									annotationAttributeRepository.save(annotationAttribute);
									//annotationAttributeList.add(annotationAttribute);
									//priority.setAnnotationAttributeList(annotationAttributeList);
									//annotationRepository.save(priority);
									//relationship.getAnnotationList().add(priority);
								*/}
								relationshipList.add(relationship);
								tabRelationshipList.add(relationship);
								continue;
							}
							
							
						}
						
						metaTab.setFieldList(tabFieldList);
						metaTab.setEnumFieldList(tabEnumFieldList);
						metaTab.setRelationshipList(tabRelationshipList);
						tabRepository.save(metaTab);
						
						tabList.add(metaTab);
					} // fine ciclo entity
					entity.setTabList(tabList);
					entity.setFieldList(fieldList);
					entity.setRelationshipList(relationshipList);
					entity.setEnumFieldList(enumFieldList);
					entityRepository.save(entity);
					packageEntityList.add(entity);
				}
				
				entityGroup.setEntityList(packageEntityList);
				//entityGroup.setProject(project);
				entityGroupRepository.save(entityGroup);
				entityGroupList.add(entityGroup);
				System.out.println("finito entityGroup"+entityGroup.getName());
		}
		project.setEntityGroupList(entityGroupList);
		project.setEnumEntityList(enumEntityList);
		projectRepository.save(project);
		putAutoAnnotation(project);
		
	}
	
	private void initEntity(Class myClass, Map<String, Entity> entityMap)
	{

		ReflectionManager reflectionManager = new ReflectionManager(myClass);
		Entity entity = new Entity();
		entity.setName(reflectionManager.parseName());
		entity.setEnableRestrictionData(false);
		entity.setDisableViewGeneration(false);
		entity.setGenerateFrontEnd(false);
		entity.setIgnoreMenu(generationType==GenerationType.SHOW_INCLUDE.getValue());
		entity.setGenerationType(generationType==GenerationType.SHOW_INCLUDE.getValue() ? GenerationType.SHOW_INCLUDE:GenerationType.HIDE_IGNORE);
		entity.setSecurityType(it.anggen.model.SecurityType.ACCESS_WITH_PERMISSION);
		entity.setDescendantMaxLevel(1);
		Annotation[] annotationArray=myClass.getAnnotations();
		for (int i=0; i<annotationArray.length;i++)
		{
			if (annotationArray[i].annotationType()==SecurityType.class)
			for (Method method : annotationArray[i].annotationType().getDeclaredMethods()) {
				if (method.getName().equals("type"))
				{
					Object value= null;
					try {
						value=  method.invoke(annotationArray[i], (Object[])null);
						entity.setSecurityType((it.anggen.model.SecurityType) value);
					} catch (IllegalAccessException
							| IllegalArgumentException
							| InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			
			if (annotationArray[i].annotationType()==it.anggen.utils.annotation.GenerationType.class)
				for (Method method : annotationArray[i].annotationType().getDeclaredMethods()) {
					if (method.getName().equals("type"))
					{
						Object value= null;
						try {
							value=  method.invoke(annotationArray[i], (Object[])null);
							entity.setGenerationType((it.anggen.model.GenerationType) value);
						} catch (IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			
			
			if (annotationArray[i].annotationType()==EnableRestrictionData.class)
			{
				entity.setEnableRestrictionData(true);
			} 
			if (annotationArray[i].annotationType()==IgnoreMenu.class)
			{
				entity.setIgnoreMenu(true);
			} 
			if (annotationArray[i].annotationType()==IncludeMenu.class)
			{
				entity.setIgnoreMenu(false);
			} 
			if (annotationArray[i].annotationType()==DisableViewGeneration.class)
			{
				entity.setDisableViewGeneration(true);
			} 
			
			if (annotationArray[i].annotationType()==GenerateFrontEnd.class)
			{
				entity.setGenerateFrontEnd(true);
			} 
			
			if (annotationArray[i].annotationType()==Cache.class || annotationArray[i].annotationType()==org.hibernate.annotations.Cache.class)
			{
				entity.setCache(true);
			} 
			
			if (annotationArray[i].annotationType()==MaxDescendantLevel.class)
				for (Method method : annotationArray[i].annotationType().getDeclaredMethods()) {
					if (method.getName().equals("value"))
					{
						Object value= null;
						try {
							value=  method.invoke(annotationArray[i], (Object[])null);
							entity.setDescendantMaxLevel((Integer) value);
						} catch (IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
		}
		entity.setEntityId(getEntityId(entity));
		entityRepository.save(entity);
		//System.out.println("Ho salvato "+entity.getName()+" con id "+entity.getEntityId()+ " ma sul db ho "+savedEntity.getEntityId());
		entityMap.put(reflectionManager.parseName(), entity);
	
	}
	
	private void setRightPriority(it.anggen.utils.Field field,EntityAttribute metaEntityAttribute)
	{
		Annotation[] annotationArray = field.getAnnotationList();


		for (int i=0; i<annotationArray.length; i++)
		{



			if (annotationArray[i].annotationType()==Priority.class)
			{
				for (Method method : annotationArray[i].annotationType().getDeclaredMethods()) {
					if (method.getName().equals("value"))
					{
						Object value= null;
						try {
							value=  method.invoke(annotationArray[i], (Object[])null);
							metaEntityAttribute.setPriority((Integer) value);
							/*if (EntityAttributeManager.getInstance(metaEntityAttribute).isField())
								fieldRepository.save(EntityAttributeManager.getInstance(metaEntityAttribute).asField());
							if (EntityAttributeManager.getInstance(metaEntityAttribute).isEnumField())
								enumFieldRepository.save(EntityAttributeManager.getInstance(metaEntityAttribute).asEnumField());
							if (EntityAttributeManager.getInstance(metaEntityAttribute).isRelationship())
								relationshipRepository.save(EntityAttributeManager.getInstance(metaEntityAttribute).asRelationship());
						*/
						} catch (IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				//metaAnnotation.setAnnotationAttributeList(annotationAttributeList);
			}
		}
	}
	
	
	private void convertAnnotation(it.anggen.utils.Field field,EntityAttribute metaEntityAttribute)
	{
		Annotation[] annotationArray = field.getAnnotationList();
		
		List<it.anggen.model.field.Annotation> metaAnnotationList = new ArrayList<it.anggen.model.field.Annotation>();
		
		for (int i=0; i<annotationArray.length; i++)
		{
			
			
			
			//List<it.anggen.model.field.Annotation> metaAnnotationList = annotationRepository.findByAnnotationIdAndAnnotationTypeAndRelationshipAndFieldAndEnumFieldAndAnnotationAttribute(null, AnnotationType.PRIORITY.getValue(), EntityAttributeManager.getInstance(metaEntityAttribute).asRelationship(), EntityAttributeManager.getInstance(metaEntityAttribute).asField(), EntityAttributeManager.getInstance(metaEntityAttribute).asEnumField(), null);
			it.anggen.model.field.Annotation metaAnnotation = null;		
			//if (metaAnnotationList.size()>0)
			//	metaAnnotation=metaAnnotationList.get(0);
			//else
				metaAnnotation=	new it.anggen.model.field.Annotation();
			
			AnnotationType annotationType= null;
			if (annotationArray[i].annotationType()==Priority.class)
			{
				annotationType=AnnotationType.PRIORITY;
				List<AnnotationAttribute> annotationAttributeList = new ArrayList<AnnotationAttribute>();
				for (Method method : annotationArray[i].annotationType().getDeclaredMethods()) {
					if (method.getName().equals("value"))
					{
						Object value= null;
						try {
							value=  method.invoke(annotationArray[i], (Object[])null);
							AnnotationAttribute annotationAttribute = new AnnotationAttribute();
							annotationAttribute.setProperty(method.getName());
							annotationAttribute.setValue(value.toString());
							//annotationAttribute.setAnnotation(metaAnnotation);
							metaAnnotation.setAnnotationType(annotationType);
							/*if (EntityAttributeManager.getInstance(metaEntityAttribute).isField())
								metaAnnotation.setField(EntityAttributeManager.getInstance(metaEntityAttribute).asField());
							if (EntityAttributeManager.getInstance(metaEntityAttribute).isEnumField())
								metaAnnotation.setEnumField(EntityAttributeManager.getInstance(metaEntityAttribute).asEnumField());
							if (EntityAttributeManager.getInstance(metaEntityAttribute).isRelationship())
								metaAnnotation.setRelationship(EntityAttributeManager.getInstance(metaEntityAttribute).asRelationship());
							annotationRepository.save(metaAnnotation);
							*/
							annotationAttributeRepository.save(annotationAttribute);
							annotationAttributeList.add(annotationAttribute);
						} catch (IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				metaAnnotation.setAnnotationAttributeList(annotationAttributeList);
			}
			if (annotationArray[i].annotationType()==Id.class)
			{
				annotationType=AnnotationType.PRIMARY_KEY;
			}
			if (annotationArray[i].annotationType()==Between.class)
			{
				annotationType=AnnotationType.BETWEEN_FILTER;
			}
			if (annotationArray[i].annotationType()==DescriptionField.class)
			{
				annotationType=AnnotationType.DESCRIPTION_FIELD;
			}
			if (annotationArray[i].annotationType()==ExcelExport.class)
			{
				annotationType=AnnotationType.EXCEL_EXPORT;
			}
			if (annotationArray[i].annotationType()==Filter.class)
			{
				annotationType=AnnotationType.FILTER_FIELD;
			}
			if (annotationArray[i].annotationType()==IgnoreSearch.class)
			{
				annotationType=AnnotationType.IGNORE_SEARCH;
			}
			if (annotationArray[i].annotationType()==IgnoreTableList.class)
			{
				annotationType=AnnotationType.IGNORE_TABLE_LIST;
			}
			if (annotationArray[i].annotationType()==IgnoreUpdate.class)
			{
				annotationType=AnnotationType.IGNORE_UPDATE;
			}
			
			if (annotationArray[i].annotationType()==IncludeSearch.class)
			{
				annotationType=AnnotationType.INCLUDE_SEARCH;
			}
			if (annotationArray[i].annotationType()==IncludeTableList.class)
			{
				annotationType=AnnotationType.INCLUDE_TABLE_LIST;
			}
			if (annotationArray[i].annotationType()==IncludeUpdate.class)
			{
				annotationType=AnnotationType.INCLUDE_UPDATE;
			}
			
			
			if (annotationArray[i].annotationType()==Embedded.class)
			{
				annotationType=AnnotationType.EMBEDDED;
			}
			if (annotationArray[i].annotationType()==Size.class)
			{
				annotationType=AnnotationType.SIZE;
				metaAnnotation.setAnnotationType(annotationType);
				
				/*if (EntityAttributeManager.getInstance(metaEntityAttribute).isField())
					metaAnnotation.setField(EntityAttributeManager.getInstance(metaEntityAttribute).asField());
				if (EntityAttributeManager.getInstance(metaEntityAttribute).isEnumField())
					metaAnnotation.setEnumField(EntityAttributeManager.getInstance(metaEntityAttribute).asEnumField());
				if (EntityAttributeManager.getInstance(metaEntityAttribute).isRelationship())
					metaAnnotation.setRelationship(EntityAttributeManager.getInstance(metaEntityAttribute).asRelationship());
				*/
				annotationRepository.save(metaAnnotation);
				List<AnnotationAttribute> annotationAttributeList = new ArrayList<AnnotationAttribute>();
				for (Method method : annotationArray[i].annotationType().getDeclaredMethods()) {
					if (method.getName().equals("min") || method.getName().equals("max"))
					{
						Object value= null;
						try {
							value=  method.invoke(annotationArray[i], (Object[])null);
							AnnotationAttribute annotationAttribute = new AnnotationAttribute();
							annotationAttribute.setProperty(method.getName());
							annotationAttribute.setValue(value.toString());
							annotationAttribute.setAnnotation(metaAnnotation);
							annotationAttributeRepository.save(annotationAttribute);
							annotationAttributeList.add(annotationAttribute);
						} catch (IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				metaAnnotation.setAnnotationAttributeList(annotationAttributeList);
			}
			if (annotationArray[i].annotationType()==NotNull.class)
			{
				annotationType=AnnotationType.NOT_NULL;
			}
			if (annotationArray[i].annotationType()==NotBlank.class)
			{
				annotationType=AnnotationType.NOT_BLANK;
			}
			if (annotationArray[i].annotationType()==Password.class)
			{
				annotationType=AnnotationType.PASSWORD;
			}
			
			if (annotationType!=null && metaAnnotation.getAnnotationType()!=AnnotationType.SIZE && metaAnnotation.getAnnotationType()!=AnnotationType.PRIORITY)
			{
				metaAnnotation.setAnnotationType(annotationType);
				/*if (EntityAttributeManager.getInstance(metaEntityAttribute).isField())
					metaAnnotation.setField(EntityAttributeManager.getInstance(metaEntityAttribute).asField());
				if (EntityAttributeManager.getInstance(metaEntityAttribute).isEnumField())
					metaAnnotation.setEnumField(EntityAttributeManager.getInstance(metaEntityAttribute).asEnumField());
				if (EntityAttributeManager.getInstance(metaEntityAttribute).isRelationship())
					metaAnnotation.setRelationship(EntityAttributeManager.getInstance(metaEntityAttribute).asRelationship());
				*/
				annotationRepository.save(metaAnnotation);
				metaAnnotationList.add(metaAnnotation);
			}
		}
		metaEntityAttribute.setAnnotationList(metaAnnotationList);
	}
	
	private Boolean isAngGenSecurity(Entity entity)
	{
		if (projectName.equals("serverTest"))
			return false;
		if (entity.getName().equals("restrictionField") || 
				entity.getName().equals("restrictionEntityGroup") || 
				entity.getName().equals("restrictionEntity") || 
				entity.getName().equals("user") || 
				entity.getName().equals("role") ||
				entity.getName().equals("field") ||
				entity.getName().equals("entity") ||
				entity.getName().equals("entityGroup") )
			return true;
		return false;
	}
	
	private Long getEntityId(Entity entity)
	{
		if (!isAngGenSecurity(entity))
			{
				firstEntityId++;
				return firstEntityId;
			}
		if (entity.getName().equals("user"))
			return User.staticEntityId;
		if (entity.getName().equals("role"))
			return Role.staticEntityId;
		if (entity.getName().equals("restrictionEntity"))
			return RestrictionEntity.staticEntityId;
		if (entity.getName().equals("restrictionEntityGroup"))
			return RestrictionEntityGroup.staticEntityId;
		if (entity.getName().equals("restrictionField"))
			return RestrictionField.staticEntityId;
		if (entity.getName().equals("field"))
			return Field.staticEntityId;
		if (entity.getName().equals("entity"))
			return Entity.staticEntityId;
		if (entity.getName().equals("entityGroup"))
			return EntityGroup.staticEntityId;

		firstEntityId++;
		return firstEntityId;
	}
	
	
	public void putAutoAnnotation(Project project)
	{
		for (EntityGroup entityGroup : project.getEntityGroupList())
		{
			for (Entity entity : entityGroup.getEntityList())
				if (!entity.getIgnoreMenu())
				{
					for (Relationship relationship: entity.getRelationshipList())
						if (relationship.getEntityTarget()!=null && relationship.getEntityTarget().getIgnoreMenu())
						{
							Entity target= relationship.getEntityTarget();
							EntityManager entityManager = new EntityManagerImpl(target);
							Boolean foundAnnotation=false;
							for (EntityAttribute entityAttribute: entityManager.getAllAttribute())
							{
								if (EntityAttributeManager.getInstance(entityAttribute).getIncludeUpdate())
								{
									foundAnnotation=true;
									break;
								}
							}
							if (!foundAnnotation)
							{ // add default on field
								for (Field field: target.getFieldList())
								{
									it.anggen.model.field.Annotation annotation = new it.anggen.model.field.Annotation();
									annotation.setAnnotationType(AnnotationType.INCLUDE_UPDATE);
									annotation.setField(field);
									annotationRepository.save(annotation);
								}
								target.setGenerationType(GenerationType.SHOW_INCLUDE);
								entityRepository.save(target);
							}
						}
				}
		}
	}
	
	
	public void generateTabForRelationship(Project project)
	{
		for (EntityGroup entityGroup : project.getEntityGroupList())
		{
			for (Entity entity : entityGroup.getEntityList())
			{
				for (Relationship relationship: entity.getRelationshipList())
					if (relationship.getTab().getName().equals("Detail"))
					{
						it.anggen.model.entity.Tab tab = new it.anggen.model.entity.Tab();
						tab.setEntity(entity);
						tab.setName(Utility.getFirstUpper(relationship.getName()));
						List<Relationship> relationshipList = new ArrayList<Relationship>();
						relationshipList.add(relationship);
						tab.setRelationshipList(relationshipList);
						tabRepository.save(tab);
						
					}
			}
		}
	}
	

}
