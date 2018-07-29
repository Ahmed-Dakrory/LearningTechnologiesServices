/**
 * 
 */
package main.com.zc.services.applicationService.shared.service;

import main.com.zc.services.domain.shared.enumurations.SemesterEnum;

/**
 * @author omnya
 *
 */
public interface IGetSemesterFromDateService {

	public SemesterEnum getSemester(Integer month, Integer day);
}
