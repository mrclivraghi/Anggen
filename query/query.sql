select * from meta.project;

truncate table meta.project cascade;
truncate table sso.role cascade;
truncate table sso.user cascade;
truncate table log.log_entry cascade;

select * from meta.project
select * from meta.entity_group where project_id_project=710
delete from meta.entity where entity_id=121 
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

update meta.field set name='clusterTypeId' where name='clusterId'

select * from meta.entity where name like '%userData%'
select * from meta.field where name='userId'
select * from meta.annotation where field_id_field=4390
select * from meta.tab where entity_id_entity=128
insert into meta.field(field_id,field_type,name,priority,entity_id_entity,tab_id_tab)
values (346,1,'productInfoPropertyId',1,128,4405)

insert into meta.annotation(annotation_id,annotation_type,field_id_field) values (347,0,346)

update meta.field set name='userDataId' where field_id=5520

update meta.entity set name='order' where name='orderHead'