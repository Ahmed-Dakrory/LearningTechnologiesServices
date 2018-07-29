/**
 * 
 */
package main.com.zc.services.applicationService.survey.courseEval.services.impl;

import java.util.ArrayList;
import java.util.List;
import main.com.zc.services.applicationService.survey.courseEval.services.IScaleTypeAppService;
import main.com.zc.services.domain.courseEval.model.IScaleTypeRep;
import main.com.zc.services.domain.courseEval.model.ScaleType;
import main.com.zc.services.presentation.survey.courseEval.dto.ScaleTypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Omnya Alaa
 * 
 */
@Service
public class ScaleTypeAppServiceImpl implements IScaleTypeAppService {

	@Autowired
	IScaleTypeRep rep;

	@Override
	public List<ScaleTypeDTO> getAll() {
		List<ScaleTypeDTO> dtos = new ArrayList<ScaleTypeDTO>();

		try {
			List<ScaleType> scales = new ArrayList<ScaleType>();
			scales = rep.getAll();
			for (int i = 0; i < scales.size(); i++) {
				ScaleTypeDTO dto = new ScaleTypeDTO();
				dto.setId(scales.get(i).getId());
				dto.setName(scales.get(i).getName());
				dtos.add(dto);
			}
			return dtos;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	@Override
	public ScaleTypeDTO getById(Integer id) {
		try {

			ScaleType scale = rep.getById(id);
			ScaleTypeDTO dto = new ScaleTypeDTO();
			dto.setId(scale.getId());
			dto.setName(scale.getName());

			return dto;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public ScaleTypeDTO add(ScaleTypeDTO form) {
		try {

			ScaleType scale = new ScaleType();
			scale.setName(form.getName());
			scale = rep.add(scale);

			ScaleTypeDTO dto = new ScaleTypeDTO();
			dto.setId(scale.getId());
			dto.setName(scale.getName());

			return dto;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public ScaleTypeDTO update(ScaleTypeDTO form) {
		try {

			ScaleType scale = new ScaleType();
			scale.setName(form.getName());
			scale.setId(form.getId());
			scale = rep.update(scale);

			ScaleTypeDTO dto = new ScaleTypeDTO();
			dto.setId(scale.getId());
			dto.setName(scale.getName());

			return dto;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean delete(ScaleTypeDTO form) {
		try {

			ScaleType scale = new ScaleType();
			scale=rep.getById(form.getId());
			
			return rep.delete(scale);
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

}
