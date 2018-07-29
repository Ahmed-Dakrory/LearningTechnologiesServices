/**
 * 
 */
package main.com.zc.services.applicationService.survey.config.assembler;

import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.survey.model.Section;
import main.com.zc.services.presentation.survey.config.dto.SectionsDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;

/**
 * @author omnya
 *
 */
public class SectionAssembler {
	public SectionsDTO toDTO(Section section)
	{
		SectionsDTO dto=new SectionsDTO();
		dto.setId(section.getId());
		dto.setName(section.getName());
		dto.setDate(section.getDate());
		dto.setTitle(section.getTitle());
		dto.setExportStyle(section.getExportStyle());
		try{
			CoursesDTO course=new CoursesDTO();
			course.setId(section.getCourse().getId());
			course.setName(section.getCourse().getName());
			dto.setCourse(course);
		}
		catch(Exception ex){
			System.out.println("Can not attach course to the section");
		}
		return dto;
	} 
	
	public Section toEntity(SectionsDTO dto)
	{
		Section section=new Section();
		section.setId(dto.getId());
		section.setName(dto.getName());
		section.setDate(dto.getDate());
		section.setTitle(dto.getTitle());
		section.setExportStyle(dto.getExportStyle());
		try{
			Courses course=new Courses();
			course.setId(dto.getCourse().getId());
			course.setName(dto.getCourse().getName());
			section.setCourse(course);
		}
		catch(Exception ex){
			System.out.println("Can not attach course to the section");
		}
		return section;
	} 
}
