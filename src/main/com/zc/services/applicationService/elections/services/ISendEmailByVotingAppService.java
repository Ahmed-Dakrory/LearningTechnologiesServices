/**
 * 
 */
package main.com.zc.services.applicationService.elections.services;

import main.com.zc.services.presentation.elections.dto.ElectionResultDTO;


/**
 * @author omnya
 *
 */
public interface ISendEmailByVotingAppService {

	public boolean sendEmailByVoting(ElectionResultDTO vote);
}
