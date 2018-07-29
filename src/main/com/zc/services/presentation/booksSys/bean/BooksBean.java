/**
 * 
 */
package main.com.zc.services.presentation.booksSys.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.com.zc.services.domain.shared.enumurations.BookStatusEnum;
import main.com.zc.services.presentation.booksSys.dto.BookCopiesDTO;
import main.com.zc.services.presentation.booksSys.dto.BookDTO;
import main.com.zc.services.presentation.booksSys.dto.BookInstructorDTO;
import main.com.zc.services.presentation.booksSys.dto.BookStudentDTO;
import main.com.zc.services.presentation.booksSys.dto.BooksLogsDTO;
import main.com.zc.services.presentation.booksSys.facade.IBookCopiesFacade;
import main.com.zc.services.presentation.booksSys.facade.IBooksFacade;
import main.com.zc.services.presentation.booksSys.facade.IBooksLogsFacade;
import main.com.zc.services.presentation.configuration.facade.ICoursesFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.BaseDTO;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author omnya
 *
 */
@SessionScoped
@ManagedBean(name="BooksBean")
public class BooksBean {
	
	private List<BookDTO> books;
	private BookDTO detailedBook;
    private List<BookStudentDTO> bookStudentsDTOs;
    private List<BookInstructorDTO> bookInstructorsDTOs;
    private List<CoursesDTO> courses;
    private BookDTO addedBook;
    private Integer selectedYear;
    private Integer selectedSemester;
    private List<BaseDTO> semesterLst;
    private List<Integer> yearLst;
    private Integer selectedCourse;
    private BookDTO selectedBook;
    private Integer copies;
    private boolean increase;
    private boolean withDate;
    private List<BookCopiesDTO> copiesLst;
	@ManagedProperty("#{IBooksFacade}")
   	private IBooksFacade facade; 
	
	@ManagedProperty("#{ICoursesFacade}")
   	private ICoursesFacade coursesFacade; 
	
	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
	private IGetLoggedInInstructorData getInsDataFacade;
	
	@ManagedProperty("#{IBooksLogsFacade}")
	private IBooksLogsFacade logFacade;
	
	@ManagedProperty("#{IBookCopiesFacade}")
	private IBookCopiesFacade copiesFacade;
	
	@PostConstruct
	public void init()
	{
	bookStudentsDTOs=new ArrayList<BookStudentDTO>();
	bookInstructorsDTOs=new ArrayList<BookInstructorDTO>();
	fillBooksList();
	fillSemesterLst();
	}
	public void fillBooksList()
	{
		books=new ArrayList<BookDTO>();
		books=facade.getAll();
	}
	public void onRowSelect(SelectEvent event) {  
	  	try {
	  		BookDTO selectedDTO=(BookDTO) event.getObject();
	  		setDetailedBook(selectedDTO);
	  	    getDetailedBook().setLogs(facade.getLogsOfBook(getDetailedBook().getId()));
	  	    fillCopies();
	  	   
	  		try {
	    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    		StringBuffer url=origRequest.getRequestURL();
	    			FacesContext.getCurrentInstance().getExternalContext().redirect
					("bookdetails.xhtml");
	    			
	    			
	    		
			} catch (IOException e) {
				
				e.printStackTrace();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void activateCourse(BookDTO book)
	{
		book.setStatus(1);
		book=facade.update(book);
		if(book!=null)
		{
		setDetailedBook(book);
		 getDetailedBook().setLogs(facade.getLogsOfBook(getDetailedBook().getId()));
		fillBooksList();
		
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Book is activated successfully");
		}
		else 
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Failed to be activated");
	}
	public void deactivateCourse(BookDTO book)
	{
		book.setStatus(0);
		book=facade.update(book);
		if(book!=null)
		{
		setDetailedBook(book);
		 getDetailedBook().setLogs(facade.getLogsOfBook(getDetailedBook().getId()));
		fillBooksList();
		 
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Book is deactivated successfully");
		}
		else 
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Failed to be deactivated");
	}
	public List<BookDTO> getBooks() {
		fillBooksList();
		return books;
	}

	public void fillCopies(){
		copiesLst=copiesFacade.getByBookID(getDetailedBook().getId());
	}
	
	public void createNewBook(){
		
		try{
			CoursesDTO course=new CoursesDTO();
			course.setId(getSelectedCourse());
			getAddedBook().setCourse(course);
			getAddedBook().setRemaingCopies(getAddedBook().getOriginalCopies());
			BookDTO addedBookDTO=facade.add(getAddedBook());
			// Log the creator
			BooksLogsDTO logs=new BooksLogsDTO();
			logs.setAction("Added");
			logs.setDate(Calendar.getInstance());
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				InstructorDTO staff=new InstructorDTO();
				staff=getInsDataFacade.getInsByPersonMail(authentication.getName());
				logs.setUser(staff.getName());
				logs.setBook(addedBookDTO);
				logs=logFacade.addLog(logs);
				if(logs!=null){
					System.out.println("Failed to log the history");
				}
			}
		
		fillBooksList();
		setSelectedBook(addedBookDTO);
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Book was added successfully");
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("addCourseDlg.hide();");
	
		}
		catch(Exception ex){
			ex.printStackTrace();
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "System Error");
		}
	}

	public void fillCourseLst(AjaxBehaviorEvent event)
	{
		courses=new ArrayList<CoursesDTO>();
		courses=coursesFacade.getCoursesByYearAndSemester(getSelectedYear(), getSelectedSemester());
		
	}
	public void fillSemesterLst()
	{
		semesterLst=new ArrayList<BaseDTO>();
		semesterLst.add(new BaseDTO(0,"Fall"));
		semesterLst.add(new BaseDTO(1,"Spring"));
		semesterLst.add(new BaseDTO(2,"Summer"));
		//semesterLst.add(new BaseDTO(3,"Winter"));
	}
	public void fillYearLst(AjaxBehaviorEvent event)
	{
		 yearLst=new ArrayList<Integer>();
		 setSelectedYear(null);
		 setSelectedCourse(null);
		for(int i=2013;i<2031;i++)
		{
			yearLst.add(i);
		}
	}
    public void clearDialogBox(){
    	addedBook=new BookDTO();
    	setSelectedSemester(null);
    	setSelectedYear(null);
    	setSelectedCourse(null);
    }
    public void preGenerate(){
    	setCopies(null);
    	RequestContext context = RequestContext.getCurrentInstance();
		context.execute("generateCopiesDlg.show();");
    }
    public void preExport(){
    	setWithDate(false);
    	RequestContext context = RequestContext.getCurrentInstance();
		context.execute("exportCopiesDlg.show();");
    }
    public void GenerateCodes(){
    //open dialog box to write number of copies
    //open generate list of codes and choose the place to save the file
    // add the copies to DB
    }
    public void downloadFile() throws IOException
    {
       File file = new File("/home/omnya/Desktop/Summary.xlsx");
       InputStream fis = new FileInputStream(file);
       
       byte[] buf = new byte[1024];
       int offset = 0;
       int numRead = 0;
       while ((offset < buf.length) && ((numRead = fis.read(buf, offset, buf.length - offset)) >= 0)) 
       {
         offset += numRead;
       }
       fis.close();
       HttpServletResponse response =
          (HttpServletResponse) FacesContext.getCurrentInstance()
              .getExternalContext().getResponse();
     
      response.setContentType("application/vnd.ms-excel");
      response.setHeader("Content-Disposition", "attachment;filename="+file.getName());
      response.getOutputStream().write(buf);
      response.getOutputStream().flush();
      response.getOutputStream().close();
      FacesContext.getCurrentInstance().responseComplete();
    }
    public void generateReport(){
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
         
        HSSFRow row;
         
        row = sheet.createRow((short)0);
        row.createCell((short)0).setCellValue("Bar Code");
/*        row.createCell((short)1).setCellValue("Year");
        row.createCell((short)2).setCellValue("Course ID");
        row.createCell((short)3).setCellValue("Version ID");
        row.createCell((short)4).setCellValue("Copy Num");*/
         

        for (int i = 1; i < getCopies()+1; i++){
        	row = sheet.createRow((short)i);
        	//TODO 
            //Add New Version to DB 
        	BookCopiesDTO copyDTO=new BookCopiesDTO();    
        	copyDTO.setBook(getDetailedBook());
        	copyDTO.setAddedDate(Calendar.getInstance());
        	copyDTO.setLastOper(Calendar.getInstance());
        	copyDTO.setStatus(BookStatusEnum.FREE);
        	BookCopiesDTO addedCopy= copiesFacade.add(copyDTO);
        	if(addedCopy!=null)
        	{
        		if(addedCopy.getId()!=null)
        		{
        			if(increase)
        			{
        				
        				List<BookCopiesDTO> copiess=new ArrayList<BookCopiesDTO>();
        				copiess=copiesFacade.getByBookID(getDetailedBook().getId());
        				addedCopy.setBarCode("ZC-"+getDetailedBook().getCourse().getSemester().getName()+"-"+getDetailedBook().getCourse().getYear()+"-"+getDetailedBook().getCourse().getId()+
                    			"-"+addedCopy.getId()+"-"+(copiess.size()));
        				
        				BookCopiesDTO updatedCopy= copiesFacade.update(addedCopy);
            			getDetailedBook().setRemaingCopies(getDetailedBook().getRemaingCopies()+1);
        				detailedBook=facade.update(getDetailedBook());
            		  	
            			    row.createCell((short)0).setCellValue(updatedCopy.getBarCode());
        			}
        			else {
        				List<BookCopiesDTO> copiess=new ArrayList<BookCopiesDTO>();
        				copiess=copiesFacade.getByBookID(getDetailedBook().getId());
        				addedCopy.setBarCode("ZC-"+getDetailedBook().getCourse().getSemester().getName()+"-"+getDetailedBook().getCourse().getYear()+"-"+getDetailedBook().getCourse().getId()+
                    			"-"+addedCopy.getId()+"-"+(copiess.size()));
            			BookCopiesDTO updatedCopy= copiesFacade.update(addedCopy);
            		
            		  	
            			    row.createCell((short)0).setCellValue(updatedCopy.getBarCode());
        			}
        			
        	         /*   row.createCell((short)1).setCellValue(addedCopy.getBook().getCourse().getYear());
        	            row.createCell((short)2).setCellValue(addedCopy.getBook().getCourse().getId());
        	            row.createCell((short)3).setCellValue(addedCopy.getId());
        	            row.createCell((short)4).setCellValue(i+i);*/
        		}
        	}
            //Get the added copy Data to fill the excel sheet
        	
        	
           
      
        }
    	     HttpServletResponse response =
                (HttpServletResponse) FacesContext.getCurrentInstance()
                    .getExternalContext().getResponse();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition",  "attachment; filename=bar codes.xls");
         
         
        try {
            ServletOutputStream out = response.getOutputStream();
   
             wb.write(out);
             out.flush();
             out.close();
         
       } catch (IOException ex) { 
               ex.printStackTrace();
       }
  
         
      FacesContext faces = FacesContext.getCurrentInstance();
      faces.responseComplete();  
      RequestContext context = RequestContext.getCurrentInstance();
      context.execute("generateCopiesDlg.hide();");
      //
      RequestContext.getCurrentInstance().update("dlgForm:addPanel");
      FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("dlgForm:addPanel");
 
    }
    public void exportCodes(){
    	List<BookCopiesDTO> copiess=new ArrayList<BookCopiesDTO>();
		copiess=copiesFacade.getByBookID(getDetailedBook().getId());
    	if(withDate){
    		 HSSFWorkbook wb = new HSSFWorkbook();
    	        HSSFSheet sheet = wb.createSheet();
    	         
    	        HSSFRow row;
    	         
    	        row = sheet.createRow((short)0);
    	        row.createCell((short)0).setCellValue("Bar Code");
    	        row.createCell((short)1).setCellValue("Creation Date");
    	        for (int i = 0; i < copiess.size(); i++){
    	        	row = sheet.createRow((short)i+1);
    	           	if(copiess.get(i)!=null)
    	        	{
    	        		if(copiess.get(i).getId()!=null)
    	        		{
    	        				    row.createCell((short)0).setCellValue(copiess.get(i).getBarCode());
    	            			    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
    	            			    String strDate = sdf.format(copiess.get(i).getAddedDate().getTime());
    	            			    row.createCell((short)1).setCellValue(strDate);
    	        		
    	        		
    	        		}
    	        	}
    	          
    	      
    	        }
    	    	     HttpServletResponse response =
    	                (HttpServletResponse) FacesContext.getCurrentInstance()
    	                    .getExternalContext().getResponse();
    	        response.setContentType("application/vnd.ms-excel");
    	        response.setHeader("Content-disposition",  "attachment; filename=bar-codes.xls");
    	         
    	         
    	        try {
    	            ServletOutputStream out = response.getOutputStream();
    	   
    	             wb.write(out);
    	             out.flush();
    	             out.close();
    	         
    	       } catch (IOException ex) { 
    	               ex.printStackTrace();
    	       }
    	  
    	         
    	      FacesContext faces = FacesContext.getCurrentInstance();
    	      faces.responseComplete();  
    	      RequestContext context = RequestContext.getCurrentInstance();
    	      context.execute("exportCopiesDlg.hide();");
     	      //
     	      RequestContext.getCurrentInstance().update("dlgFormExport:addPanelExport");
     	      FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("dlgFormExport:addPanelExport");
    	}
    	else {
    		 HSSFWorkbook wb = new HSSFWorkbook();
 	        HSSFSheet sheet = wb.createSheet();
 	         
 	        HSSFRow row;
 	         
 	        row = sheet.createRow((short)0);
 	        row.createCell((short)0).setCellValue("Bar Code");
 	     //   row.createCell((short)1).setCellValue("Creation Date");
 	        for (int i = 0; i < copiess.size(); i++){
 	        	row = sheet.createRow((short)i+1);
 	           	if(copiess.get(i)!=null)
 	        	{
 	        		if(copiess.get(i).getId()!=null)
 	        		{
 	        				    row.createCell((short)0).setCellValue(copiess.get(i).getBarCode());
 	            			  /*  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
 	            			    String strDate = sdf.format(copiess.get(i).getAddedDate().getTime());
 	            			    row.createCell((short)1).setCellValue(strDate);*/
 	        		
 	        		
 	        		}
 	        	}
 	          
 	      
 	        }
 	    	     HttpServletResponse response =
 	                (HttpServletResponse) FacesContext.getCurrentInstance()
 	                    .getExternalContext().getResponse();
 	        response.setContentType("application/vnd.ms-excel");
 	        response.setHeader("Content-disposition",  "attachment; filename=bar-codes.xls");
 	         
 	         
 	        try {
 	            ServletOutputStream out = response.getOutputStream();
 	   
 	             wb.write(out);
 	             out.flush();
 	             out.close();
 	         
 	       } catch (IOException ex) { 
 	               ex.printStackTrace();
 	       }
 	  
 	         
 	      FacesContext faces = FacesContext.getCurrentInstance();
 	      faces.responseComplete();  
 	      RequestContext context = RequestContext.getCurrentInstance();
 	      context.execute("exportCopiesDlg.hide();");
  	      //
  	      RequestContext.getCurrentInstance().update("dlgFormExport:addPanelExport");
  	      FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("dlgFormExport:addPanelExport");
    	}
    }
    
	public void setBooks(List<BookDTO> books) {
		this.books = books;
	}
	public IBooksFacade getFacade() {
		return facade;
	}
	public void setFacade(IBooksFacade facade) {
		this.facade = facade;
	}
	public BookDTO getDetailedBook() {
		return detailedBook;
	}
	public void setDetailedBook(BookDTO detailedBook) {
		this.detailedBook = detailedBook;
	}
	public List<BookStudentDTO> getBookStudentsDTOs() {
		// fillStudents();
		return bookStudentsDTOs;
	}
	public void setBookStudentsDTOs(List<BookStudentDTO> bookStudentsDTOs) {
		this.bookStudentsDTOs = bookStudentsDTOs;
	}
	public List<BookInstructorDTO> getBookInstructorsDTOs() {
		 //fillInstructors();
		return bookInstructorsDTOs;
	}
	public void setBookInstructorsDTOs(List<BookInstructorDTO> bookInstructorsDTOs) {
		this.bookInstructorsDTOs = bookInstructorsDTOs;
	}
	public List<CoursesDTO> getCourses() {
		return courses;
	}
	public void setCourses(List<CoursesDTO> courses) {
		this.courses = courses;
	}
	public BookDTO getAddedBook() {
		return addedBook;
	}
	public void setAddedBook(BookDTO addedBook) {
		this.addedBook = addedBook;
	}
	public Integer getSelectedYear() {
		return selectedYear;
	}
	public void setSelectedYear(Integer selectedYear) {
		this.selectedYear = selectedYear;
	}
	public Integer getSelectedSemester() {
		return selectedSemester;
	}
	public void setSelectedSemester(Integer selectedSemester) {
		this.selectedSemester = selectedSemester;
	}
	public ICoursesFacade getCoursesFacade() {
		return coursesFacade;
	}
	public void setCoursesFacade(ICoursesFacade coursesFacade) {
		this.coursesFacade = coursesFacade;
	}
	public List<BaseDTO> getSemesterLst() {
		return semesterLst;
	}
	public void setSemesterLst(List<BaseDTO> semesterLst) {
		this.semesterLst = semesterLst;
	}
	public List<Integer> getYearLst() {
		return yearLst;
	}
	public void setYearLst(List<Integer> yearLst) {
		this.yearLst = yearLst;
	}
	public Integer getSelectedCourse() {
		return selectedCourse;
	}
	public void setSelectedCourse(Integer selectedCourse) {
		this.selectedCourse = selectedCourse;
	}
	public BookDTO getSelectedBook() {
		return selectedBook;
	}
	public void setSelectedBook(BookDTO selectedBook) {
		this.selectedBook = selectedBook;
	}
	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}
	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}
	public IBooksLogsFacade getLogFacade() {
		return logFacade;
	}
	public void setLogFacade(IBooksLogsFacade logFacade) {
		this.logFacade = logFacade;
	}
	public Integer getCopies() {
		return copies;
	}
	public void setCopies(Integer copies) {
		this.copies = copies;
	}
	public IBookCopiesFacade getCopiesFacade() {
		return copiesFacade;
	}
	public void setCopiesFacade(IBookCopiesFacade copiesFacade) {
		this.copiesFacade = copiesFacade;
	}
	public boolean isIncrease() {
		return increase;
	}
	public void setIncrease(boolean increase) {
		this.increase = increase;
	}
	public boolean isWithDate() {
		return withDate;
	}
	public void setWithDate(boolean withDate) {
		this.withDate = withDate;
	}
	public List<BookCopiesDTO> getCopiesLst() {
		return copiesLst;
	}
	public void setCopiesLst(List<BookCopiesDTO> copiesLst) {
		this.copiesLst = copiesLst;
	}

}
