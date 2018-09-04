/**
 * 
 */
package main.com.zc.services.presentation.booksSys.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.primefaces.context.RequestContext;

import main.com.zc.services.domain.shared.enumurations.BookActionEnum;
import main.com.zc.services.domain.shared.enumurations.BookStatusEnum;
import main.com.zc.services.presentation.booksSys.dto.BookCopiesDTO;
import main.com.zc.services.presentation.booksSys.dto.BookDTO;
import main.com.zc.services.presentation.booksSys.dto.BookInstructorDTO;
import main.com.zc.services.presentation.booksSys.dto.BookStudentDTO;
import main.com.zc.services.presentation.booksSys.facade.IBookCopiesFacade;
import main.com.zc.services.presentation.booksSys.facade.IBookReservationFacade;
import main.com.zc.services.presentation.booksSys.facade.IBooksFacade;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.services.presentation.users.facade.IStudentFacade;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.LoginStaffDTO;
import main.com.zc.shared.presentation.dto.PersonDataDTO;

/**
 * @author omnya
 *
 */
@SessionScoped
@ManagedBean(name="CopiesOperationBean")
public class CopiesOperationBean {

	
	
	@ManagedProperty("#{IBookReservationFacade}")
	private IBookReservationFacade bookReservationFacade;
	
	@ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
	private IGetLoggedInStudentDataFacade studentDataFacade;
	
	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
	private IGetLoggedInInstructorData getInsDataFacade;
	
	
	@ManagedProperty("#{IStudentFacade}")
	private	IStudentFacade studentFacade;
	
	@ManagedProperty("#{IBooksFacade}")
	IBooksFacade booksFacade;
	
	@ManagedProperty("#{IBookCopiesFacade}")
	IBookCopiesFacade booksCopiesFacade;
	
	private Integer selectedAssignee;
	private Integer selectedInsID;
	private Integer selectedTaID;
	private List<InstructorDTO> insLst;
	private List<InstructorDTO> taLst;
	private String studentID;
	private String barCode;
	private InstructorDTO selectedIns;
	private InstructorDTO selectedTa;
	private StudentDTO selectedStudent;
	private List<BookStudentDTO> studentBooks;
	private List<BookInstructorDTO> insBooks;
	private List<BookInstructorDTO> taBooks;
	private BookStudentDTO selectedStudentBooks;
	private BookInstructorDTO selectedInsBooks;
	private BookInstructorDTO selectedTABooks;
	private String holdingImageURL;
	private String numberOfBooksD;
	private int numberOfBooksN;
	
	
	
	@PostConstruct
	public void init()
	{
		 fillInsLst();
		 holdingImageURL="/resources/images/onhold.png";
	}
	public void fillInsLst(){
		insLst=new ArrayList<InstructorDTO>();
		taLst=new ArrayList<InstructorDTO>();
		insLst=bookReservationFacade.getAllInstructors();
		taLst=bookReservationFacade.getAllTAs();
	}
	public void getPersonData(){
		if(selectedAssignee==1) // student
		{
			
			selectedStudent=new StudentDTO();
			studentBooks=new ArrayList<BookStudentDTO>();
			try{
			Integer id=Integer.parseInt(studentID);
			PersonDataDTO person=studentDataFacade.getPersonByFileNo(id);
			StudentDTO student=studentFacade.getById(person.getId());
			System.out.println("Name:"+holdingImageURL);

			if(student!=null){
				System.out.println("Name:"+student.getName());

				selectedStudent=student;
				studentBooks=bookReservationFacade.getByStudentID(selectedStudent.getId());
				/*
				 * Ahmed Dakrory
				 * set a hold for who not deliver all books
				 */
				int numberOfReserved=0;
				int numbmerOfRetrieved=0;
				
				for(BookStudentDTO bookStudentDTO:studentBooks){
					if(bookStudentDTO.getAction().getValue()==0){
						numberOfReserved++;
					}else{
						numbmerOfRetrieved++;
					}
				}
				if(numberOfReserved==numbmerOfRetrieved){
					 holdingImageURL="/resources/images/allowed.png";
					 numberOfBooksN=numberOfReserved-numbmerOfRetrieved;
					 numberOfBooksD="Number of unretrived Books = "+String.valueOf(numberOfBooksN);

				}else{
					 holdingImageURL="/resources/images/onhold.png";
					 numberOfBooksN=numberOfReserved-numbmerOfRetrieved;
					 numberOfBooksD="Number of unretrived Books = "+String.valueOf(numberOfBooksN);

				}
				System.out.println("Name:"+holdingImageURL);
			   
			}
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		else if(selectedAssignee==2) // Instructor
		{

			System.out.println(holdingImageURL);
			selectedIns=new InstructorDTO();
			insBooks=new ArrayList<BookInstructorDTO>();
			try{
			
			InstructorDTO tempIns =getInsDataFacade.getInsByPersonID(selectedInsID);
			
			if(tempIns!=null){
				selectedIns=tempIns;
				insBooks=bookReservationFacade.getByInsID(selectedIns.getId());
				/*
				 * Ahmed Dakrory
				 * set a hold for who not deliver all books
				 */
				int numberOfReserved=0;
				int numbmerOfRetrieved=0;
				
				for(BookInstructorDTO bookInstructorDTO:insBooks){
					if(bookInstructorDTO.getAction().getValue()==0){
						numberOfReserved++;
					}else{
						numbmerOfRetrieved++;
					}
				}
				if(numberOfReserved==numbmerOfRetrieved){
					 holdingImageURL="/resources/images/allowed.png";
					 numberOfBooksN=numberOfReserved-numbmerOfRetrieved;
					 numberOfBooksD="Number of unretrived Books = "+String.valueOf(numberOfBooksN);

				}else{
					 holdingImageURL="/resources/images/onhold.png";
					 numberOfBooksN=numberOfReserved-numbmerOfRetrieved;
					 numberOfBooksD="Number of unretrived Books = "+String.valueOf(numberOfBooksN);

				}
			}
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		else if(selectedAssignee==3) // TA 
		{
			System.out.println(holdingImageURL);
			selectedTa=new InstructorDTO();
			taBooks=new ArrayList<BookInstructorDTO>();
			try{
			
			InstructorDTO tempIns =getInsDataFacade.getInsByPersonID(selectedTaID);
			
			if(tempIns!=null){
				selectedTa=tempIns;
				taBooks=bookReservationFacade.getByInsID(selectedTa.getId());
				/*
				 * Ahmed Dakrory
				 * set a hold for who not deliver all books
				 */
				int numberOfReserved=0;
				int numbmerOfRetrieved=0;
				
				for(BookInstructorDTO bookInstructorDTO:taBooks){
					if(bookInstructorDTO.getAction().getValue()==0){
						numberOfReserved++;
					}else{
						numbmerOfRetrieved++;
					}
				}
				if(numberOfReserved==numbmerOfRetrieved){
					 holdingImageURL="/resources/images/allowed.png";
					 numberOfBooksN=numberOfReserved-numbmerOfRetrieved;
					 numberOfBooksD="Number of unretrived Books = "+String.valueOf(numberOfBooksN);

				}else{
					 holdingImageURL="/resources/images/onhold.png";
					 numberOfBooksN=numberOfReserved-numbmerOfRetrieved;
					 numberOfBooksD="Number of unretrived Books = "+String.valueOf(numberOfBooksN);

				}
			}
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	public void ReserveBook(){
		try{
		BookCopiesDTO checkCopy=new BookCopiesDTO();
		checkCopy=booksCopiesFacade.getByBarCode(getBarCode());
		if(checkCopy!=null){
			if(checkCopy.getId()!=null)
			{
		if(!checkCopy.getStatus().equals(BookStatusEnum.HELD)){
		if(selectedAssignee==1) // student
		{
				BookStudentDTO relation=new BookStudentDTO();
				relation.setDate(Calendar.getInstance());
				relation.setBarCode(getBarCode());
				relation.setStudent(getSelectedStudent());
				relation.setAction(BookActionEnum.RESERVE);
				relation=bookReservationFacade.add(relation);
			
				if(relation!=null)
				{
					 BookCopiesDTO copy=new BookCopiesDTO();
					    copy= booksCopiesFacade.getByBarCode(getBarCode());
					    BookDTO book =copy.getBook();
					    book.setRemaingCopies(copy.getBook().getRemaingCopies()-1);;
					    booksFacade.update(book);
					    copy.setStatus(BookStatusEnum.HELD);
					    booksCopiesFacade.update(copy);
						JavaScriptMessagesHandler.RegisterErrorMessage(null, "Book is reserved Successfully");
					
				}setBarCode(null);
				studentBooks=bookReservationFacade.getByStudentID(selectedStudent.getId());}
				
		
		
		
		else if(selectedAssignee==2) // ins
		{
				if(checkCopy!=null)
			{
				if(checkCopy.getId()!=null)
				{

		
				
		
			
			BookInstructorDTO relation=new BookInstructorDTO();
			relation.setDate(Calendar.getInstance());
			relation.setBarCode(getBarCode());
			relation.setInstructor(getSelectedIns());
			relation.setAction(BookActionEnum.RESERVE);
			relation=bookReservationFacade.add(relation);
		
			if(relation!=null)
			{
				 BookCopiesDTO copy=new BookCopiesDTO();
				    copy= booksCopiesFacade.getByBarCode(getBarCode());
				    BookDTO book =copy.getBook();
				    book.setRemaingCopies(copy.getBook().getRemaingCopies()-1);;
				    booksFacade.update(book);
				    copy.setStatus(BookStatusEnum.HELD);
				    booksCopiesFacade.update(copy);
					JavaScriptMessagesHandler.RegisterErrorMessage(null, "Book is reserved Successfully");
			
					
			}
		
			setBarCode(null);	
			}
		 insBooks=bookReservationFacade.getByInsID(selectedIns.getId());
		}
				
		}
		else 	if(selectedAssignee==3) // TA
		{
				if(checkCopy!=null)
			{
				if(checkCopy.getId()!=null)
				{
	
				
		
			BookInstructorDTO relation=new BookInstructorDTO();
			relation.setDate(Calendar.getInstance());
			relation.setBarCode(getBarCode());
			relation.setInstructor(getSelectedTa());
			relation.setAction(BookActionEnum.RESERVE);
			relation=bookReservationFacade.add(relation);
		
			if(relation!=null)
			{
				 BookCopiesDTO copy=new BookCopiesDTO();
				    copy= booksCopiesFacade.getByBarCode(getBarCode());
				    BookDTO book =copy.getBook();
				    book.setRemaingCopies(copy.getBook().getRemaingCopies()-1);;
				    booksFacade.update(book);
				    copy.setStatus(BookStatusEnum.HELD);
				    booksCopiesFacade.update(copy);
					JavaScriptMessagesHandler.RegisterErrorMessage(null, "Book is reserved Successfully");
			}
		
			setBarCode(null);	}
		 taBooks=bookReservationFacade.getByInsID(selectedTa.getId());
		}
		}
		
		}
		else JavaScriptMessagesHandler.RegisterErrorMessage(null, "Busy Book");
		}
		else {
			 JavaScriptMessagesHandler.RegisterErrorMessage(null, "No such recorded book in Database");
		}
		}
		else JavaScriptMessagesHandler.RegisterErrorMessage(null, "No such recorded book in Database");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Unexpected Error please contact LT");
		}
		
	
	}



	  public void ExportReport(){
	    	
	    		 HSSFWorkbook wb = new HSSFWorkbook();
	 	        HSSFSheet sheet = wb.createSheet();
	 	        
	 	        HSSFRow row;
	 	         
	 	        row = sheet.createRow((short)0);
	 	        row.createCell((short)0).setCellValue("Book");
	 	        row.createCell((short)1).setCellValue("Course");
	 	        row.createCell((short)2).setCellValue("Bar Code");
	 	        row.createCell((short)3).setCellValue("Date");
	 	        row.createCell((short)4).setCellValue("Action");
	 	       
	 	        
	 	        if(selectedAssignee==1){
	 	        	//Student
	 	        	 for (int i = 0; i < studentBooks.size(); i++){
	 	 	        	row = sheet.createRow((short)i+1);
	 	 	           	if(studentBooks.get(i)!=null)
	 	 	        	{
	 	 	        		if(studentBooks.get(i).getId()!=null)
	 	 	        		{
	 	 	        			
	 	 	        				    row.createCell((short)0).setCellValue(studentBooks.get(i).getBook().getName());
	 	 	        		 	        row.createCell((short)1).setCellValue(studentBooks.get(i).getBook().getCourse().getName());
	 	 	        		 	        row.createCell((short)2).setCellValue(studentBooks.get(i).getBarCode());
	 	 	        		 	      row.createCell((short)3).setCellValue(studentBooks.get(i).getFriendlyDate());
	 	 	        		 	        row.createCell((short)4).setCellValue(studentBooks.get(i).getAction().getName());
	 	 	        		 	      boolean BookIsReturned=false;
	 		 	        		 	     for(int j=0;j<studentBooks.size();j++){
	 		 	        		 	    	 if(i!=j){
	 			 	        		 	    	 if(studentBooks.get(i).getBarCode().equalsIgnoreCase(studentBooks.get(j).getBarCode())){
	 			 	        		 	    		BookIsReturned=true;
	 			 	        		 	    	 }
	 		 	        		 	    	 }
	 		 	        		 	     }
	 		 	        		 	        if(!BookIsReturned){
	 		 		 	        				HSSFCellStyle style = wb.createCellStyle();
	 		 			        		 	    style.setFillForegroundColor(HSSFColor.YELLOW.index);
	 		 	 	        		 	    	style.setFillPattern((short) FillPatternType.SOLID_FOREGROUND.ordinal());
	 		 	 	        		 	    	HSSFCell curCell = row.getCell((short)0);
	 		 	 	        		 	        curCell.setCellStyle(style);
	 		 	 	        		 	      HSSFCell curCell2 = row.getCell((short)1);
	 		 	 	        		 	        curCell2.setCellStyle(style);
	 		 	 	        		 	      HSSFCell curCell3 = row.getCell((short)2);
	 		 	 	        		 	        curCell3.setCellStyle(style);
	 		 	 	        		 	      HSSFCell curCell4 = row.getCell((short)3);
	 		 	 	        		 	        curCell4.setCellStyle(style);
	 		 	 	        		 	      HSSFCell curCell5 = row.getCell((short)4);
	 		 	 	        		 	        curCell5.setCellStyle(style);		
	 		 	        		 	      	}
	 	 	        		
	 	 	        		}
	 	 	        	}
	 	 	          
	 	 	      
	 	 	        }
	 	 	        
	 	        }else if(selectedAssignee==2){
	 	        	//inst
	 	        	 for (int i = 0; i < insBooks.size(); i++){
	 	 	        	row = sheet.createRow((short)i+1);
	 	 	           	if(insBooks.get(i)!=null)
	 	 	        	{
	 	 	        		if(insBooks.get(i).getId()!=null)
	 	 	        		{
	 	 	        				
	 	 	        			
	 	 	        		
	 	 	        				    row.createCell((short)0).setCellValue(insBooks.get(i).getBook().getName());
	 	 	        				  row.createCell((short)1).setCellValue(insBooks.get(i).getBook().getCourse().getName());
	 	 	        		 	        row.createCell((short)2).setCellValue(insBooks.get(i).getBarCode());
	 	 	        		 	      row.createCell((short)3).setCellValue(insBooks.get(i).getFriendlyDate());
	 	 	        		 	        row.createCell((short)4).setCellValue(insBooks.get(i).getAction().getName());
	 	 	        		 	     
	 	 	        		 	        boolean BookIsReturned=false;
	 	 	        		 	     for(int j=0;j<insBooks.size();j++){
	 	 	        		 	    	 if(i!=j){
	 		 	        		 	    	 if(insBooks.get(i).getBarCode().equalsIgnoreCase(insBooks.get(j).getBarCode())){
	 		 	        		 	    		BookIsReturned=true;
	 		 	        		 	    	 }
	 	 	        		 	    	 }
	 	 	        		 	     }
	 	 	        		 	        if(!BookIsReturned){
	 	 		 	        				HSSFCellStyle style = wb.createCellStyle();
	 	 			        		 	    style.setFillForegroundColor(HSSFColor.YELLOW.index);
	 	 	 	        		 	    	style.setFillPattern((short) FillPatternType.SOLID_FOREGROUND.ordinal());
	 	 	 	        		 	    	HSSFCell curCell = row.getCell((short)0);
	 	 	 	        		 	        curCell.setCellStyle(style);
	 	 	 	        		 	      HSSFCell curCell2 = row.getCell((short)1);
	 	 	 	        		 	        curCell2.setCellStyle(style);
	 	 	 	        		 	      HSSFCell curCell3 = row.getCell((short)2);
	 	 	 	        		 	        curCell3.setCellStyle(style);
	 	 	 	        		 	      HSSFCell curCell4 = row.getCell((short)3);
	 	 	 	        		 	        curCell4.setCellStyle(style);
	 	 	 	        		 	      HSSFCell curCell5 = row.getCell((short)4);
	 	 	 	        		 	        curCell5.setCellStyle(style);		
	 	 	        		 	      	}
	 	 	        		
	 	 	        		}
	 	 	        	}
	 	 	          
	 	 	      
	 	 	        }
	 	 	        
	 	 	    	 
	 	        }else if(selectedAssignee==3){
	 	        	//Ta
	 	        	 
		 	        for (int i = 0; i < taBooks.size(); i++){
		 	        	row = sheet.createRow((short)i+1);
		 	           	if(taBooks.get(i)!=null)
		 	        	{
		 	        		if(taBooks.get(i).getId()!=null)
		 	        		{
		 	        			
		 	        				    row.createCell((short)0).setCellValue(taBooks.get(i).getBook().getName());
		 	        		 	        row.createCell((short)1).setCellValue(taBooks.get(i).getBook().getCourse().getName());
		 	        		 	      row.createCell((short)2).setCellValue(taBooks.get(i).getBarCode());
		 	        		 	      row.createCell((short)3).setCellValue(taBooks.get(i).getFriendlyDate());
		 	        		 	        row.createCell((short)4).setCellValue(taBooks.get(i).getAction().getName());
		 	        		 	      boolean BookIsReturned=false;
			 	        		 	     for(int j=0;j<taBooks.size();j++){
			 	        		 	    	 if(i!=j){
				 	        		 	    	 if(taBooks.get(i).getBarCode().equalsIgnoreCase(taBooks.get(j).getBarCode())){
				 	        		 	    		BookIsReturned=true;
				 	        		 	    	 }
			 	        		 	    	 }
			 	        		 	     }
			 	        		 	        if(!BookIsReturned){
			 		 	        				HSSFCellStyle style = wb.createCellStyle();
			 			        		 	    style.setFillForegroundColor(HSSFColor.YELLOW.index);
			 	 	        		 	    	style.setFillPattern((short) FillPatternType.SOLID_FOREGROUND.ordinal());
			 	 	        		 	    	HSSFCell curCell = row.getCell((short)0);
			 	 	        		 	        curCell.setCellStyle(style);
			 	 	        		 	      HSSFCell curCell2 = row.getCell((short)1);
			 	 	        		 	        curCell2.setCellStyle(style);
			 	 	        		 	      HSSFCell curCell3 = row.getCell((short)2);
			 	 	        		 	        curCell3.setCellStyle(style);
			 	 	        		 	      HSSFCell curCell4 = row.getCell((short)3);
			 	 	        		 	        curCell4.setCellStyle(style);
			 	 	        		 	      HSSFCell curCell5 = row.getCell((short)4);
			 	 	        		 	        curCell5.setCellStyle(style);		
			 	        		 	      	}
		 	        		
		 	        		}
		 	        	}
		 	          
		 	      
		 	        }
	 	        }
	 	        
	 	        
	 	        
	 	       HttpServletResponse response =
	 	                (HttpServletResponse) FacesContext.getCurrentInstance()
	 	                    .getExternalContext().getResponse();
	 	        response.setContentType("application/vnd.ms-excel");
	 	        response.setHeader("Content-disposition",  "attachment; filename=ReportOfAllBooks.xls");
	 	         
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
    
	  public void SendEmail() {
			if(numberOfBooksN!=0){
				if(selectedAssignee==1){
					//Student
					try{
						if (selectedStudent.getMail() != null) { // Implementation of sending mails
							Properties props = new Properties();
							props.put("mail.smtp.host", "smtp.gmail.com");
							props.put("mail.smtp.socketFactory.port", "465");
							props.put("mail.smtp.socketFactory.class",
									"javax.net.ssl.SSLSocketFactory");
							props.put("mail.smtp.auth", "true");
							props.put("mail.smtp.port", "465");

							Session session = Session.getDefaultInstance(props,
									new javax.mail.Authenticator() {
										protected PasswordAuthentication getPasswordAuthentication() {
											return new PasswordAuthentication(
													"LearningTechnologies@zewailcity.edu.eg",
													"zcltinfo");
										}
									});

							javax.mail.internet.InternetAddress[] addressTo = new javax.mail.internet.InternetAddress[1];
							
			     				addressTo[0] = new javax.mail.internet.InternetAddress(
			     						selectedStudent.getMail());
				

							/* Message message = new MimeMessage(session); */
							Message message = new MimeMessage(session);

							message.setFrom(new InternetAddress(
									"LearningTechnologies@zewailcity.edu.eg"));
							message.setRecipients(Message.RecipientType.TO, addressTo);

							message.setSubject("Need The Reserved Books");

							String htmlText = "<div style=width:700px;margin:0 auto;font:normal 13px/30px Segoe, Segoe UI, DejaVu Sans, Trebuchet MS, Verdana, sans-serif !important;>"
									+ "<ul style=margin:0;padding:0;>"
									+ "<li style=list-style:none;float:left;width:700px;margin:0;>"
									+ "	<ul style=margin:0;padding:0;width:700px;margin-top:18px;>"
									+ "<li style=list-style:none;float:left;width:260px;padding:0;><img src=\"http://zclt.info/ZCTestMail/university_logo.png\" alt=Zewail City of Science and Technology /></li>"
									+ "<li style=list-style:none;float:right;width:121px;padding:0;><img src=\"http://zclt.info/ZCTestMail/LT_logo_l.png\" alt=Center for Learning Technologies style=margin-top:4px; /></li>"
									+ "</ul>"
									+ "</li>"
									+ "<li style=list-style:none;float:left;width:700px;background:#f1f2f2;margin:15px 0 24px 0;padding:1px 0;>&nbsp;</li>"
									+ "<li style=list-style:none;float:left;width:700px;margin-bottom:24px;padding-left:24px;>"
									+ "<h2 style=margin:0;padding:0;color:#404040 !important;>Learning Technologies Services</h2>"
									+ "</li>"
									+ "<li style=list-style:none;float:left;width:700px;marin:0;background:#f2f0f0;>"
									+ "<div style=padding:24px 36px;color:#676767 !important;>"
									+ "<span style=color:#676767>Dear "
									+ selectedStudent.getName()
									+ ",</span><br/><br/><br/>"
									+ "<span style=color:#676767>You're receiving this email because you don't return "+String.valueOf(numberOfBooksN)+" Books"+"</span><br/><br/><br/>"
									+ "</span><br/><br/>"
									+ "<span style=color:#676767>We kindly ask you to visit the admission for returning the Books</span><br/><br/>"
									+ "<span style=color:#676767>Thank you, </span><br/><br/>"
									+ "<span style=color:#676767>Center for Learning Technologies</span>"
									+ "</div>"
									+ "</li>"
									+ "<li style=list-style:none;float:left;width:700px;margin-bottom:4px;background:#ececec;>"
									+ "<ul style=margin:0;padding:0;>"
									+ "<li style=list-style:none;float:left;width:134px;margin:0;padding:18px 36px !important;color:#717070;>"
									+ "<a href=http://www.zclt.info/ title=Center for Learning Technologies><img src=\"http://zclt.info/ZCTestMail/LT_logo_s.png\"  alt=Center for Learning Technologies /></a><br/>"
									+ "<span style=color:#404040;font-size:11px;>Giving Fuel to Innovation</span>"
									+ "</li>"
									+ "<li style=list-style:none;float:right;width:59px;margin:0;padding:18px 36px !important;color:#717070;>"
									+ "<a href=http://www.zewailcity.edu.eg/ title=Zewail City of Science and Technology><img src=\"http://zclt.info/ZCTestMail/ZC_logo.png\"  alt=Zewail City of Science and Technology /></a>"
									+ "</li>"
									+ "</ul>"
									+ "</li>"
									+ "<li style=list-style:none;float:left;width:700px;margin-bottom:12px;background:#ececec;>"
									+ "<div style=padding:8px 16px;color:#a1a0a0;font-size:11px;line-height:20px;>"
									+ " <br/><b><span style=color:#a1a0a0;font-size:11px;>Follow us:</sapn></b><a href=https://www.facebook.com/learning.technologies.zewailcity title=ZC LT Facebook><img src=\"http://zclt.info/ZCTestMail/facebook_square.png\"  alt=ZC LT Facebook style=vertical-align:middle;/></a>"
									+ "  <a href=https://www.youtube.com/channel/UCiajXXIv0rCpxVIgCDekm2A title=ZC LT Youtube><img src=\"http://zclt.info/ZCTestMail/youtube_square.png\"   alt=ZC LT Youtube style=vertical-align:middle;/></a>"
									+ "</div>" + "</li>" + "</ul>" + "</div>";
							/*
							 * message.setText("Dear " + dao.getName() + " ," +
							 * "\n Your Password is : " + dao.getPassword() + "\n\n Regards"
							 * + "\n Learning Technologies Department" +
							 * "\n\n Please do not reply to this email ");
							 * 
							 * Transport.send(message);
							 */

							message.setContent(htmlText, "text/html; charset=ISO-8859-1");

							Transport.send(message);

							JavaScriptMessagesHandler.RegisterNotificationMessage("",
									"Email Sent");
							// System.out.println("Done sending ");
							

						} else {
							JavaScriptMessagesHandler.RegisterErrorMessage("",
									"This email address is not registered in the system!");
						}

					} catch (Exception excep) {
						excep.printStackTrace();
						JavaScriptMessagesHandler.RegisterErrorMessage("",
								"This email address is not registered in the system!");

					}
					
				}else if(selectedAssignee==2){
					//inst
					try{
						if (selectedIns.getMail() != null) { // Implementation of sending mails
							Properties props = new Properties();
							props.put("mail.smtp.host", "smtp.gmail.com");
							props.put("mail.smtp.socketFactory.port", "465");
							props.put("mail.smtp.socketFactory.class",
									"javax.net.ssl.SSLSocketFactory");
							props.put("mail.smtp.auth", "true");
							props.put("mail.smtp.port", "465");

							Session session = Session.getDefaultInstance(props,
									new javax.mail.Authenticator() {
										protected PasswordAuthentication getPasswordAuthentication() {
											return new PasswordAuthentication(
													"LearningTechnologies@zewailcity.edu.eg",
													"zcltinfo");
										}
									});

							javax.mail.internet.InternetAddress[] addressTo = new javax.mail.internet.InternetAddress[1];
							
			     				addressTo[0] = new javax.mail.internet.InternetAddress(
			     						selectedIns.getMail());
				

							/* Message message = new MimeMessage(session); */
							Message message = new MimeMessage(session);

							message.setFrom(new InternetAddress(
									"LearningTechnologies@zewailcity.edu.eg"));
							message.setRecipients(Message.RecipientType.TO, addressTo);

							message.setSubject("Need The Reserved Books");

							String htmlText = "<div style=width:700px;margin:0 auto;font:normal 13px/30px Segoe, Segoe UI, DejaVu Sans, Trebuchet MS, Verdana, sans-serif !important;>"
									+ "<ul style=margin:0;padding:0;>"
									+ "<li style=list-style:none;float:left;width:700px;margin:0;>"
									+ "	<ul style=margin:0;padding:0;width:700px;margin-top:18px;>"
									+ "<li style=list-style:none;float:left;width:260px;padding:0;><img src=\"http://zclt.info/ZCTestMail/university_logo.png\" alt=Zewail City of Science and Technology /></li>"
									+ "<li style=list-style:none;float:right;width:121px;padding:0;><img src=\"http://zclt.info/ZCTestMail/LT_logo_l.png\" alt=Center for Learning Technologies style=margin-top:4px; /></li>"
									+ "</ul>"
									+ "</li>"
									+ "<li style=list-style:none;float:left;width:700px;background:#f1f2f2;margin:15px 0 24px 0;padding:1px 0;>&nbsp;</li>"
									+ "<li style=list-style:none;float:left;width:700px;margin-bottom:24px;padding-left:24px;>"
									+ "<h2 style=margin:0;padding:0;color:#404040 !important;>Learning Technologies Services</h2>"
									+ "</li>"
									+ "<li style=list-style:none;float:left;width:700px;marin:0;background:#f2f0f0;>"
									+ "<div style=padding:24px 36px;color:#676767 !important;>"
									+ "<span style=color:#676767>Dear "
									+ selectedIns.getName()
									+ ",</span><br/><br/><br/>"
									+ "<span style=color:#676767>You're receiving this email because you don't return "+String.valueOf(numberOfBooksN)+" Books"+"</span><br/><br/><br/>"
									+ "</span><br/><br/>"
									+ "<span style=color:#676767>We kindly ask you to visit the admission for returning the Books</span><br/><br/>"
									+ "<span style=color:#676767>Thank you, </span><br/><br/>"
									+ "<span style=color:#676767>Center for Learning Technologies</span>"
									+ "</div>"
									+ "</li>"
									+ "<li style=list-style:none;float:left;width:700px;margin-bottom:4px;background:#ececec;>"
									+ "<ul style=margin:0;padding:0;>"
									+ "<li style=list-style:none;float:left;width:134px;margin:0;padding:18px 36px !important;color:#717070;>"
									+ "<a href=http://www.zclt.info/ title=Center for Learning Technologies><img src=\"http://zclt.info/ZCTestMail/LT_logo_s.png\"  alt=Center for Learning Technologies /></a><br/>"
									+ "<span style=color:#404040;font-size:11px;>Giving Fuel to Innovation</span>"
									+ "</li>"
									+ "<li style=list-style:none;float:right;width:59px;margin:0;padding:18px 36px !important;color:#717070;>"
									+ "<a href=http://www.zewailcity.edu.eg/ title=Zewail City of Science and Technology><img src=\"http://zclt.info/ZCTestMail/ZC_logo.png\"  alt=Zewail City of Science and Technology /></a>"
									+ "</li>"
									+ "</ul>"
									+ "</li>"
									+ "<li style=list-style:none;float:left;width:700px;margin-bottom:12px;background:#ececec;>"
									+ "<div style=padding:8px 16px;color:#a1a0a0;font-size:11px;line-height:20px;>"
									+ " <br/><b><span style=color:#a1a0a0;font-size:11px;>Follow us:</sapn></b><a href=https://www.facebook.com/learning.technologies.zewailcity title=ZC LT Facebook><img src=\"http://zclt.info/ZCTestMail/facebook_square.png\"  alt=ZC LT Facebook style=vertical-align:middle;/></a>"
									+ "  <a href=https://www.youtube.com/channel/UCiajXXIv0rCpxVIgCDekm2A title=ZC LT Youtube><img src=\"http://zclt.info/ZCTestMail/youtube_square.png\"   alt=ZC LT Youtube style=vertical-align:middle;/></a>"
									+ "</div>" + "</li>" + "</ul>" + "</div>";
							/*
							 * message.setText("Dear " + dao.getName() + " ," +
							 * "\n Your Password is : " + dao.getPassword() + "\n\n Regards"
							 * + "\n Learning Technologies Department" +
							 * "\n\n Please do not reply to this email ");
							 * 
							 * Transport.send(message);
							 */

							message.setContent(htmlText, "text/html; charset=ISO-8859-1");

							Transport.send(message);

							JavaScriptMessagesHandler.RegisterNotificationMessage("",
									"Email Sent");
							// System.out.println("Done sending ");
							

						} else {
							JavaScriptMessagesHandler.RegisterErrorMessage("",
									"This email address is not registered in the system!");
						}

					} catch (Exception excep) {
						excep.printStackTrace();
						JavaScriptMessagesHandler.RegisterErrorMessage("",
								"This email address is not registered in the system!");

					}
					
				}else if(selectedAssignee==3){
					//TA
					try{
						if (selectedTa.getMail() != null) { // Implementation of sending mails
							Properties props = new Properties();
							props.put("mail.smtp.host", "smtp.gmail.com");
							props.put("mail.smtp.socketFactory.port", "465");
							props.put("mail.smtp.socketFactory.class",
									"javax.net.ssl.SSLSocketFactory");
							props.put("mail.smtp.auth", "true");
							props.put("mail.smtp.port", "465");

							Session session = Session.getDefaultInstance(props,
									new javax.mail.Authenticator() {
										protected PasswordAuthentication getPasswordAuthentication() {
											return new PasswordAuthentication(
													"LearningTechnologies@zewailcity.edu.eg",
													"zcltinfo");
										}
									});

							javax.mail.internet.InternetAddress[] addressTo = new javax.mail.internet.InternetAddress[1];
							
			     				addressTo[0] = new javax.mail.internet.InternetAddress(
			     						selectedTa.getMail());
				

							/* Message message = new MimeMessage(session); */
							Message message = new MimeMessage(session);

							message.setFrom(new InternetAddress(
									"LearningTechnologies@zewailcity.edu.eg"));
							message.setRecipients(Message.RecipientType.TO, addressTo);

							message.setSubject("Need The Reserved Books");

							String htmlText = "<div style=width:700px;margin:0 auto;font:normal 13px/30px Segoe, Segoe UI, DejaVu Sans, Trebuchet MS, Verdana, sans-serif !important;>"
									+ "<ul style=margin:0;padding:0;>"
									+ "<li style=list-style:none;float:left;width:700px;margin:0;>"
									+ "	<ul style=margin:0;padding:0;width:700px;margin-top:18px;>"
									+ "<li style=list-style:none;float:left;width:260px;padding:0;><img src=\"http://zclt.info/ZCTestMail/university_logo.png\" alt=Zewail City of Science and Technology /></li>"
									+ "<li style=list-style:none;float:right;width:121px;padding:0;><img src=\"http://zclt.info/ZCTestMail/LT_logo_l.png\" alt=Center for Learning Technologies style=margin-top:4px; /></li>"
									+ "</ul>"
									+ "</li>"
									+ "<li style=list-style:none;float:left;width:700px;background:#f1f2f2;margin:15px 0 24px 0;padding:1px 0;>&nbsp;</li>"
									+ "<li style=list-style:none;float:left;width:700px;margin-bottom:24px;padding-left:24px;>"
									+ "<h2 style=margin:0;padding:0;color:#404040 !important;>Learning Technologies Services</h2>"
									+ "</li>"
									+ "<li style=list-style:none;float:left;width:700px;marin:0;background:#f2f0f0;>"
									+ "<div style=padding:24px 36px;color:#676767 !important;>"
									+ "<span style=color:#676767>Dear "
									+ selectedTa.getName()
									+ ",</span><br/><br/><br/>"
									+ "<span style=color:#676767>You're receiving this email because you don't return "+String.valueOf(numberOfBooksN)+" Books"+"</span><br/><br/><br/>"
									+ "</span><br/><br/>"
									+ "<span style=color:#676767>We kindly ask you to visit the admission for returning the Books</span><br/><br/>"
									+ "<span style=color:#676767>Thank you, </span><br/><br/>"
									+ "<span style=color:#676767>Center for Learning Technologies</span>"
									+ "</div>"
									+ "</li>"
									+ "<li style=list-style:none;float:left;width:700px;margin-bottom:4px;background:#ececec;>"
									+ "<ul style=margin:0;padding:0;>"
									+ "<li style=list-style:none;float:left;width:134px;margin:0;padding:18px 36px !important;color:#717070;>"
									+ "<a href=http://www.zclt.info/ title=Center for Learning Technologies><img src=\"http://zclt.info/ZCTestMail/LT_logo_s.png\"  alt=Center for Learning Technologies /></a><br/>"
									+ "<span style=color:#404040;font-size:11px;>Giving Fuel to Innovation</span>"
									+ "</li>"
									+ "<li style=list-style:none;float:right;width:59px;margin:0;padding:18px 36px !important;color:#717070;>"
									+ "<a href=http://www.zewailcity.edu.eg/ title=Zewail City of Science and Technology><img src=\"http://zclt.info/ZCTestMail/ZC_logo.png\"  alt=Zewail City of Science and Technology /></a>"
									+ "</li>"
									+ "</ul>"
									+ "</li>"
									+ "<li style=list-style:none;float:left;width:700px;margin-bottom:12px;background:#ececec;>"
									+ "<div style=padding:8px 16px;color:#a1a0a0;font-size:11px;line-height:20px;>"
									+ " <br/><b><span style=color:#a1a0a0;font-size:11px;>Follow us:</sapn></b><a href=https://www.facebook.com/learning.technologies.zewailcity title=ZC LT Facebook><img src=\"http://zclt.info/ZCTestMail/facebook_square.png\"  alt=ZC LT Facebook style=vertical-align:middle;/></a>"
									+ "  <a href=https://www.youtube.com/channel/UCiajXXIv0rCpxVIgCDekm2A title=ZC LT Youtube><img src=\"http://zclt.info/ZCTestMail/youtube_square.png\"   alt=ZC LT Youtube style=vertical-align:middle;/></a>"
									+ "</div>" + "</li>" + "</ul>" + "</div>";
							/*
							 * message.setText("Dear " + dao.getName() + " ," +
							 * "\n Your Password is : " + dao.getPassword() + "\n\n Regards"
							 * + "\n Learning Technologies Department" +
							 * "\n\n Please do not reply to this email ");
							 * 
							 * Transport.send(message);
							 */

							message.setContent(htmlText, "text/html; charset=ISO-8859-1");

							Transport.send(message);

							JavaScriptMessagesHandler.RegisterNotificationMessage("",
									"Email Sent");
							// System.out.println("Done sending ");
							

						} else {
							JavaScriptMessagesHandler.RegisterErrorMessage("",
									"This email address is not registered in the system!");
						}

					} catch (Exception excep) {
						excep.printStackTrace();
						JavaScriptMessagesHandler.RegisterErrorMessage("",
								"This email address is not registered in the system!");

					}
				
			}
					
				}else{
					JavaScriptMessagesHandler.RegisterNotificationMessage("",
							"There is no books to be send the mail!");
				}
				
		}
public IBookReservationFacade getBookReservationFacade() {
		return bookReservationFacade;
	}
	public void setBookReservationFacade(
			IBookReservationFacade bookReservationFacade) {
		this.bookReservationFacade = bookReservationFacade;
	}
	public Integer getSelectedAssignee() {
		return selectedAssignee;
	}
	public void setSelectedAssignee(Integer selectedAssignee) {
		this.selectedAssignee = selectedAssignee;
	}

	public Integer getSelectedInsID() {
		return selectedInsID;
	}

	public void setSelectedInsID(Integer selectedInsID) {
		this.selectedInsID = selectedInsID;
	}

	public Integer getSelectedTaID() {
		return selectedTaID;
	}

	public void setSelectedTaID(Integer selectedTaID) {
		this.selectedTaID = selectedTaID;
	}

	public List<InstructorDTO> getInsLst() {
		return insLst;
	}

	public void setInsLst(List<InstructorDTO> insLst) {
		this.insLst = insLst;
	}

	public List<InstructorDTO> getTaLst() {
		return taLst;
	}

	
	public void setTaLst(List<InstructorDTO> taLst) {
		this.taLst = taLst;
	}
	public String getStudentID() {
		return studentID;
	}
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public InstructorDTO getSelectedIns() {
		return selectedIns;
	}
	public void setSelectedIns(InstructorDTO selectedIns) {
		this.selectedIns = selectedIns;
	}
	public InstructorDTO getSelectedTa() {
		return selectedTa;
	}
	public void setSelectedTa(InstructorDTO selectedTa) {
		this.selectedTa = selectedTa;
	}
	public StudentDTO getSelectedStudent() {
		return selectedStudent;
	}
	public void setSelectedStudent(StudentDTO selectedStudent) {
		this.selectedStudent = selectedStudent;
	}
	public IGetLoggedInStudentDataFacade getStudentDataFacade() {
		return studentDataFacade;
	}
	public void setStudentDataFacade(IGetLoggedInStudentDataFacade studentDataFacade) {
		this.studentDataFacade = studentDataFacade;
	}
	public IStudentFacade getStudentFacade() {
		return studentFacade;
	}
	public void setStudentFacade(IStudentFacade studentFacade) {
		this.studentFacade = studentFacade;
	}
	public List<BookStudentDTO> getStudentBooks() {
		return studentBooks;
	}
	public void setStudentBooks(List<BookStudentDTO> studentBooks) {
		this.studentBooks = studentBooks;
	}
	public BookStudentDTO getSelectedStudentBooks() {
		return selectedStudentBooks;
	}
	public void setSelectedStudentBooks(BookStudentDTO selectedStudentBooks) {
		this.selectedStudentBooks = selectedStudentBooks;
	}
	public IBooksFacade getBooksFacade() {
		return booksFacade;
	}
	public void setBooksFacade(IBooksFacade booksFacade) {
		this.booksFacade = booksFacade;
	}
	public IBookCopiesFacade getBooksCopiesFacade() {
		return booksCopiesFacade;
	}
	public void setBooksCopiesFacade(IBookCopiesFacade booksCopiesFacade) {
		this.booksCopiesFacade = booksCopiesFacade;
	}
	public List<BookInstructorDTO> getInsBooks() {
		return insBooks;
	}
	public void setInsBooks(List<BookInstructorDTO> insBooks) {
		this.insBooks = insBooks;
	}
	public List<BookInstructorDTO> getTaBooks() {
		return taBooks;
	}
	public void setTaBooks(List<BookInstructorDTO> taBooks) {
		this.taBooks = taBooks;
	}
	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}
	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}
	public BookInstructorDTO getSelectedInsBooks() {
		return selectedInsBooks;
	}
	public void setSelectedInsBooks(BookInstructorDTO selectedInsBooks) {
		this.selectedInsBooks = selectedInsBooks;
	}
	public BookInstructorDTO getSelectedTABooks() {
		return selectedTABooks;
	}
	public void setSelectedTABooks(BookInstructorDTO selectedTABooks) {
		this.selectedTABooks = selectedTABooks;
	}
	
	public String getHoldingImageURL() {
		
		return holdingImageURL;	
		}
	
	public void setHoldingImageURL(String holdingImageURL) {
		this.holdingImageURL = holdingImageURL;
	}
	public String getNumberOfBooksD() {
		return numberOfBooksD;
	}
	public void setNumberOfBooksD(String numberOfBooksD) {
		this.numberOfBooksD = numberOfBooksD;
	}
	
	public int getNumberOfBooksN() {
		return numberOfBooksN;
	}
	public void setNumberOfBooksN(int numberOfBooksN) {
		this.numberOfBooksN = numberOfBooksN;
	}
	
}
