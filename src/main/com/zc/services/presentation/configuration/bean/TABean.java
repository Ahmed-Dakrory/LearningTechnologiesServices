/**
 * 
 */
package main.com.zc.services.presentation.configuration.bean;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.UploadedFile;

import main.com.zc.services.domain.shared.enumurations.SemesterEnum;
import main.com.zc.services.presentation.configuration.facade.IStudentCourseFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.facade.IInstructorFacade;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
/**
 * @author omnya
 *
 */
@SessionScoped
@ManagedBean(name="TABean")
public class TABean {

	private List<InstructorDTO> tas;
	private UploadedFile file;
	private String statusMessage;
	
	@ManagedProperty("#{IStudentCourseFacade}")
   	private IStudentCourseFacade facade; 
	
	@ManagedProperty("#{IInstructorFacade}")
   	private IInstructorFacade insFacade;
	@PostConstruct
	public void init(){
		tas=new ArrayList<InstructorDTO>();
		saveStudentImages();
		//parse the folder of images to DB 
		/*String dirName="C:\\";
		ByteArrayOutputStream baos=new ByteArrayOutputStream(1000);
		BufferedImage img;
		try {
			img = ImageIO.read(new File(dirName,"rose.jpg"));
			ImageIO.write(img, "jpg", baos);
			baos.flush();
	 
			String base64String=Base64.encode(baos.toByteArray());
			baos.close();
	 
			byte[] bytearray = Base64.decode(base64String);
	 
			BufferedImage imag=ImageIO.read(new ByteArrayInputStream(bytearray));
			ImageIO.write(imag, "jpg", new File(dirName,"snap.jpg"));
			//parseExcelFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}
	public void parseExcelFile(){
		List<InstructorDTO> dataList = new ArrayList<InstructorDTO>();
		try {
			//inputStream = resource.getInputStream();
			// Create Workbook instance holding reference to .xlsx file
			 InputStream inputStream = null;
			inputStream=file.getInputstream();
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows one by one

			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();
				CoursesDTO course=new CoursesDTO();
				InstructorDTO instructor=new InstructorDTO();
				int count = 0;
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					count++;
				
                  
					if (count == 1) { // Emp ID
						
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_NUMERIC:
						{ 
						try {
							instructor.setEmpID(cell
									.getStringCellValue());
						} catch (Exception ex) {
						}
			
							 break;
							 
						}
						case Cell.CELL_TYPE_STRING:
						{
						try{
								instructor.setEmpID(cell
										.getStringCellValue());
							}
							catch(Exception ex)
							{
								ex.printStackTrace();
								
							}
						}
						
						}
					}

					

					else if (count ==2) {//name
						switch (cell.getCellType()) {
	
						
						      
						case Cell.CELL_TYPE_STRING:
							instructor.setName(cell
									.getStringCellValue());
						    
						
							break;
						}
					}

					else if (count ==3) { // title
						
						switch (cell.getCellType()) {
						
						case Cell.CELL_TYPE_STRING:
							instructor.setTitle(cell
									.getStringCellValue());    
						
							break;
						}
					
						
						
						
					}
 else if (count == 6) { // mobile

						switch (cell.getCellType()) {

						case Cell.CELL_TYPE_STRING:
							instructor.setPhone(cell.getStringCellValue());

							break;
						}

					}
 else if (count == 7) { // email

		switch (cell.getCellType()) {

		case Cell.CELL_TYPE_STRING:
			instructor.setMail(cell.getStringCellValue());

			break;
		}

	}
					

				}
				instructor.setEmpType(2);
					dataList.add(instructor);
				
				System.out.println("");
			}

			  inputStream .close();
		 dataList.remove(0);
		
		 tas=dataList;
		 // Add the list to DB 
		 for(int i=0;i<tas.size();i++){
			 InstructorDTO dto=facade.addIns(tas.get(i));
			 if(dto!=null)
			 {
				 System.out.println(dto.getName()+" is addedd successfully");
			 }
		 }
		//return dataList;
		 
	
		} catch (Exception e) {
			e.printStackTrace();
		//	return null;
		}
	}
	public void saveStudentImages()
	{
		try{
		/*File folder = new File("C:\\Users\\Omnya Alaa\\Desktop\\TAs Photos\\TAs Photos");
		File[] listOfFiles = folder.listFiles();
*/
		
			 InputStream inputStream = null;
				inputStream=file.getInputstream();
				//File listOfFiles=(File)file;
				File listOfFiles = File.createTempFile(file.getFileName(), "jpg");
				
				listOfFiles.deleteOnExit();
			    FileOutputStream out = new FileOutputStream(listOfFiles);
			    IOUtils.copy(inputStream, out);
			   
		List<InstructorDTO> tasLst = insFacade.getAllTAs();
		/* for (int i = 0; i < listOfFiles.length; i++) 
	    {
	   */ 	if (listOfFiles.isFile()) 
	    	{
	    		try 
	    		{
		    		String fileNameWithOutExt = FilenameUtils.removeExtension(listOfFiles.getName());
			    	String taName =fileNameWithOutExt;
			    	//try to get student in database
			    	InstructorDTO instructor = null;
			    	for (InstructorDTO ins_temp : tasLst) 
			    	{
			    		if(taName.startsWith(ins_temp.getName()))
			    		{
			    			instructor = ins_temp;
			    			break;
			    		}
					}
			    	
			    	if(instructor != null)
			    	{
			    		Path FilePath = listOfFiles.toPath();
			    		byte[] imageData;
						imageData = Files.readAllBytes(FilePath);
						instructor.setPhoto(imageData);
			    		insFacade.update(instructor);
			    		
			    		System.out.println("File " + listOfFiles.getName() + " DONE");
			    	}
			    	else
			    	{
			    		System.out.println("File " + listOfFiles.getName() + " dose not refere to student ??");
			    	}
		    	} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("File " + listOfFiles.getName() + " -- error in prosessing ??");
				}
	    		
	    		
	    	}
	    	else
	    	{}
	    	  
	  //  }
	    
	    System.out.println("--------- DONE ----------");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public List<InstructorDTO> getTas() {
		return tas;
	}
	public void setTas(List<InstructorDTO> tas) {
		this.tas = tas;
	}
	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public IStudentCourseFacade getFacade() {
		return facade;
	}
	public void setFacade(IStudentCourseFacade facade) {
		this.facade = facade;
	}
	public IInstructorFacade getInsFacade() {
		return insFacade;
	}
	public void setInsFacade(IInstructorFacade insFacade) {
		this.insFacade = insFacade;
	}
	
}
