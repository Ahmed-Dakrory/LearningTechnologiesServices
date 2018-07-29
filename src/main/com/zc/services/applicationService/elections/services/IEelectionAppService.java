/**
 * 
 */
package main.com.zc.services.applicationService.elections.services;

import java.util.List;

import main.com.zc.services.presentation.elections.dto.ElectionResultDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
public interface IEelectionAppService {

	public List<BaseDTO> getCandidateByType(int id);
	public boolean addVote(ElectionResultDTO dto);
	public boolean votedBefore(int stdeuntID);
	public int totalNoOfVotes();
	public List<ElectionResultDTO> resultsForPresident(int id);
	public List<ElectionResultDTO> resultsForVice(int id);
	public List<ElectionResultDTO> resultsForAct(int id);
	public List<ElectionResultDTO> resultsForService(int id);
	public List<ElectionResultDTO> resultsForAcad(int id);
	public List<ElectionResultDTO> resultsForAcadPresident(int id);
	public List<ElectionResultDTO> resultsForServicePresident(int id);
	public List<ElectionResultDTO> resultsForActPresident(int id);
	public void generateElectionCodes();
	public void sendEmailsWithCodes();
	public boolean matchFileNoWithElcCode(int fileNo, String code);
	public List<BaseDTO> getAllActivtiesResults();
	public List<BaseDTO> getAllServicesResults();
	public List<BaseDTO> getAllAcademicResults();
	public BaseDTO getCandByID(int id);
	
}
