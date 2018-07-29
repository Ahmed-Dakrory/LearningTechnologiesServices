/**
 * 
 */
package main.com.zc.services.applicationService.forms.overloadRequest.services;

import java.util.List;

import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;

/**
 * @author omnya
 *
 */
public interface IReportsServiceOverload {
	public List<OverloadRequestDTO> getOldSummer(Integer year);
	public List<OverloadRequestDTO> getOldSpring(Integer year);
	public List<OverloadRequestDTO> getOldFall(Integer year);
	public void generateExcelByList(List<OverloadRequestDTO> lst);
}
