/**
 * 
 */
package main.com.zc.services.presentation.elections.facade;

import java.util.List;

import main.com.zc.services.presentation.elections.dto.ElectionResultDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
public interface IElectionFacade {

	public List<BaseDTO> getCandidateByID(int id);
	public boolean addVote(ElectionResultDTO dto);
	public boolean votedBefore(int studentID);
	public int totalNoOfVotes();
	public boolean isRightFileNo(int fileNo);
	public boolean isRightCodeWithFileNo(int fileNo,String code) ;
	public List<ElectionResultDTO> resultsForPresident(int id);
	public List<ElectionResultDTO> resultsForVice(int id);
	public List<ElectionResultDTO> resultsForAct(int id);
	public List<ElectionResultDTO> resultsForService(int id);
	public List<ElectionResultDTO> resultsForAcad(int id);
	public void generateElectionCodes();
	public void sendEmailsWithCodes();
	public List<BaseDTO> getAllActivtiesResults();
	public List<BaseDTO> getAllServicesResults();
	public List<BaseDTO> getAllAcademicResults();
	public BaseDTO getCandByID(int id);
	public boolean sendEmailByVoting(ElectionResultDTO vote);
}
