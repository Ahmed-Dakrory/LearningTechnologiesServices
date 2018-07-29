/**
 * 
 */
package main.com.zc.services.presentation.shared.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.shared.service.IGetStudentImgAppService;
import main.com.zc.services.presentation.shared.IGetStudentImgFacade;

/**
 * @author omnya
 *
 */
@Service("IGetStudentImgFacade")
public class GetStudentImgFacadeImpl implements IGetStudentImgFacade{

	@Autowired
	IGetStudentImgAppService imgService;
	@Override
	public byte[] getStudentImg(Integer studentID) {
	
		return imgService.getStudentImg(studentID);
	}

}
