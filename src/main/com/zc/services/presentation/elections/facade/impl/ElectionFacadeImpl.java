/**
 * 
 */
package main.com.zc.services.presentation.elections.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.elections.services.IEelectionAppService;
import main.com.zc.services.applicationService.elections.services.ISendEmailByVotingAppService;
import main.com.zc.services.applicationService.shared.service.IGetStudentByFileNoAppService;
import main.com.zc.services.presentation.elections.dto.ElectionResultDTO;
import main.com.zc.services.presentation.elections.facade.IElectionFacade;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
@Service("electionFacadeImpl")
public class ElectionFacadeImpl implements IElectionFacade {

	@Autowired
	IEelectionAppService electionAppService;
	@Autowired
	IGetStudentByFileNoAppService getStudentAppService;
	@Autowired
	ISendEmailByVotingAppService sendVotingEmailAppServ;
	@Override
	public List<BaseDTO> getCandidateByID(int id) {
		
		return electionAppService.getCandidateByType(id);
	}

	@Override
	public boolean addVote(ElectionResultDTO dto) {
	
		return electionAppService.addVote(dto);
	}

	@Override
	public boolean votedBefore(int studentID) {
	
		return electionAppService.votedBefore(studentID);
		
	}

	@Override
	public int totalNoOfVotes() {
		
		return electionAppService.totalNoOfVotes();
	}

	@Override
	public boolean isRightFileNo(int fileNo) {
		if(getStudentAppService.getStudentDataByFileNo(fileNo)==null)
			
		return false;
		else 
			return true;
	}

	@Override
	public boolean isRightCodeWithFileNo(int fileNo,String code) {
		return electionAppService.matchFileNoWithElcCode(fileNo, code);
	}

	
	@Override
	public List<ElectionResultDTO> resultsForPresident(int id) {
		
		return electionAppService.resultsForPresident(id);
	}

	@Override
	public List<ElectionResultDTO> resultsForVice(int id) {
		return electionAppService.resultsForVice(id);
	}

	@Override
	public List<ElectionResultDTO> resultsForAct(int id) {
		return electionAppService.resultsForAct(id);
	}

	@Override
	public List<ElectionResultDTO> resultsForService(int id) {
		return electionAppService.resultsForService(id);
	}

	@Override
	public List<ElectionResultDTO> resultsForAcad(int id) {
		return electionAppService.resultsForAcad(id);
	}

	@Override
	public void generateElectionCodes() {
		 electionAppService.generateElectionCodes();
		
	}

	@Override
	public void sendEmailsWithCodes() {
		 electionAppService.sendEmailsWithCodes();
		
	}

	@Override
	public List<BaseDTO> getAllActivtiesResults() {
		
		return  electionAppService.getAllActivtiesResults();
	}

	@Override
	public List<BaseDTO> getAllServicesResults() {
		
        return  electionAppService.getAllServicesResults();
	}

	@Override
	public List<BaseDTO> getAllAcademicResults() {
		  return  electionAppService.getAllAcademicResults();
	}

	@Override
	public BaseDTO getCandByID(int id) {
		  return  electionAppService.getCandByID(id);
	}

	@Override
	public boolean sendEmailByVoting(ElectionResultDTO vote) {
		
		return sendVotingEmailAppServ.sendEmailByVoting(vote);
	}

}
