package it.anggen.generation.postgresmeta.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import it.anggen.generation.postgresmeta.model.MetaField;
import it.anggen.generation.postgresmeta.model.MetaTable;

@Repository
public class MetaFieldRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public MetaFieldRepository(){
		
	}
	
	public List<MetaField> findByTableName(String tableName,String schemaName)
	{
		return jdbcTemplate.query("select * from "
				+" information_schema.columns "
				+" where table_name=?  and table_schema= ? ",new BeanPropertyRowMapper(MetaField.class),tableName,schemaName);
	}
}
