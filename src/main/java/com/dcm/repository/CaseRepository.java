package com.dcm.repository;



import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dcm.modal.Case;

public interface CaseRepository extends CrudRepository<Case, Integer>{
    
    @Query(value = "select title from cases;", nativeQuery = true)
    public List<String> findTitle();

	@Query(value = "select caseno from cases;", nativeQuery = true)
	public List<String> findCaseno();

	Case findByCaseno(String caseno);

	public Iterable<Case> findByNexthearing(String d);

	@Query(value = "select * from cases;", nativeQuery = true)
	public List<Case> countBycategory();
	
}
