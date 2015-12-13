select * from meta.project;

truncate table meta.project cascade;
truncate table sso.role cascade;
truncate table sso.user cascade;

select * from meta.project
select * from meta.entity_group where project_id_project=710
select * from meta.entity where entity_group_id_entity_group=720
select * from meta.field where entity_id_entity=70
select * from meta.tab where tab_id=731

select * from meta.relationship 
update meta.entity set descendant_max_level=100

select * from sso.restriction_entity 