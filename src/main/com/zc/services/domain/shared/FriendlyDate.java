/**
 * 
 */
package main.com.zc.services.domain.shared;

/**
 * @author Omnya Alaa
 *
 */
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;

public class FriendlyDate
{
	public static void main(String[] args) throws ParseException
	{
		 // Get the current date and time in a Calendar object
		/*for(int i=1;i<8;i++)
		{
			System.out.println("@Column(name="+"\""+"QUES"+i+"\""+")"+"\n"+"private String ques"+i+";"+"\n\n");
		}*/
		String arr[] = "Omnya Alaa".split(" ", 2);
		String firstWord = arr[0];
		String theRest = arr[1];
		System.out.println(firstWord);
		Calendar now = Calendar.getInstance();
		
			SimpleDateFormat df = new SimpleDateFormat();
			df.applyPattern("dd/MM/yyyy");
			System.out.println("courses_instructors".toUpperCase());
		    

		//System.out.println(format1.parse(formatted));
		System.out.println("The only-friendly-to-robots date is : " + now.toString() );
		System.out.println();	
 
		System.out.println("The more-like-a-timestamp date is   : " + now.getTime() );
		System.out.println();
 
		String verboseFriendlyDate = getFriendlyDate(now, true);
		System.out.println("The verbose friendly date is        : " + verboseFriendlyDate);
		System.out.println();
 
		String friendlyDate = getFriendlyDate(now);
		System.out.println("The non-verbose friendly date is    : " + friendlyDate);
	}
 
	// Overloaded function to cut down on calling arguments, no default parameters in Java =/
	public static String getFriendlyDate(Calendar theDate)
	{
		return getFriendlyDate(theDate, false);
	}
 
	// Function to get a human readable version of a Calendar object
	// If verbose is true we slightly expand the date wording
	public static String getFriendlyDate(Calendar theDate, boolean verbose)
	{
		int year       = theDate.get(Calendar.YEAR);
		int month      = theDate.get(Calendar.MONTH);
		int dayOfMonth = theDate.get(Calendar.DAY_OF_MONTH);
		int dayOfWeek  = theDate.get(Calendar.DAY_OF_WEEK);
 
		// Get the day of the week as a String.
		// Note: The Calendar DAY_OF_WEEK property is NOT zero-based, and Sunday is the first day of week.
		String friendly = "";
		switch (dayOfWeek)
		{
			case 1:
				friendly = "Sunday";
				break;
			case 2:
				friendly = "Monday";
				break;
			case 3:
				friendly = "Tuesday";
				break;
			case 4:
				friendly = "Wednesday";
				break;
			case 5:
				friendly = "Thursday";
				break;
			case 6:
				friendly = "Friday";
				break;
			case 7:
				friendly = "Saturday";
				break;
			default:
				friendly = "BadDayValue";
				break;
		}
 
		// Add padding and the prefix to the day of month
		if (verbose == true)
		{
			friendly += " the " + dayOfMonth;
		}
		else
		{
			friendly += ", " + dayOfMonth;
		}
 
		String dayString = String.valueOf(dayOfMonth);   // Convert dayOfMonth to String using valueOf
 
		// Suffix is "th" for day of day of month values ending in 0, 4, 5, 6, 7, 8, and 9
		if (dayString.endsWith("0") || dayString.endsWith("4") || dayString.endsWith("5") || dayString.endsWith("6") ||
                    dayString.endsWith("7") || dayString.endsWith("8") || dayString.endsWith("9"))
		{
			friendly += "th ";
		}
 
		// Suffix is "st" for day of month values ending in 1
		if (dayString.endsWith("1"))
		{
			friendly += "st ";
		}
 
		// Suffix is "nd" for day of month values ending in 2
		if (dayString.endsWith("2"))
		{
			friendly += "nd ";
		}
 
		// Suffix is "rd" for day of month values ending in 3
		if (dayString.endsWith("3"))
		{
			friendly += "rd ";
		}
 
		// Add more padding if we've been asked to be verbose
		if (verbose == true)
		{
			friendly += "of ";
		}
 
 
		// Get a friendly version of the month.
		// Note: The Calendar MONTH property is zero-based to increase the chance of developers making mistakes.
		switch (month)
		{
			case 0:
				friendly += "January";
				break;
			case 1:
				friendly += "February";
				break;
			case 2:
				friendly += "March";
				break;
			case 3:
				friendly += "April";
				break;
			case 4:
				friendly += "May";
				break;
			case 5:
				friendly += "June";
				break;
			case 6:
				friendly += "July";
				break;
			case 7:
				friendly += "August";
				break;
			case 8:
				friendly += "September";
				break;
			case 9:
				friendly += "October";
				break;
			case 10:
				friendly += "November";
				break;
			case 11:
				friendly += "December";
				break;
			default:
				friendly += "BadMonthValue";
				break;
		}
 
		// Tack on the year and we're done. Phew!
		friendly += " " + year;		
 
		return friendly;
 
	} // End of getFriendlyDate function
 
} // End of FriendlyDate class