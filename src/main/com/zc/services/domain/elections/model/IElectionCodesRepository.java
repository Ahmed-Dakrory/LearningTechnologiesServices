/**
 * 
 */
package main.com.zc.services.domain.elections.model;

import java.util.List;

/**
 * @author omnya
 *
 */
public interface IElectionCodesRepository {

	public List<ElectionCodes> getAll();
	public ElectionCodes getById(int id);
	public ElectionCodes getByFileNo(String fileNo);
	public ElectionCodes addCode(ElectionCodes code);
	public ElectionCodes update(ElectionCodes code);
	public ElectionCodes getByElectionCode(String code);
}
