package java.greedao3.dao.test;

import greedao3.dao.anntation.Query;

import java.util.List;

public interface IEmailDAO {

	@Query("select * from REmailDTO a limit :=limit")
	List<REmailDTO> getTopBy(Integer limit);
}
