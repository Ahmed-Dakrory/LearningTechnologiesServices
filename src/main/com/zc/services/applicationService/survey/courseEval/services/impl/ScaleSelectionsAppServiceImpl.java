/**
 * 
 */
package main.com.zc.services.applicationService.survey.courseEval.services.impl;

import java.util.ArrayList;
import java.util.List;
import main.com.zc.services.applicationService.survey.courseEval.services.IScaleSelectionsAppService;
import main.com.zc.services.domain.courseEval.model.IScaleSelectionsRep;
import main.com.zc.services.domain.courseEval.model.IScaleTypeRep;
import main.com.zc.services.domain.courseEval.model.ScaleSelections;
import main.com.zc.services.domain.courseEval.model.ScaleType;
import main.com.zc.services.domain.shared.enumurations.ScaleSelectionTypeEnum;
import main.com.zc.services.presentation.survey.courseEval.dto.ScaleSelectionsDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.ScaleTypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Omnya Alaa
 *
 */
@Service
public class ScaleSelectionsAppServiceImpl implements IScaleSelectionsAppService {

	@Autowired
	IScaleSelectionsRep rep;
	@Autowired
	IScaleTypeRep scaleTypeRep;
	@Override
	public List<ScaleSelectionsDTO> getAll() {
		List<ScaleSelectionsDTO> dtos = new ArrayList<ScaleSelectionsDTO>();

		try {
			List<ScaleSelections> scales = new ArrayList<ScaleSelections>();
			scales = rep.getAll();
			for (int i = 0; i < scales.size(); i++) {
				ScaleSelectionsDTO dto = new ScaleSelectionsDTO();
				dto.setId(scales.get(i).getId());
				dto.setName(scales.get(i).getName());
				ScaleTypeDTO scale = new ScaleTypeDTO();
				scale.setId(scales.get(i).getScaleType().getId());
				scale.setName(scales.get(i).getScaleType().getName());
				dto.setScaleType(scale);
				dto.setType(scales.get(i).getType());
				dtos.add(dto);
			}
			return dtos;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public ScaleSelectionsDTO getById(Integer id) {
		try {
			ScaleSelections selection = new ScaleSelections();
			selection = rep.getById(id);
			
				ScaleSelectionsDTO dto = new ScaleSelectionsDTO();
				dto.setId(selection.getId());
				dto.setName(selection.getName());
				ScaleTypeDTO scale = new ScaleTypeDTO();
				scale.setId(selection.getScaleType().getId());
				scale.setName(selection.getScaleType().getName());
				dto.setScaleType(scale);
				dto.setType(selection.getType());
				return dto;
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ScaleSelectionsDTO> getByScaleTypeId(Integer id) {
		List<ScaleSelectionsDTO> dtos = new ArrayList<ScaleSelectionsDTO>();

		try {
			List<ScaleSelections> scales = new ArrayList<ScaleSelections>();
			scales = rep.getByScaleTypeId(id);
			for (int i = 0; i < scales.size(); i++) {
				ScaleSelectionsDTO dto = new ScaleSelectionsDTO();
				dto.setId(scales.get(i).getId());
				dto.setName(scales.get(i).getName());
				ScaleTypeDTO scale = new ScaleTypeDTO();
				scale.setId(scales.get(i).getScaleType().getId());
				scale.setName(scales.get(i).getScaleType().getName());
				dto.setScaleType(scale);
				dto.setType(scales.get(i).getType());
				dtos.add(dto);
			}
			return dtos;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ScaleSelectionsDTO> getByScaleTypeIDAndSelType(Integer id,
			Integer type) {
		List<ScaleSelectionsDTO> dtos = new ArrayList<ScaleSelectionsDTO>();

		try {
			List<ScaleSelections> scales = new ArrayList<ScaleSelections>();
			scales = rep.getByScaleTypeIDAndSelType(id,type);
			for (int i = 0; i < scales.size(); i++) {
				ScaleSelectionsDTO dto = new ScaleSelectionsDTO();
				dto.setId(scales.get(i).getId());
				dto.setName(scales.get(i).getName());
				ScaleTypeDTO scale = new ScaleTypeDTO();
				scale.setId(scales.get(i).getScaleType().getId());
				scale.setName(scales.get(i).getScaleType().getName());
				dto.setScaleType(scale);
				dto.setType(scales.get(i).getType());
				dtos.add(dto);
			}
			return dtos;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public ScaleSelectionsDTO add(ScaleSelectionsDTO form) {
		try {
            ScaleSelections selection = new ScaleSelections();
			selection.setName(selection.getName());
			ScaleType scale = new ScaleType();
			scale=scaleTypeRep.getById(form.getScaleType().getId());
			selection.setScaleType(scale);
			selection.setType(selection.getType());
			selection=rep.add(selection);
			
			ScaleSelectionsDTO addedDTO = new ScaleSelectionsDTO();
			addedDTO.setId(selection.getId());
			addedDTO.setName(selection.getName());
			ScaleTypeDTO scaleDTO = new ScaleTypeDTO();
			scaleDTO.setId(selection.getScaleType().getId());
			scaleDTO.setName(selection.getScaleType().getName());
			addedDTO.setScaleType(scaleDTO);
			addedDTO.setType(selection.getType());
			
			return addedDTO;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public ScaleSelectionsDTO update(ScaleSelectionsDTO form) {
		try {
            ScaleSelections selection = new ScaleSelections();
			selection.setName(selection.getName());
			selection.setId(form.getId());
			ScaleType scale = new ScaleType();
			scale=scaleTypeRep.getById(form.getScaleType().getId());
			selection.setScaleType(scale);
			selection.setType(selection.getType());
			selection=rep.update(selection);
			
			ScaleSelectionsDTO addedDTO = new ScaleSelectionsDTO();
			addedDTO.setId(selection.getId());
			addedDTO.setName(selection.getName());
			ScaleTypeDTO scaleDTO = new ScaleTypeDTO();
			scaleDTO.setId(selection.getScaleType().getId());
			scaleDTO.setName(selection.getScaleType().getName());
			addedDTO.setScaleType(scaleDTO);
			addedDTO.setType(selection.getType());
			
			return addedDTO;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean delete(ScaleSelectionsDTO form) {
		try {

			ScaleSelections scale = new ScaleSelections();
			scale=rep.getById(form.getId());
			
			return rep.delete(scale);
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public List<ScaleSelectionsDTO> getByScaleTypeStrength(Integer id) {
		List<ScaleSelectionsDTO> dtos = new ArrayList<ScaleSelectionsDTO>();

		try {
			List<ScaleSelections> scales = new ArrayList<ScaleSelections>();
			scales = rep.getByScaleTypeIDAndSelType(id,ScaleSelectionTypeEnum.STRENGTH.getID());
			for (int i = 0; i < scales.size(); i++) {
				ScaleSelectionsDTO dto = new ScaleSelectionsDTO();
				dto.setId(scales.get(i).getId());
				dto.setName(scales.get(i).getName());
				ScaleTypeDTO scale = new ScaleTypeDTO();
				scale.setId(scales.get(i).getScaleType().getId());
				scale.setName(scales.get(i).getScaleType().getName());
				dto.setScaleType(scale);
				dto.setType(scales.get(i).getType());
				dtos.add(dto);
			}
			return dtos;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ScaleSelectionsDTO> getByScaleTypeConcern(Integer id) {
		List<ScaleSelectionsDTO> dtos = new ArrayList<ScaleSelectionsDTO>();

		try {
			List<ScaleSelections> scales = new ArrayList<ScaleSelections>();
			scales = rep.getByScaleTypeIDAndSelType(id,ScaleSelectionTypeEnum.CONCERN.getID());
			for (int i = 0; i < scales.size(); i++) {
				ScaleSelectionsDTO dto = new ScaleSelectionsDTO();
				dto.setId(scales.get(i).getId());
				dto.setName(scales.get(i).getName());
				ScaleTypeDTO scale = new ScaleTypeDTO();
				scale.setId(scales.get(i).getScaleType().getId());
				scale.setName(scales.get(i).getScaleType().getName());
				dto.setScaleType(scale);
				dto.setType(scales.get(i).getType());
				dtos.add(dto);
			}
			return dtos;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ScaleSelectionsDTO> getByScaleTypeMain(Integer id) {
		List<ScaleSelectionsDTO> dtos = new ArrayList<ScaleSelectionsDTO>();

		try {
			List<ScaleSelections> scales = new ArrayList<ScaleSelections>();
			scales = rep.getByScaleTypeIDAndSelType(id,ScaleSelectionTypeEnum.MAIN.getID());
			for (int i = 0; i < scales.size(); i++) {
				ScaleSelectionsDTO dto = new ScaleSelectionsDTO();
				dto.setId(scales.get(i).getId());
				dto.setName(scales.get(i).getName());
				ScaleTypeDTO scale = new ScaleTypeDTO();
				scale.setId(scales.get(i).getScaleType().getId());
				scale.setName(scales.get(i).getScaleType().getName());
				dto.setScaleType(scale);
				dto.setType(scales.get(i).getType());
				dtos.add(dto);
			}
			return dtos;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
