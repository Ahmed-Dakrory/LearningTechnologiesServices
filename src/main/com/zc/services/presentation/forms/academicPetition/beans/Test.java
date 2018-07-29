/**
 * 
 */
package main.com.zc.services.presentation.forms.academicPetition.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * @author omnya
 *
 */
public class Test {
	public static void main(String[]args)
	{
		List<Integer> yearLst=new ArrayList<Integer>();
		for(int i=2013;i<2031;i++)
		{
			yearLst.add(i);
		}
		for(int i=0;i<yearLst.size();i++)
		{
			System.out.println(yearLst.get(i));
		}
	}

}
