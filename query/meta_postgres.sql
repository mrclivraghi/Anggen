-- list table
select * from pg_class where relname !~ '^(pg_|sql_)' and relkind='r';

-- list table fields
select a.* from pg_class c 
inner join pg_attribute a on c.oid=a.attrelid
inner join pg_type t on t.oid=a.atttypid
where c.relname='risorsa' and a.attnum>0;

-- list coplumn attribute
select a.attnum as ordinal_position,
a.attname AS column_name,
t.typname AS data_type,a.attlen AS character_maximum_length,
a.atttypmod AS modifier,
a.attnotnull AS notnull,
a.atthasdef AS hasdefault
from pg_class c 
inner join pg_attribute a on c.oid=a.attrelid
inner join pg_type t on t.oid=a.atttypid
where c.relname='risorsa' and a.attnum>0;