/**
 * 
 */
package main.com.zc.services.applicationService.shared.service.impl;

import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.shared.service.IGetSemesterFromDateService;
import main.com.zc.services.domain.shared.enumurations.SemesterEnum;

/**
 * @author omnya
 *
 */
@Service("IGetSemesterFromDateService")
public class GetSemesterFromDateServiceImpl implements IGetSemesterFromDateService{

	@Override
	public SemesterEnum getSemester(Integer month, Integer day) {
		// TODO 
		//Spring : 2 , 3 , 4, 15-6
		//Summer : 16-6 , 7 , 8 ,15-9
		//Fall   :16-9 ,1 
		
		SemesterEnum semester =null;
		switch (month) {
        //Spring months
		case 2:  {semester = SemesterEnum.Spring;
                 break;
		}
        case 3:  {semester = SemesterEnum.Spring;
                 break;}
        case 4:  {semester = SemesterEnum.Spring;
                 break;}
        case 5:  {semester = SemesterEnum.Spring;
                 break;
        			}
        case 6:  
        	    {
        	    	if(day>=16)
        	    	semester = SemesterEnum.Summer;
        	    	else if(day<16)
            	    semester = SemesterEnum.Spring;
        	    	break;
        	    }
       //Summer
		case 7:
			{semester = SemesterEnum.Summer;
			break;}
		case 8:
			{semester = SemesterEnum.Summer;
			break;
			}
		case 9:
			{
				if(day<=15)
			semester = SemesterEnum.Summer;
				else if(day>15)
			semester = SemesterEnum.Fall;
				break;
			}
		//Fall
		case 10:
			{semester = SemesterEnum.Fall;
			break;}
		case 11:
			{semester = SemesterEnum.Fall;
			break;
			}
		case 12:
			 {
				 semester = SemesterEnum.Fall;
			 
			break;
			 }
		case 1:
			{
				semester = SemesterEnum.Fall;
				break;
			}
			
    }
		
		return semester;
	}


}
