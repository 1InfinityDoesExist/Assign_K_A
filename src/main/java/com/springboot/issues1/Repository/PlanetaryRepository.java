package com.springboot.issues1.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springboot.issues1.Beans.Planetary;

@Repository
public interface PlanetaryRepository extends JpaRepository<Planetary, Long>, CrudRepository<Planetary, Long> {

	@Query("Select Planetary from #{#entityName} Planetary where id=?1")
	public Planetary getPlanetary(Long id);

	@Query("Select Planetary from #{#entityName} Planetary")
	public List<Planetary> getAllPlanetary();

	@Modifying
	@Transactional
	@Query("Delete  #{#entityName} Planetary where id= ?1")
	public void deletePlanetaryById(Long id);

}
