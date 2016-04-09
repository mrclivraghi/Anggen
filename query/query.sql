select * from meta.project;

truncate table meta.project cascade;
truncate table sso.role cascade;
truncate table sso.user cascade;
truncate table log.log_entry cascade;

select * from meta.project
select * from meta.entity_group where project_id_project=710
select * from meta.entity where entity_group_id_entity_group=720
select * from meta.field where entity_id_entity=70
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