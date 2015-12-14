package it.anggen.repository.entity;

import java.util.List;

import it.anggen.model.entity.EnumEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnumEntityRepository 
		extends CrudRepository<EnumEntity, java.lang.Long>{

	
		@Query("select e from EnumEntity e")
	    public List<it.anggen.model.entity.EnumEntity> findByAll();
	
}
