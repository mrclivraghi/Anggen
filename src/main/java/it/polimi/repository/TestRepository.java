
package it.polimi.repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import it.polimi.model.Photo;
import it.polimi.model.SeedQuery;
import it.polimi.model.Test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface TestRepository
    extends JpaRepository<Test, Long>
{

    @Query("select t from Test t where (:myDate IS NULL)")
    public List<Test> findByMyDate(  @Param("myDate")     Date myDate);
    
    public List<Test> findByTestIdOrTestIdIsNull(@Param("myId") Long testId);

}
