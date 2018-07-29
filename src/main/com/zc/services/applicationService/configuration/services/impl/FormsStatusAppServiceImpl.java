/**
 * 
 */
package main.com.zc.services.applicationService.configuration.services.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.configuration.services.IFormsStatusAppService;
import main.com.zc.services.domain.petition.model.FormsStatus;
import main.com.zc.services.domain.petition.model.IFormsStatusRepository;
import main.com.zc.services.domain.shared.enumurations.SemesterEnum;
import main.com.zc.services.domain.survey.model.FormsSettings;
import main.com.zc.services.domain.survey.model.IFormsSettingsRep;
import main.com.zc.services.presentation.configuration.dto.FormsStatusDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service
public class FormsStatusAppServiceImpl implements IFormsStatusAppService{

	@Autowired
	IFormsStatusRepository rep;
	@Autowired
	IFormsSettingsRep formsSettingRep;
	@Override
	public FormsStatusDTO add(FormsStatusDTO form) {
		
		try
		{
			FormsStatus status=new FormsStatus();
			status.setFormTypeName(form.getFormTypeName());
			status.setStatus(form.getStatus());
			status=rep.add(status);
			FormsStatusDTO dto=new FormsStatusDTO();
			dto.setId(status.getId());
			dto.setFormTypeName(status.getFormTypeName());
			dto.setStatus(status.getStatus());
			return dto;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}

	@Override
	public boolean remove(FormsStatusDTO dto) {

		try
		{
			FormsStatus status=new FormsStatus();
			status=rep.getById(dto.getId());
			return rep.remove(status);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public FormsStatusDTO update(FormsStatusDTO form) {
		try
		{
			FormsStatus status=new FormsStatus();
			status=rep.getById(form.getId());
			status.setFormTypeName(form.getFormTypeName());
			status.setStatus(form.getStatus());
			status=rep.update(status);
			FormsStatusDTO dto=new FormsStatusDTO();
			dto.setId(status.getId());
			dto.setFormTypeName(status.getFormTypeName());
			dto.setStatus(status.getStatus());
			dto.setYear(status.getYear());
			try{
			BaseDTO semesterDTO=new BaseDTO();
			semesterDTO.setId(status.getSemester().getID());
			semesterDTO.setName(status.getSemester().getName());
			dto.setSemester(semesterDTO);
			}
			catch(Exception ex)
			{}
			List<FormsSettings> previousSetings=new ArrayList<FormsSettings>();
			previousSetings=formsSettingRep.getByFormId((dto.getId()));
			List<Integer> levels=new ArrayList<Integer>();
			for(int i=0;i<previousSetings.size();i++)
			{
				levels.add(previousSetings.get(i).getLevelID());
			}
			dto.setLevels(levels);
			return dto;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<FormsStatusDTO> getAll() {
		List<FormsStatusDTO> dtos=new ArrayList<FormsStatusDTO>();
		try
		{
			List<FormsStatus> objects=new ArrayList<FormsStatus>();
			objects=rep.getAll();
			for(int i=0;i<objects.size();i++)
			{
				FormsStatusDTO dto=new FormsStatusDTO();
				dto.setFormTypeName(objects.get(i).getFormTypeName());
				dto.setId(objects.get(i).getId());
				dto.setStatus(objects.get(i).getStatus());
				
				List<FormsSettings> settings=formsSettingRep.getByFormId(objects.get(i).getId());
				if(settings!=null){
					List<Integer> levels=new ArrayList<Integer>();
				for(int j=0;j<settings.size();j++)
				{
					
					levels.add(settings.get(j).getLevelID());
					dto.setLevels(levels);
				}}
				try{
				dto.setYear(objects.get(i).getYear());
	
				BaseDTO semester=new BaseDTO();
				
				semester.setId(objects.get(i).getSemester().getID());
					semester.setName(objects.get(i).getSemester().getName());
					dto.setSemester(semester);
				}
				catch(Exception ex){
					ex.toString();
				}
				dtos.add(dto);
			
				
			}
			return dtos;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}

	@Override
	public FormsStatusDTO getById(Integer id) {
		
		try
		{
			    FormsStatus status=rep.getById(id);
				FormsStatusDTO dto=new FormsStatusDTO();
				dto.setFormTypeName(status.getFormTypeName());
				dto.setId(status.getId());
				dto.setStatus(status.getStatus());
				List<FormsSettings> settings=formsSettingRep.getByFormId(id);
				if(settings!=null){
					List<Integer> levels=new ArrayList<Integer>();
				for(int j=0;j<settings.size();j++)
				{
					
					levels.add(settings.get(j).getLevelID());
					dto.setLevels(levels);
				}}
				try{
					dto.setYear(status.getYear());
					BaseDTO semesterDTO=new BaseDTO();
					semesterDTO.setId(status.getSemester().getID());
					semesterDTO.setName(status.getSemester().getName());
					dto.setSemester(semesterDTO);
				}
				catch(Exception ex){
					
				}
				
			
			return dto;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public FormsStatusDTO addLevelsToForm(FormsStatusDTO dto) {
		List<Integer> levels=new ArrayList<Integer>();
		try
		{
			List<FormsSettings> previousSetings=new ArrayList<FormsSettings>();
			previousSetings=formsSettingRep.getByFormId((dto.getId()));
			for(int i=0;i<previousSetings.size();i++)
			{
				formsSettingRep.remove(previousSetings.get(i).getId());
			}
			for(int i=0;i<dto.getLevels().size();i++)
		
		{
			
			FormsSettings setting=new FormsSettings();
			FormsStatus form=new FormsStatus();
			form.setId(dto.getId());
			setting.setFormID(form);
			setting.setLevelID(dto.getLevels().get(i));
			setting=formsSettingRep.add(setting);
			if(setting!=null)
			{
				levels.add(setting.getLevelID());
			}
		}
			dto.setLevels(levels);
			dto.setYear(dto.getYear());
			BaseDTO semDTO=new BaseDTO();
			semDTO.setId(dto.getSemester().getId());
			semDTO.setName(dto.getSemester().getName());
			return dto;
			}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Can't add Level");
			return null;
		}
	
	}

	@Override
	public FormsStatusDTO updateYearAndSemesters(FormsStatusDTO dto) {
		try
		{
			FormsStatus status=new FormsStatus();
			status=rep.getById(dto.getId());
			status.setFormTypeName(dto.getFormTypeName());
			status.setYear(dto.getYear());
			if(dto.getSemester().getId()==0)
			{
				status.setSemester(SemesterEnum.Fall);
			}
			else if(dto.getSemester().getId()==1)
			{
				status.setSemester(SemesterEnum.Spring);
			} 
			else if(dto.getSemester().getId()==2)
			{
				status.setSemester(SemesterEnum.Summer);
			} 
			
			status=rep.update(status);
			dto=new FormsStatusDTO();
			dto.setId(status.getId());
			dto.setFormTypeName(status.getFormTypeName());
			dto.setStatus(status.getStatus());
			dto.setYear(status.getYear());
			BaseDTO semesterDTO=new BaseDTO();
			semesterDTO.setId(status.getSemester().getID());
			semesterDTO.setName(status.getSemester().getName());
			dto.setSemester(semesterDTO);
			List<FormsSettings> previousSetings=new ArrayList<FormsSettings>();
			previousSetings=formsSettingRep.getByFormId((dto.getId()));
			List<Integer> levels=new ArrayList<Integer>();
			for(int i=0;i<previousSetings.size();i++)
			{
				levels.add(previousSetings.get(i).getLevelID());
			}
			dto.setLevels(levels);
			return dto;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}

}
