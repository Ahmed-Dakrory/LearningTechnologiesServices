/**
 * 
 */
package main.com.zc.services.applicationService.configuration.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.configuration.assembler.CourseStudentAssembler;
import main.com.zc.services.applicationService.configuration.services.IStudentCourseService;
import main.com.zc.services.domain.configurations.model.CourseStudent;
import main.com.zc.services.domain.configurations.model.ICourseStudentRep;
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.data.model.Courses_Instructors;
import main.com.zc.services.domain.data.model.Data;
import main.com.zc.services.domain.data.model.ICourse_InstructorRepository;
import main.com.zc.services.domain.data.model.ICoursesRepository;
import main.com.zc.services.domain.data.model.IDataRepository;
import main.com.zc.services.domain.data.model.IMailSettingsRepository;
import main.com.zc.services.domain.data.model.MailSetting;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.person.model.IEmployeeRepository;
import main.com.zc.services.domain.person.model.IStudentRepository;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.shared.enumurations.SemesterEnum;
import main.com.zc.services.presentation.configuration.dto.StudentCourseDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
@Service
public class StudentCourseServiceImpl implements IStudentCourseService{

	@Autowired
	IStudentRepository studentRep;
	@Autowired
	ICoursesRepository courseRep;
	@Autowired
	ICourseStudentRep courseStudentRep;
	@Autowired
	IDataRepository dataRep;
	@Autowired
	IEmployeeRepository insRep;
	@Autowired
	ICourse_InstructorRepository courseInsRep;
	@Autowired
	IMailSettingsRepository mailSettingRep;
	CourseStudentAssembler assem=new CourseStudentAssembler();
	@Override
	public List<StudentCourseDTO> praseFile(InputStream input)throws IOException
	  {
		List<StudentCourseDTO> dataList = new ArrayList<StudentCourseDTO>();
		try {
			//inputStream = resource.getInputStream();
			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(input);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows one by one

			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
				
				Row row = rowIterator.next();
				if(row.getRowNum()==0 || row.getRowNum()==1||
						row.getRowNum()==2||row.getRowNum()==3||row.getRowNum()==4||row.getRowNum()==5||
						row.getRowNum()==6||row.getRowNum()==7||row.getRowNum()==8||row.getRowNum()==9){
					   continue; //just skip the rows if row number is 0 or 8
					  }
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();
				StudentCourseDTO dto=new StudentCourseDTO();
				  StudentDTO student=new StudentDTO();
                  CoursesDTO course=new CoursesDTO();
				int count = 1;
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					count++;
				
                  
					if (count == 2) { // file No 
						
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_NUMERIC:
						{ /*System.out.print(cell.getNumericCellValue()
														 + "\t\t");*/
							String dec = new BigDecimal(
									cell.getNumericCellValue()).toString();
							try{
								
							/* System.out.print(Integer.parseInt(dec)+1 +
							 "\t\t");*/
							 student.setFacultyId((int)cell.getNumericCellValue());
							}
							catch(Exception ex)
							{
								ex.printStackTrace();
								
							}
			
							 break;
							 
						}
						case Cell.CELL_TYPE_STRING:
						{
						/*	System.out.print(cell.getStringCellValue());*/
							try{
								student.setFacultyId(Integer.parseInt(cell.getStringCellValue()));
							}
							catch(Exception ex)
							{
								ex.printStackTrace();
								
							}
						}
						
						}
					}

					

					if (count ==6) {// Mail
						switch (cell.getCellType()) {
	
						case Cell.CELL_TYPE_STRING:
							
						 
						      
						    	student.setMail(cell.getStringCellValue());
						    
						
							break;
						}
					}

					if (count == 9) { // course code
						
						switch (cell.getCellType()) {
						
						case Cell.CELL_TYPE_STRING:
							
							System.out.print(cell.getStringCellValue());
							course.setName(cell.getStringCellValue());
						      
						
							break;
						}
					
						
						
						
					}

					if (count == 16) { // year
				
						
						switch (cell.getCellType()) {

						case Cell.CELL_TYPE_STRING:
							try {
								course.setYear(Integer.parseInt(cell
										.getStringCellValue()));
							} catch (Exception ex) {
							}


							break;
						}
					}

					if (count == 17) { // semester
						
						switch (cell.getCellType()) {

						case Cell.CELL_TYPE_STRING:
							if (cell.getStringCellValue().equals("SPRG"))
								course.setSemester(SemesterEnum.Spring);
							else if (cell.getStringCellValue().equals("FALL"))
								course.setSemester(SemesterEnum.Fall);
							else if (cell.getStringCellValue().equals("SUM"))
								course.setSemester(SemesterEnum.Summer);

							break;
						}
					}
				
				}
			
			
					dto.setCourse(course);
					dto.setStudent(student);
					dataList.add(dto);
				
				System.out.println("");
			}

			input.close();
			
/*		 dataList.remove(0);
		 dataList.remove(0);
		 dataList.remove(0);
		 dataList.remove(0);
		 dataList.remove(0);
		 dataList.remove(0);
		 dataList.remove(0);
		 dataList.remove(0);
		 dataList.remove(0);*/
		 //dataList.remove(0);
/*		 dataList.remove(0);
		 dataList.remove(0);*/
/*		 dataList.remove(0);
		 dataList.remove(0);*/
		 
		// dataList.remove(0);
		// dataList.remove(0);
	//	 dataList.remove(0);
/*		 dataList.remove(1);
		 dataList.remove(2);
		 dataList.remove(3);
		 dataList.remove(4);
		 dataList.remove(5);
		 dataList.remove(6);
		 dataList.remove(7);
		 dataList.remove(8);
		 dataList.remove(9);
		 dataList.remove(10);
		 dataList.remove(11);
		 dataList.remove(12);
		 dataList.remove(13);*/
		 
		// dataList.remove(3);
		return dataList;
		 
	
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		

	}
	

	@Override
	public List<StudentCourseDTO> saveStudents(List<StudentCourseDTO> list) {
		
		try{
			List<CourseStudent>isbefore=courseStudentRep.getBySemesterAndYear(list.get(0).getCourse().getSemester().getID(),
					list.get(0).getCourse().getYear());
			boolean b =courseStudentRep.removelst(isbefore);
		/*	for(CourseStudent c:isbefore){
			boolean b =courseStudentRep.remove(c);
			
			System.out.print("old is deleted"+b);
			}*/
		}
		catch (Exception e) {
			// TODO: handle exception
		}	
	
		//TODO 
		//if lists uploaded before
		/*List<CourseStudent>isbefore=courseStudentRep.getBySemesterAndYear(list.get(0).getCourse().getSemester().getID(),
				list.get(0).getCourse().getYear());
		if(isbefore!=null)
			{if(isbefore.size()>0)
			{*/
				
				
/*		Map<Integer, List<String>> subs = new HashMap<Integer, List<String>>();
		for(StudentCourseDTO o : list){

		    // fetch the list for this object's id
		    List<String> temp = subs.get(o.getStudent().getFacultyId());

		    if(temp == null){
		         temp = new ArrayList<String>();

		         subs.put(o.getStudent().getFacultyId(), temp);
		    }

		     temp.add(o.getCourse().getName());
		}
		 for (Map.Entry<Integer, List<String>> entry : subs.entrySet())
		 {
		     System.out.println(entry.getKey() + "/" + entry.getValue());
		 	System.out.println("---------------------------------");
			System.out.println("Student with id "+entry.getKey());
			for( String c:entry.getValue())
			System.out.println("Course :"+c);
			List<CourseStudent>old=courseStudentRep.getByFacultyIDAndSemesterAndYear(entry.getKey(), list.get(0).getCourse().getSemester()
					.getID(), list.get(0).getCourse().getYear());
			List<String> oldCourses=new ArrayList<String>();
			for(CourseStudent o:old)
			{
				oldCourses.add(o.getCourse().getName());
			}
			Set<String> hs = new HashSet<>();
			hs.addAll(entry.getValue());
			List<String>temp=new ArrayList<String>();
			temp.addAll(hs);
			
			
			Set missing = new HashSet(temp);
			missing.removeAll(oldCourses);
			System.out.println("missing:" + missing);

			Set extra = new HashSet(oldCourses);
			extra.removeAll(temp);
			System.out.println("extra:" + extra);
			
			
			
		
		 } */
		//TODO 
		//print

		//else 
		//perform the below code
		
			
		List<StudentCourseDTO> outputLst=new ArrayList<StudentCourseDTO>();
		for(int i=0;i<list.size();i++)
		{
			CourseStudent entity=assem.toEntity(list.get(i));
			CourseStudent b=addRow(entity);
			if(b!=null)
			{
			if(b.getId()!=null)
			{
				StudentCourseDTO resultDTO=assem.toDTO(b);
				if(studentRep.getPersonById(resultDTO.getStudent().getId()).getData()!=null){
				resultDTO.getStudent().setName(studentRep.getPersonById(resultDTO.getStudent().getId()).getData().getNameInEnglish());
				resultDTO.getStudent().setFacultyId(studentRep.getPersonById(resultDTO.getStudent().getId()).getFileNo());
				resultDTO.getStudent().setMail(studentRep.getPersonById(resultDTO.getStudent().getId()).getData().getMail());
				}
				else {
					System.out.println(b.getStudent().getFileNo());
				}
			outputLst.add(resultDTO);
			
			}
			}
		
		}
		return outputLst;
			}
	
	
	public CourseStudent addRow(CourseStudent courseStudent)
	{
		try
		{
			//Get the student from DB
			Student student=studentRep.getPersonByFileNo(courseStudent.getStudent().getFileNo());
			// add the student if it doesn't exist
			if(student==null)
			{
				 int addedStudent=studentRep.add(courseStudent.getStudent());
				 courseStudent.getStudent().setId(addedStudent);
				
			}
			else // student exists before
			{
				if(student.getId()!=0)
				{
				 courseStudent.getStudent().setId(student.getId());
				}
				else // student doesn't exist before
				{
					 int addedStudent=studentRep.add(student);
					 courseStudent.getStudent().setId(addedStudent);
				}
			}
			
			
			// get the course from DB by Name and Year and semester
			Courses course=courseRep.getByNameAndYearAndSemester(courseStudent.getCourse().getName(), courseStudent.getCourse().getYear(), courseStudent.getCourse().getSemester().getValue());
			// add the course if it doesn't exist
			if(course==null)
			{
				Integer addedCourse=courseRep.add(courseStudent.getCourse());
				courseStudent.getCourse().setId(addedCourse);
			}
			else // course exists before 
			{
				if(course.getId()!=null)
				{
					courseStudent.getCourse().setId(course.getId());
				}
				else // course doesn't exist before
				{
					Integer addedCourse=courseRep.add(course);
					courseStudent.getCourse().setId(addedCourse);
				}
			}
			
			// then add new relation in table course student 
			return courseStudentRep.add(courseStudent);
			
			
		}
	catch(ConstraintViolationException ex )
		{
		ex.toString();
		return null;
			}
		catch(Exception ex)
		{
			ex.toString();
			return null;
		
		}
	}


	@Override
	public List<CoursesDTO> parseCoursesFile(InputStream input) {
		List<CoursesDTO> dataList = new ArrayList<CoursesDTO>();
		try {
			//inputStream = resource.getInputStream();
			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(input);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows one by one

			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();
				CoursesDTO course=new CoursesDTO();
				
				int count = 0;
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					count++;
				
                  
					if (count == 1) { // Year
						
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_NUMERIC:
						{ 
						try {
							course.setYear(Integer.parseInt(cell
									.getStringCellValue()));
						} catch (Exception ex) {
						}
			
							 break;
							 
						}
						case Cell.CELL_TYPE_STRING:
						{
						/*	System.out.print(cell.getStringCellValue());*/
							try{
								course.setYear(Integer.parseInt(cell.getStringCellValue()));
							}
							catch(Exception ex)
							{
								ex.printStackTrace();
								
							}
						}
						
						}
					}

					

					if (count ==2) {//semester
						switch (cell.getCellType()) {
	
						
						      
						case Cell.CELL_TYPE_STRING:
							if (cell.getStringCellValue().equals("SPRG"))
								course.setSemester(SemesterEnum.Spring);
							else if (cell.getStringCellValue().equals("FALL"))
								course.setSemester(SemesterEnum.Fall);
							else if (cell.getStringCellValue().equals("SUM"))
								course.setSemester(SemesterEnum.Summer);
						    
						
							break;
						}
					}

if (count ==3) { // course code
						
						switch (cell.getCellType()) {
						
						case Cell.CELL_TYPE_STRING:
							
							course.setName(cell.getStringCellValue());
						      
						
							break;
						}
					
						
						
						
					}

	if (count ==10) { // course code
		
		switch (cell.getCellType()) {
		
		case Cell.CELL_TYPE_STRING:
			
			course.setClo(cell.getStringCellValue());
		      
		
			break;
		}

	
	
	
}


				}
					dataList.add(course);
				
				System.out.println("");
			}

			input.close();
		 dataList.remove(0);
		
		return dataList;
		 
	
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public List<CoursesDTO> saveCourses(List<CoursesDTO> List) {
		List<CoursesDTO> addedCoursesLst=new ArrayList<CoursesDTO>();
		try
		{
			
			for(int i=0;i<List.size();i++)
			{
				// get the course from DB by Name and Year and semester
				Courses course=courseRep.getByNameAndYearAndSemester(List.get(i).getName(), List.get(i).getYear(), List.get(i).getSemester().getValue());
				// add the course if it doesn't exist
				
			if(course==null)
			{
				Courses courseObj=new Courses();
				courseObj.setName(List.get(i).getName());
				courseObj.setSemester(List.get(i).getSemester());
				courseObj.setYear(List.get(i).getYear());
				courseObj.setClo(List.get(i).getClo());
				Integer addedCourse=courseRep.add(courseObj);
				courseObj=courseRep.getById(addedCourse);
				CoursesDTO dto=new CoursesDTO();
				dto.setId(courseObj.getId());
				dto.setName(courseObj.getName());
				dto.setYear(courseObj.getYear());
				dto.setSemester(courseObj.getSemester());
				dto.setClo(courseObj.getClo());
				addedCoursesLst.add(dto);
				
			}
			else // course exists before 
			{
				if(course.getId()==null)
				{
					Courses courseObj=new Courses();
					courseObj.setName(List.get(i).getName());
					courseObj.setSemester(List.get(i).getSemester());
					courseObj.setYear(List.get(i).getYear());
					courseObj.setClo(List.get(i).getClo());
					Integer addedCourse=courseRep.add(courseObj);
					courseObj=courseRep.getById(addedCourse);
					CoursesDTO dto=new CoursesDTO();
					dto.setId(courseObj.getId());
					dto.setName(courseObj.getName());
					dto.setYear(courseObj.getYear());
					dto.setSemester(courseObj.getSemester());
					dto.setClo(courseObj.getClo());
					addedCoursesLst.add(dto);
					//courseStudent.getCourse().setId(course.getId());
					
				}
				
			}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return addedCoursesLst;
	}


	@Override
	public List<StudentDTO> praseStudentFile(InputStream input)
			throws IOException {
		List<StudentDTO> dataList = new ArrayList<StudentDTO>();
		try {
			//inputStream = resource.getInputStream();
			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(input);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows one by one

			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();
				
				  StudentDTO student=new StudentDTO();
                  
				int count = 0;
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					count++;
				
                  
					if (count == 1) { // file No 
						
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_NUMERIC:
						{ 
						
							try{
								
						
							 student.setFacultyId((int)cell.getNumericCellValue());
							}
							catch(Exception ex)
							{
								ex.printStackTrace();
								
							}
			
							 break;
							 
						}
						case Cell.CELL_TYPE_STRING:
						{
						
							try{
								student.setFacultyId(Integer.parseInt(cell.getStringCellValue()));
							}
							catch(Exception ex)
							{
								ex.printStackTrace();
								
							}
						}
						
						}
					}

					

					if (count ==2) {// Name
						switch (cell.getCellType()) {
	
						case Cell.CELL_TYPE_STRING:
							
						 
						      
						    	student.setName(cell.getStringCellValue());
						    
						
							break;
						}
					}
					
					if (count ==3) {// Mail
						switch (cell.getCellType()) {
	
						case Cell.CELL_TYPE_STRING:
							
						 
						      
						    	student.setMail(cell.getStringCellValue());
						    
						
							break;
						}
					}
					
					if (count ==4) {// Phone
						switch (cell.getCellType()) {
	
						case Cell.CELL_TYPE_STRING:
							
						 
						      
						    	student.setPhone(cell.getStringCellValue());
						    
						
							break;
						}
					}
					

				

				
				}
			
			
				
					dataList.add(student);
				
				System.out.println("");
			}

			input.close();
		 dataList.remove(0);
				return dataList;
		 
	
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		

	}


	@Override
	public List<StudentDTO> getNewStudents(List<StudentDTO> originalList) {
		List<StudentDTO> newStudents=new ArrayList<StudentDTO>();
		for(int i=0;i<originalList.size();i++)
		{
			Student student=studentRep.getPersonByFileNo(originalList.get(i).getFacultyId());
			if(student==null)
			{
				newStudents.add(originalList.get(i));
			}
		}
		return newStudents;
	}


	@Override
	public boolean addStudent(StudentDTO student) {
		try
		{
			Student addedStudent=new Student();
			Data data=new Data();
			data.setMail(student.getMail());
			data.setPhone(student.getPhone());
			data.setNameInEnglish(student.getName());
			int dataId=dataRep.add(data);
			data.setId(dataId);
			addedStudent.setData(data);
			addedStudent.setFileNo(student.getFacultyId());
			int addedID=studentRep.add(addedStudent);
			if(addedID!=0)
				return true;
			else 
				return false;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}


	@Override
	public List<CoursesDTO> getNewCourses(List<CoursesDTO> originalList) {
		List<CoursesDTO> addedCoursesLst=new ArrayList<CoursesDTO>();
		try
		{
			
			for(int i=0;i<originalList.size();i++)
			{
				// get the course from DB by Name and Year and semester
				Courses course=courseRep.getByNameAndYearAndSemester(originalList.get(i).getName(), originalList.get(i).getYear(), originalList.get(i).getSemester().getValue());
				// add the course if it doesn't exist
				
			if(course==null)
			{
				Courses courseObj=new Courses();
				courseObj.setName(originalList.get(i).getName());
				courseObj.setSemester(originalList.get(i).getSemester());
				courseObj.setYear(originalList.get(i).getYear());
				courseObj.setClo(originalList.get(i).getClo());
				
			/*	Integer addedCourse=courseRep.add(courseObj);
				courseObj=courseRep.getById(addedCourse);
				CoursesDTO dto=new CoursesDTO();
				dto.setId(courseObj.getId());
				dto.setName(courseObj.getName());
				dto.setYear(courseObj.getYear());
				dto.setSemester(courseObj.getSemester());*/
				addedCoursesLst.add(originalList.get(i));
				
			}
/*			else // course exists before 
			{
				if(course.getId()==null)
				{
					Courses courseObj=new Courses();
					courseObj.setName(originalList.get(i).getName());
					courseObj.setSemester(originalList.get(i).getSemester());
					courseObj.setYear(originalList.get(i).getYear());
					Integer addedCourse=courseRep.add(courseObj);
					courseObj=courseRep.getById(addedCourse);
					CoursesDTO dto=new CoursesDTO();
					dto.setId(courseObj.getId());
					dto.setName(courseObj.getName());
					dto.setYear(courseObj.getYear());
					dto.setSemester(courseObj.getSemester());
					addedCoursesLst.add(dto);
					//courseStudent.getCourse().setId(course.getId());
					
				}
				
			}*/
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return addedCoursesLst;
	}


	@Override
	public List<StudentDTO> getAllStudents() {
		List<Student> students=new ArrayList<Student>();
		students=studentRep.getAll();
		List<StudentDTO> dtos=new ArrayList<StudentDTO>();
		for(int i=0;i<students.size();i++)
		{
			StudentDTO dto=new StudentDTO();
			dto.setFacultyId(students.get(i).getFileNo());
			if(students.get(i).getData()!=null){
			dto.setMail(students.get(i).getData().getMail());
			dto.setName(students.get(i).getData().getNameInEnglish());
			dto.setPhone(students.get(i).getData().getPhone());
			}
			dto.setId(students.get(i).getId());
			dtos.add(dto);
		}
		
		return dtos;
	}


	@Override
	public List<CoursesDTO> getCoursesOfStudent(Integer studenID) {
		List<CourseStudent> objects=new ArrayList<CourseStudent>();
		List<CoursesDTO> dtos=new ArrayList<CoursesDTO>();
		objects=courseStudentRep.getByStudentID(studenID);
		for(int i=0;i<objects.size();i++)
		{
			CoursesDTO dto=new CoursesDTO();
			dto.setId(objects.get(i).getCourse().getId());
			dto.setName(objects.get(i).getCourse().getName());
			dto.setSemester(objects.get(i).getCourse().getSemester());
			dto.setYear(objects.get(i).getCourse().getYear());
			dto.setClo(objects.get(i).getCourse().getClo());
			dtos.add(dto);
		}
		return dtos;
	}


	@Override
	public boolean deleteCourseForStudent(Integer courseId, Integer studentID) {
	    
		try{
			CourseStudent courseStudent=new CourseStudent();
		
	    courseStudent=courseStudentRep.getByStudentIDAndByCourseID(studentID, courseId);
	    boolean b=courseStudentRep.remove(courseStudent);
		return b;
		}
		catch(Exception ex)
		{
			return false;
		}
	}


	@Override
	public List<InstructorDTO> getAllInstructors() {
		List<Employee> instructors=new ArrayList<Employee>();
		List<InstructorDTO> dtos=new ArrayList<InstructorDTO>();
		
		instructors=insRep.getAll();
		for(int i=0;i<instructors.size();i++)
		{
			InstructorDTO dto=new InstructorDTO();
			dto.setId(instructors.get(i).getId());
			dto.setMail(instructors.get(i).getMail());
			dto.setName(instructors.get(i).getName());
			dto.setEmpType(instructors.get(i).getType());
			dtos.add(dto);
		}
		return dtos;
	}
	@Override
	public List<InstructorDTO> getAllEmp() {
		List<Employee> instructors=new ArrayList<Employee>();
		List<InstructorDTO> dtos=new ArrayList<InstructorDTO>();
		
		instructors=insRep.getAllEmp();
		for(int i=0;i<instructors.size();i++)
		{
			InstructorDTO dto=new InstructorDTO();
			dto.setId(instructors.get(i).getId());
			dto.setMail(instructors.get(i).getMail());
			dto.setName(instructors.get(i).getName());
			dto.setEmpType(instructors.get(i).getType());
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public List<CoursesDTO> getCoursesOfInstructor(Integer insID) {
		
		List<Courses_Instructors> objects=new ArrayList<Courses_Instructors>();
		List<CoursesDTO> courses=new ArrayList<CoursesDTO>();
		
		try{
		objects=courseInsRep.getByInstructorId(insID);
		for(int i=0;i<objects.size();i++)
		{
			CoursesDTO course=new CoursesDTO();
			course.setYear(objects.get(i).getCourse().getYear());
			course.setSemester(objects.get(i).getCourse().getSemester());
			course.setName(objects.get(i).getCourse().getName());
			course.setId(objects.get(i).getCourse().getId());
			course.setClo(objects.get(i).getCourse().getClo());
			courses.add(course);
		}
	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			
		}
		return courses;
		
	}


	@Override
	public InstructorDTO addIns(InstructorDTO dto) {
		try{
		MailSetting mailSetting=new MailSetting();
		mailSetting.setEveryDays(1);
		mailSetting.setNotifyMe(true);
		mailSetting=mailSettingRep.add(mailSetting);
		Employee addedIns=new Employee();
		addedIns.setMailSetting(mailSetting);
		addedIns.setName(dto.getName());
		addedIns.setMail(dto.getMail());
		addedIns.setType(dto.getEmpType());
		addedIns.setTitle(dto.getTitle());
		addedIns.setPhone(dto.getPhone());
		addedIns=insRep.add(addedIns);
		InstructorDTO ins=new InstructorDTO();
		ins.setId(addedIns.getId());
		ins.setName(addedIns.getName());
		ins.setMail(addedIns.getMail());
		return ins;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		
		return null;
		}
	}


	@Override
	public List<CoursesDTO> getCoursesOfInstructorBySemesterAndYear(Integer insID,Integer year, Integer semester) {
		List<Courses_Instructors> objects=new ArrayList<Courses_Instructors>();
		List<CoursesDTO> courses=new ArrayList<CoursesDTO>();
		
		try{
		objects=courseInsRep.getByInstructorIdAndYearAndSemester(insID,year,semester);
		for(int i=0;i<objects.size();i++)
		{
			CoursesDTO course=new CoursesDTO();
			course.setYear(objects.get(i).getCourse().getYear());
			course.setSemester(objects.get(i).getCourse().getSemester());
			course.setName(objects.get(i).getCourse().getName());
			course.setId(objects.get(i).getCourse().getId());
			course.setClo(objects.get(i).getCourse().getClo());
			courses.add(course);
		}
	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			
		}
		return courses;
		
	}



}
