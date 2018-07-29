/**
 * 
 */
package main.com.zc.services.presentation.forms.changeOfConcentration.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.changeOfConcentration.dto.ChangeConcentrationDTO;

/**
 * @author omnya
 *
 */
public interface IReportsFacade {
	public void generateExcelByList(List<ChangeConcentrationDTO> lst);

}
