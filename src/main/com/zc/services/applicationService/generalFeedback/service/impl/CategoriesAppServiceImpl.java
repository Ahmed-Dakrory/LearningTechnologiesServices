/**
 * 
 */
package main.com.zc.services.applicationService.generalFeedback.service.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.generalFeedback.service.ICategoriesAppService;
import main.com.zc.services.domain.feedback.model.FeedbackCategory;
import main.com.zc.services.domain.feedback.model.ICategoryRepository;
import main.com.zc.services.presentation.generalFeedback.dto.CategoryDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Omnya Alaa
 *
 */
@Service(value="categoriesAppServiceImpl")
public class CategoriesAppServiceImpl implements ICategoriesAppService{

	@Autowired
	ICategoryRepository rep;
	@Override
	public List<CategoryDTO> getAll() {
		// TODO Auto-generated method stub
		List<FeedbackCategory> cats=rep.getAll();
		List<CategoryDTO> dtos=new ArrayList<CategoryDTO>();
		for(int i=0;i<cats.size();i++)
		{
			
		CategoryDTO dto=new CategoryDTO(cats.get(i).getId(),cats.get(i).getCategoryName());
		dtos.add(dto);
		}
		return dtos;
	}

}
