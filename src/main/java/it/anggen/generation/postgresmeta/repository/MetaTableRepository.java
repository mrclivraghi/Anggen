package it.anggen.generation.postgresmeta.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import it.anggen.generation.postgresmeta.model.MetaTable;

@Repository
public class MetaTableRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;


	public List<MetaTable> findAll()
	{
		return jdbcTemplate.query("select t.*,pgd.description as comment from information_schema.tables t "
				+ "left join pg_class pgc on t.table_name=pgc.relname "
				+ " left join pg_description pgd on pgd.objoid = pgc.oid "
				+" where t.table_type='BASE TABLE' and t.table_schema not in ('pg_catalog','information_schema') ",new BeanPropertyRowMapper(MetaTable.class));
	}

	public List<MetaTable> findByTableSchema(String tableSchema)
	{
		return jdbcTemplate.query("select t.*,pgd.description as comment from information_schema.tables t "
				+ " left join pg_class pgc on t.table_name=pgc.relname "
				+ " left join pg_description pgd on pgd.objoid = pgc.oid "
				+" where t.table_type='BASE TABLE' and t.table_schema not in ('pg_catalog','information_schema')"
				+ " and t.table_schema= ? ",new BeanPropertyRowMapper(MetaTable.class),tableSchema);
	}
}
