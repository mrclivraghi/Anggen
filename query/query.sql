select * from meta.project;

truncate table meta.project cascade;
truncate table sso.role cascade;
truncate table sso.user cascade;
truncate table log.log_entry cascade;

select * from meta.project
select * from meta.entity_group where project_id_project=710
select * from meta.entity where entity_group_id_entity_group is null
select * from meta.field where entity_id_entity=121
select * from meta.tab where tab_id=731
select * from meta.enum_entity 
select * from meta.enum_field 

select * from log.log_entry order by log_date desc


select * from meta.enum_value order by enum_entity_id_enum_entity
select * from meta.relationship 
select * from meta.field where entity_id_entity=308
select * from meta.annotation where field_id_field=8090
update meta.entity set descendant_max_level=100

select * from sso.restriction_entity 

select * from sso.restriction_entity 
select * from sso.restriction_entity_group 
select * from sso.user_role 
select * from sso.role

update meta.entity set descendant_max_level=3 where entity_id=3


delete from meta.entity where entity_id=121 
delete from meta.relationship  where entity_id_entity=121 
delete from meta.tab where entity_id_entity=121 
delete from sso.restriction_entity  where entity_id_entity=121 

update meta.field set name='orderHeadId' where field_id=10525

10152where name like '%media%' order by entity_id_entity
select * from meta.entity where name like '%orderHead%'
select * from meta.field where name='giftCode' and entity_id_entity=48
select * from meta.annotation where field_id_field=4390
select * from meta.tab where entity_id_entity=128
insert into meta.field(field_id,field_type,name,priority,entity_id_entity,tab_id_tab)
values (346,1,'productInfoPropertyId',1,128,4405)

insert into meta.annotation(annotation_id,annotation_type,field_id_field) values (347,0,346)

update meta.field set name='code' where field_id=10105

update meta.entity set name='order' where name='orderHead'



select relationsh0_.relationship_id as relation1_10_, relationsh0_.entity_id_entity as entity_i5_10_, 
relationsh0_.entity_id_entity_target as entity_i6_10_, relationsh0_.name as name2_10_, relationsh0_.priority as priority3_10_,
 relationsh0_.relationship_type as relation4_10_, 
relationsh0_.tab_id_tab as tab_id_t7_10_ from meta.relationship relationsh0_ where relationsh0_.relationship_id=10152