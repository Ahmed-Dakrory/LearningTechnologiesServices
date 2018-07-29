/**
 * 
 */
package main.com.zc.services.applicationService.survey.courseEval.services.impl;

import java.util.ArrayList;
import java.util.List;
import main.com.zc.services.applicationService.survey.courseEval.assembler.InstructorEvalAnswersAssembler;
import main.com.zc.services.applicationService.survey.courseEval.services.ICourseEvalQuestionsAppService;
import main.com.zc.services.applicationService.survey.courseEval.services.IInstructorsEvalAnswersAppService;
import main.com.zc.services.domain.courseEval.model.IInstructorsEvalAnswersRep;
import main.com.zc.services.domain.courseEval.model.InstructorsEvalAnswers;
import main.com.zc.services.domain.person.model.IEmployeeRepository;
import main.com.zc.services.presentation.survey.CourseEvalNew.dto.InstructorsEvalAnswersDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service
public class InstructorsEvalAnswersAppServiceImpl implements
		IInstructorsEvalAnswersAppService {
	@Autowired
	IInstructorsEvalAnswersRep rep;
	@Autowired
	IEmployeeRepository empRep;
	@Autowired
	ICourseEvalQuestionsAppService questionAppService;

	InstructorEvalAnswersAssembler assem=new InstructorEvalAnswersAssembler();
	@Override
	public List<InstructorsEvalAnswersDTO> getAll() {
		List<InstructorsEvalAnswersDTO> dtos = new ArrayList<InstructorsEvalAnswersDTO>();
		try {
			List<InstructorsEvalAnswers> all = rep.getAll();
			for (int i = 0; i < all.size(); i++) {
				dtos.add(assem.toDTO(all.get(i)));
			}

		} catch (Exception ex) {
			System.out.println("Can't get all answers");
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public List<InstructorsEvalAnswersDTO> getByCategoryID(Integer id) {
		List<InstructorsEvalAnswersDTO> dtos=new ArrayList<InstructorsEvalAnswersDTO>();
		try
		{
			List<InstructorsEvalAnswers> all=rep.getByCategoryID(id);
			for(int i=0;i<all.size();i++)
			{
				dtos.add(assem.toDTO(all.get(i)));
			}
			
		}
		catch(Exception ex)
		{
			System.out.println("Can't get all answers");
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public List<InstructorsEvalAnswersDTO> getByQuestID(Integer id) {
		List<InstructorsEvalAnswersDTO> dtos=new ArrayList<InstructorsEvalAnswersDTO>();
		try
		{
			List<InstructorsEvalAnswers> all=rep.getByQuestID(id);
			for(int i=0;i<all.size();i++)
			{
				dtos.add(assem.toDTO(all.get(i)));
			}
			
		}
		catch(Exception ex)
		{
			System.out.println("Can't get all answers");
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public List<InstructorsEvalAnswersDTO> getByInsFromID(Integer id) {
		List<InstructorsEvalAnswersDTO> dtos=new ArrayList<InstructorsEvalAnswersDTO>();
		try
		{
			List<InstructorsEvalAnswers> all=rep.getByInsFromID(id);
			for(int i=0;i<all.size();i++)
			{
				dtos.add(assem.toDTO(all.get(i)));
			}
			
		}
		catch(Exception ex)
		{
			System.out.println("Can't get all answers");
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public List<InstructorsEvalAnswersDTO> getByInsTOID(Integer id) {
		List<InstructorsEvalAnswersDTO> dtos=new ArrayList<InstructorsEvalAnswersDTO>();
		try
		{
			List<InstructorsEvalAnswers> all=rep.getByInsTOID(id);
			for(int i=0;i<all.size();i++)
			{
				dtos.add(assem.toDTO(all.get(i)));
			}
			
		}
		catch(Exception ex)
		{
			System.out.println("Can't get all answers");
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public List<InstructorsEvalAnswersDTO> getByFromInsAndTOIns(Integer from,
			Integer to) {
		List<InstructorsEvalAnswersDTO> dtos=new ArrayList<InstructorsEvalAnswersDTO>();
		try
		{
			List<InstructorsEvalAnswers> all=rep.getByFromInsAndTOIns(from,to);
			for(int i=0;i<all.size();i++)
			{
				dtos.add(assem.toDTO(all.get(i)));
			}
			
		}
		catch(Exception ex)
		{
			System.out.println("Can't get all answers");
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public InstructorsEvalAnswersDTO getByFromInsAndTOInsAndQuesID(Integer from,
			Integer to, Integer quesID) {
		InstructorsEvalAnswersDTO dtos=new InstructorsEvalAnswersDTO();
		try
		{
			InstructorsEvalAnswers all=rep.getByFromInsAndTOInsAndQuesID(from,to,quesID);
			
				dtos=assem.toDTO(all);
			
			
		}
		catch(Exception ex)
		{
			System.out.println("Can't get all answers");
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public List<InstructorsEvalAnswersDTO> getByQuestIDAndInsFromID(
			Integer ques, Integer from) {
		List<InstructorsEvalAnswersDTO> dtos=new ArrayList<InstructorsEvalAnswersDTO>();
		try
		{
			List<InstructorsEvalAnswers> all=rep.getByQuestIDAndInsFromID(ques,from);
			for(int i=0;i<all.size();i++)
			{
				dtos.add(assem.toDTO(all.get(i)));
			}
			
		}
		catch(Exception ex)
		{
			System.out.println("Can't get all answers");
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public List<InstructorsEvalAnswersDTO> getByQuestIDAndInsTOID(Integer ques,
			Integer to) {
		List<InstructorsEvalAnswersDTO> dtos=new ArrayList<InstructorsEvalAnswersDTO>();
		try
		{
			List<InstructorsEvalAnswers> all=rep.getByQuestIDAndInsTOID(ques,to);
			for(int i=0;i<all.size();i++)
			{
				dtos.add(assem.toDTO(all.get(i)));
			}
			
		}
		catch(Exception ex)
		{
			System.out.println("Can't get all answers");
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public InstructorsEvalAnswersDTO getById(Integer id) {
		InstructorsEvalAnswersDTO dtos=new InstructorsEvalAnswersDTO();
		try
		{
			InstructorsEvalAnswers all=rep.getById(id);
			
				dtos=assem.toDTO(all);
			
			
		}
		catch(Exception ex)
		{
			System.out.println("Can't get all answers");
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public InstructorsEvalAnswersDTO add(InstructorsEvalAnswersDTO form) {
		try{
			InstructorsEvalAnswers ans=new InstructorsEvalAnswers();
			ans=assem.toEntity(form);
			ans=rep.add(ans);
			InstructorsEvalAnswersDTO dto=new InstructorsEvalAnswersDTO();
			dto=assem.toDTO(ans);
			return dto;
			}
			catch(Exception ex)
			{
				System.out.println("Can't add ans");
				ex.printStackTrace();
			return null;
			}
	}

	@Override
	public InstructorsEvalAnswersDTO update(InstructorsEvalAnswersDTO form) {
		try{
			InstructorsEvalAnswers ans=new InstructorsEvalAnswers();
			ans=assem.toEntity(form);
			ans=rep.update(ans);
			InstructorsEvalAnswersDTO dto=new InstructorsEvalAnswersDTO();
			dto=assem.toDTO(ans);
			return dto;
			}
			catch(Exception ex)
			{
				System.out.println("Can't add ans");
				ex.printStackTrace();
			return null;
			}
	}

	@Override
	public boolean delete(InstructorsEvalAnswersDTO form) {
		try
		{
			return rep.delete(assem.toEntity(form));
		}
		catch(Exception ex)
		{
			System.out.println("Can't delete ans");
			ex.printStackTrace();
		}
		return false;
	}

}
