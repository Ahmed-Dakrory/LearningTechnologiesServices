/**
 * 
 */
package main.com.zc.services.domain.elections.model;

import java.util.List;

/**
 * @author omnya
 *
 */
public interface IElectionCandidateRepository {

	public List<ElectionCandidate> getAll();
	public List<ElectionCandidate> getAllByType(int id);
	public ElectionCandidate getByID(int id);
}
